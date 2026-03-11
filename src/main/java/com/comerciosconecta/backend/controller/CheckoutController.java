package com.comerciosconecta.backend.controller;

import com.comerciosconecta.backend.entity.Order;
import com.comerciosconecta.backend.entity.OrderItem;
import com.comerciosconecta.backend.entity.PaymentRecord;
import com.comerciosconecta.backend.repository.OrderRepository; // AÑADE ESTE IMPORT
import com.comerciosconecta.backend.service.CheckoutService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.comerciosconecta.backend.dto.CreateOrderRequest;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final RestTemplate restTemplate;
    private final OrderRepository orderRepository; // AGREGADO

    private static final Logger logger = LoggerFactory.getLogger(CheckoutController.class);

    @Value("${wompi.private-key}")
    private String wompiPrivateKey;

    @Value("${wompi.public-key}")
    private String wompiPublicKey;

    // Constructor actualizado con OrderRepository
    public CheckoutController(
            CheckoutService checkoutService,
            RestTemplate restTemplate,
            OrderRepository orderRepository) { // AGREGADO
        this.checkoutService = checkoutService;
        this.restTemplate = restTemplate;
        this.orderRepository = orderRepository; // INICIALIZADO
    }

    // 🔹 Método para generar la firma de integridad
    private String generateSignature(Long amountInCents, String currency, String reference) {
        String integritySecret = "test_integrity_DgTzc4WtN6hiZDW5t73wy1qE6QJHtacK";
        String data = amountInCents + currency + reference + integritySecret;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generando firma de integridad", e);
        }
    }

    // 1) Crear orden local
    @PostMapping("/create-order")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody CreateOrderRequest req) {
        List<OrderItem> items = new ArrayList<>();
        for (CreateOrderRequest.Item i : req.getItems()) {
            OrderItem it = new OrderItem();
            it.setProductoId(i.getProductoId());
            it.setNombre(i.getNombre());
            it.setCantidad(i.getCantidad());
            it.setPriceInCents(i.getPriceInCents());
            it.setIvaPercentage(i.getIvaPercentage());
            it.setSubtotalInCents(i.getSubtotalInCents());
            items.add(it);
        }

        Order order = checkoutService.createOrder(
                req.getCustomerName(),
                req.getCustomerEmail(),
                req.getCustomerPhone(),
                items,
                req.getTotalInCents()
        );

        Map<String, Object> resp = Map.of(
                "orderId", order.getId(),
                "orderUuid", order.getUuid(),
                "status", order.getStatus()
        );
        return ResponseEntity.ok(resp);
    }

    // 2) Crear Payment Link en Wompi
    @PostMapping("/create-payment-link/{orderId}")
    public ResponseEntity<Map<String, Object>> createPaymentLink(@PathVariable Long orderId) {

        Order order = checkoutService.getOrderById(orderId);

        String url = "https://sandbox.wompi.co/v1/payment_links";
        Map<String, Object> body = new HashMap<>();
        body.put("name", "Compra en Comercios Conecta - " + order.getUuid());
        body.put("description", "Compra de productos");
        body.put("single_use", true);
        body.put("collect_shipping", false);
        body.put("currency", order.getCurrency());
        body.put("amount_in_cents", order.getTotalInCents());

        body.put("redirect_url", "http://localhost:3000/store/order-confirmation");
        body.put("reference", order.getUuid());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(wompiPrivateKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> resp = restTemplate.postForEntity(url, entity, Map.class);

        Map data = (Map) resp.getBody().get("data");
        String linkId = (String) data.get("id");
        String paymentUrl = "https://checkout.wompi.co/l/" + linkId;

        PaymentRecord p = new PaymentRecord();
        p.setOrderId(order.getId());
        p.setWompiTransactionId(null);
        p.setAmountInCents(order.getTotalInCents());
        p.setCurrency(order.getCurrency());
        p.setStatus("CREATED_LINK");
        p.setPaymentMethodType("PAYMENT_LINK");
        p.setRawResponse(resp.getBody().toString());

        checkoutService.savePaymentRecord(p);

        return ResponseEntity.ok(Map.of("payment_url", paymentUrl));
    }

    // 3) Obtener todas las órdenes
    @GetMapping("/all-orders")
    public ResponseEntity<List<Map<String, Object>>> getAllOrdersSimple() {
        try {
            List<Order> orders = orderRepository.findAllByOrderByCreatedAtDesc();

            List<Map<String, Object>> response = new ArrayList<>();
            for (Order order : orders) {
                Map<String, Object> orderMap = new HashMap<>();
                orderMap.put("id", order.getId());
                orderMap.put("uuid", order.getUuid());
                orderMap.put("customerName", order.getCustomerName());
                orderMap.put("customerEmail", order.getCustomerEmail());
                orderMap.put("customerPhone", order.getCustomerPhone());
                orderMap.put("status", order.getStatus());
                orderMap.put("totalInCents", order.getTotalInCents());
                orderMap.put("totalInPesos", order.getTotalInCents() / 100.0);
                orderMap.put("createdAt", order.getCreatedAt());
                orderMap.put("updatedAt", order.getUpdatedAt());

                // Items de la orden
                List<Map<String, Object>> itemsList = new ArrayList<>();
                if (order.getItems() != null) {
                    for (OrderItem item : order.getItems()) {
                        Map<String, Object> itemMap = new HashMap<>();
                        itemMap.put("productoId", item.getProductoId());
                        itemMap.put("nombre", item.getNombre());
                        itemMap.put("cantidad", item.getCantidad());
                        itemMap.put("priceInCents", item.getPriceInCents());
                        itemMap.put("priceInPesos", item.getPriceInCents() / 100.0);
                        itemMap.put("subtotalInCents", item.getSubtotalInCents());
                        itemsList.add(itemMap);
                    }
                }
                orderMap.put("items", itemsList);
                orderMap.put("itemsCount", itemsList.size());

                response.add(orderMap);
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error obteniendo órdenes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }
}