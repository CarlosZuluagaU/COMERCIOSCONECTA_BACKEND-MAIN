package com.comerciosconecta.backend.controller;

import com.comerciosconecta.backend.entity.PaymentRecord;
import com.comerciosconecta.backend.repository.PaymentRecordRepository;
import com.comerciosconecta.backend.repository.OrderRepository;
import com.comerciosconecta.backend.service.CheckoutService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/wompi")
public class WompiWebhookController {

    private final CheckoutService checkoutService;
    private final PaymentRecordRepository paymentRecordRepository;
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${wompi.events-secret}")
    private String wompiEventsSecret;

    public WompiWebhookController(CheckoutService checkoutService,
                                  PaymentRecordRepository paymentRecordRepository,
                                  OrderRepository orderRepository) {
        this.checkoutService = checkoutService;
        this.paymentRecordRepository = paymentRecordRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload,
                                                @RequestHeader(value = "X-Event-Checksum", required = false) String headerChecksum) throws Exception {
        // 1) Parse payload
        JsonNode root = objectMapper.readTree(payload);
        JsonNode signature = root.path("signature");
        String checksumFromBody = signature.path("checksum").asText(null);
        String checksum = headerChecksum != null ? headerChecksum : checksumFromBody;

        if (checksum == null) {
            return ResponseEntity.badRequest().body("Missing checksum");
        }

        // 2) Recompute checksum per Wompi docs:
        // - read signature.properties array (ex: ["transaction.id","transaction.status",...])
        // - concat the values from root.data according to those properties, then + timestamp + secret
        JsonNode propertiesNode = signature.path("properties");
        long timestamp = root.path("timestamp").asLong(0);

        StringBuilder toHash = new StringBuilder();
        for (int i = 0; i < propertiesNode.size(); i++) {
            String prop = propertiesNode.get(i).asText();
            // prop like "transaction.id" -> navigate root.data.transaction.id
            String[] parts = prop.split("\\.");
            JsonNode node = root.path("data");
            for (String p : parts) {
                node = node.path(p);
            }
            String value = node.isMissingNode() || node.isNull() ? "" : node.asText();
            toHash.append(value);
        }
        toHash.append(timestamp);
        toHash.append(wompiEventsSecret);

        // SHA256 hex uppercase
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(toHash.toString().getBytes(StandardCharsets.UTF_8));
        StringBuilder hex = new StringBuilder();
        for (byte b : digest) {
            hex.append(String.format("%02X", b));
        }
        String computed = hex.toString();

        if (!computed.equalsIgnoreCase(checksum)) {
            // signature mismatch -> ignore
            return ResponseEntity.status(400).body("Invalid checksum");
        }

        // 3) Process event types (example: transaction.updated)
        String event = root.path("event").asText();
        if ("transaction.updated".equals(event) || event.startsWith("transaction.")) {
            JsonNode tx = root.path("data").path("transaction");
            String txId = tx.path("id").asText();
            String status = tx.path("status").asText();
            long amount = tx.path("amount_in_cents").asLong();
            String reference = tx.path("reference").asText(null); // we put order.uuid in reference when creating

            // Try find payment record by wompi id
            Optional<PaymentRecord> maybe = paymentRecordRepository.findByWompiTransactionId(txId);
            // Save or update payment record
            PaymentRecord p = maybe.orElseGet(() -> {
                PaymentRecord newP = new PaymentRecord();
                newP.setWompiTransactionId(txId);
                newP.setAmountInCents(amount);
                newP.setCurrency(tx.path("currency").asText("COP"));
                return newP;
            });

            p.setStatus(status);
            p.setRawResponse(payload);
            p.setPaymentMethodType(tx.path("payment_method_type").asText());
            paymentRecordRepository.save(p);

            // Find order by reference (uuid) -> update statuses
            if (reference != null && !reference.isEmpty()) {
                orderRepository.findByUuid(reference).ifPresent(order -> {
                    if ("APPROVED".equalsIgnoreCase(status)) {
                        checkoutService.markOrderPaidAndDecreaseStock(txId, order.getId());
                    } else if ("DECLINED".equalsIgnoreCase(status) || "ERROR".equalsIgnoreCase(status)) {
                        checkoutService.markOrderFailed(order.getId());
                    } else {
                        // other statuses: PENDING, etc. You may update as needed
                        order.setStatus(status);
                        order.setUpdatedAt(java.time.LocalDateTime.now());
                        orderRepository.save(order);
                    }
                });
            }
        }

        // Respond 200 OK
        return ResponseEntity.ok("OK");
    }
}
