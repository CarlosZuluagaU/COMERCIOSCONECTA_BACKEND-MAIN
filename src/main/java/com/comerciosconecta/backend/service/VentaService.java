package com.comerciosconecta.backend.service;

import com.comerciosconecta.backend.dto.*;
import com.comerciosconecta.backend.entity.InvoiceRecord;
import com.comerciosconecta.backend.entity.Venta;
import com.comerciosconecta.backend.entity.VentaItem;
import com.comerciosconecta.backend.repository.InvoiceRecordRepository;
import com.comerciosconecta.backend.repository.VentaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final InvoiceRecordRepository invoiceRecordRepository;
    private final FactusClient factusClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public VentaService(VentaRepository ventaRepository,
                        InvoiceRecordRepository invoiceRecordRepository,
                        FactusClient factusClient) {
        this.ventaRepository = ventaRepository;
        this.invoiceRecordRepository = invoiceRecordRepository;
        this.factusClient = factusClient;
    }

    // =========================================================================================
    //                                CREAR VENTA LOCAL
    // =========================================================================================
    @Transactional
    public Venta crearVenta(Venta venta) {

        if (venta.getDvCliente() == null) {
            venta.setDvCliente("1");
        }
        venta.setUuid(UUID.randomUUID().toString());
        venta.setEstado("CREATED");
        venta.setCreatedAt(LocalDateTime.now());
        venta.setUpdatedAt(LocalDateTime.now());

        if (venta.getItems() != null) {
            venta.getItems().forEach(i -> i.setVenta(venta));
        }

        double totalCalc = venta.getItems() != null ?
                venta.getItems().stream()
                        .mapToDouble(i -> {
                            double p = i.getPrecioTotal() != null ? i.getPrecioTotal() : 0.0;
                            int qty = i.getCantidad() != null ? i.getCantidad() : 1;
                            return p * qty;
                        })
                        .sum() : 0.0;

        venta.setTotalFactura(totalCalc);

        return ventaRepository.save(venta);
    }

    // =========================================================================================
    //                                   FACTURAR
    // =========================================================================================
    @Transactional
    public InvoiceRecord facturarVenta(Long ventaId) {
        Venta venta = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        // Obtener numbering_range_id activo desde Factus
        String numberingRangeIdStr = factusClient.getFirstNumberingRangeId();
        if (numberingRangeIdStr == null)
            throw new RuntimeException("Factus no devolvió numbering_range_id activo");

        Integer numberingRangeId;
        try {
            numberingRangeId = Integer.valueOf(numberingRangeIdStr);
        } catch (NumberFormatException ex) {
            throw new RuntimeException("numbering_range_id inválido desde Factus: " + numberingRangeIdStr);
        }

        // ================= ESTABLISHMENT DTO =================
        FactusEstablishmentDto establishmentDto = new FactusEstablishmentDto(
                "SuperMarket", // Nombre de tu establecimiento
                "calle 10 # 3-13", // Dirección de tu establecimiento
                "0987654321", // Teléfono
                "supermarket@gmail.com", // Email
                980 // municipality_id - ajustar según tu ubicación
        );

        // ================= CLIENTE DTO =================
        FactusCustomerDto customerDto = new FactusCustomerDto();
        customerDto.setIdentification(venta.getNumeroDocumentoCliente());
        customerDto.setDv(calcularDigitoVerificacion(venta.getNumeroDocumentoCliente())); // Método para calcular DV
        customerDto.setCompany("");
        customerDto.setTrade_name("");
        customerDto.setNames(venta.getNombreCliente());
        customerDto.setAddress("calle 1 # 2-68"); // Dirección del cliente
        customerDto.setEmail(venta.getEmailCliente());
        customerDto.setPhone(venta.getTelefonoCliente());
        customerDto.setLegal_organization_id(2); // Ajustar según organización legal
        customerDto.setTribute_id(21); // Ajustar según tributo
        customerDto.setIdentification_document_id(3); // Ajustar según tipo documento
        customerDto.setMunicipality_id(980); // Ajustar según municipio

        // ================= ÍTEMS DTO =================
        List<FactusItemDto> itemsDto = (venta.getItems() == null)
                ? new ArrayList<>()
                : venta.getItems().stream().map(item -> {

            // Calcular precio unitario sin impuestos
            double precioUnitario = item.getPrecioSinImpuestos() != null ?
                    item.getPrecioSinImpuestos() : 0.0;

            // Calcular tasa de impuesto como string
            String taxRate = item.getPorcentajeIva() != null ?
                    String.format("%.2f", item.getPorcentajeIva()) : "0.00";

            // Crear lista de withholding taxes vacía por defecto
            List<FactusWithholdingTaxDto> withholdingTaxes = new ArrayList<>();

            return new FactusItemDto(
                    item.getCodigoProducto(),        // code_reference
                    item.getNombre(),                // name
                    item.getCantidad(),              // quantity
                    0.0,                            // discount_rate (ajustar según necesidad)
                    precioUnitario,                  // price
                    taxRate,                         // tax_rate
                    70,                             // unit_measure_id (Unidad)
                    1,                              // standard_code_id
                    0,                              // is_excluded
                    1,                              // tribute_id
                    withholdingTaxes                 // withholding_taxes
            );
        }).collect(Collectors.toList());

        // ================= ALLOWANCE CHARGES =================
        List<FactusAllowanceChargeDto> allowanceCharges = new ArrayList<>();

// Exactamente como en tu curl original que funciona
        FactusAllowanceChargeDto allowanceCharge = new FactusAllowanceChargeDto();
        allowanceCharge.setConcept_type("03");     // "03"
        allowanceCharge.setIs_surcharge(true);     // true
        allowanceCharge.setReason("Propina");      // "Propina"
        allowanceCharge.setBase_amount(90000.00);  // 90000.00
        allowanceCharge.setAmount(9000.00);        // 9000.00

        allowanceCharges.add(allowanceCharge);

        System.out.println("Allowance Charges configurado (igual al curl original):");
        System.out.println("   - Concept Type: " + allowanceCharge.getConcept_type());
        System.out.println("   - Is Surcharge: " + allowanceCharge.getIs_surcharge());
        System.out.println("   - Reason: " + allowanceCharge.getReason());
        System.out.println("   - Base Amount: " + allowanceCharge.getBase_amount());
        System.out.println("   - Amount: " + allowanceCharge.getAmount());


        // ================= REQUEST FINAL =================
        FactusBillRequest factusRequest = new FactusBillRequest();
        factusRequest.setDocument("01"); // Factura de venta
        factusRequest.setNumbering_range_id(numberingRangeId);
        factusRequest.setReference_code("fact" + ventaId + "2025"); // Código de referencia único
        factusRequest.setObservation(venta.getNota() != null ? venta.getNota() : "");
        factusRequest.setPayment_method_code(10); // Código método de pago
        factusRequest.setEstablishment(establishmentDto);
        factusRequest.setCustomer(customerDto);
        factusRequest.setItems(itemsDto);
        factusRequest.setAllowance_charges(allowanceCharges);

        // ================= ENVIAR A FACTUS =================
        FactusValidationResponse resp = factusClient.crearFactura(factusRequest);

        // ================= GUARDAR REGISTRO =================
        InvoiceRecord record = new InvoiceRecord();
        record.setVentaId(ventaId);

        try {
            record.setRawResponse(resp != null ? objectMapper.writeValueAsString(resp) : "NULL RESPONSE");
        } catch (Exception e) {
            record.setRawResponse(resp != null ? resp.toString() : "NULL RESPONSE");
        }

        if (resp != null && "OK".equalsIgnoreCase(resp.getStatus())) {
            record.setStatus("INVOICED");
            venta.setEstado("INVOICED");

            Map<String, Object> data = resp.getData();
            if (data != null) {
                // Extraer bill_id y number según la estructura de respuesta de Factus
                String billId = extractBillId(data);
                String number = extractBillNumber(data);

                if (billId != null) record.setFactusBillId(billId);
                if (number != null) record.setNumber(number);
            }
        } else {
            record.setStatus("ERROR");
            venta.setEstado("ERROR");
        }

        invoiceRecordRepository.save(record);
        venta.setUpdatedAt(LocalDateTime.now());
        ventaRepository.save(venta);

        return record;
    }

    // Método auxiliar para calcular dígito de verificación (para NIT colombiano)
    private String calcularDigitoVerificacion(String nit) {
        if (nit == null || nit.length() < 8) return "0";

        try {
            int[] factores = {3, 7, 13, 17, 19, 23, 29, 37, 41, 43, 47, 53, 59, 67, 71};
            int suma = 0;

            for (int i = 0; i < nit.length(); i++) {
                int digito = Character.getNumericValue(nit.charAt(nit.length() - 1 - i));
                suma += digito * factores[i];
            }

            int resto = suma % 11;
            if (resto == 0 || resto == 1) {
                return String.valueOf(resto);
            } else {
                return String.valueOf(11 - resto);
            }
        } catch (Exception e) {
            return "0";
        }
    }

    // Métodos auxiliares para extraer información de la respuesta
    private String extractBillId(Map<String, Object> data) {
        // Implementar lógica para extraer bill_id según la estructura de Factus
        if (data.containsKey("bill_id")) {
            return data.get("bill_id").toString();
        }
        return null;
    }

    private String extractBillNumber(Map<String, Object> data) {
        // Implementar lógica para extraer number según la estructura de Factus
        if (data.containsKey("number")) {
            return data.get("number").toString();
        }
        return null;
    }

    // En tu VentaService.java, agrega este método:

    public VentaDTO mapToVentaDTO(Venta venta) {
        if (venta == null) {
            return null;
        }

        // Mapear items
        List<VentaItemDTO> itemDTOs = venta.getItems() == null ?
                java.util.Collections.emptyList() :
                venta.getItems().stream()
                        .map(item -> new VentaItemDTO(
                                item.getId(),
                                item.getCodigoProducto(),
                                item.getNombre(),
                                item.getCantidad(),
                                item.getPrecioTotal(),
                                item.getPorcentajeIva()
                        ))
                        .collect(java.util.stream.Collectors.toList());

        return new VentaDTO(
                venta.getId(),
                venta.getUuid(),
                venta.getEstado(),
                venta.getTotalFactura(),
                venta.getNota(),
                venta.getReferencia(),
                venta.getNombreCliente(),
                venta.getNumeroDocumentoCliente(),
                venta.getCreatedAt(),
                itemDTOs
        );
    }
}