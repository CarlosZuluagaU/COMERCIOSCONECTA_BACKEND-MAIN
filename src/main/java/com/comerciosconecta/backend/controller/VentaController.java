package com.comerciosconecta.backend.controller;

import com.comerciosconecta.backend.dto.FacturaResponseDTO;
import com.comerciosconecta.backend.dto.VentaDTO;
import com.comerciosconecta.backend.entity.InvoiceRecord;
import com.comerciosconecta.backend.entity.Venta;
import com.comerciosconecta.backend.repository.InvoiceRecordRepository;
import com.comerciosconecta.backend.repository.VentaRepository;
import com.comerciosconecta.backend.service.VentaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;
    private final VentaRepository ventaRepository;
    private final InvoiceRecordRepository invoiceRepo;

    public VentaController(VentaService ventaService,
                           VentaRepository ventaRepository,
                           InvoiceRecordRepository invoiceRepo) {
        this.ventaService = ventaService;
        this.ventaRepository = ventaRepository;
        this.invoiceRepo = invoiceRepo;
    }

    // ============================================================
    //                     CREAR VENTA LOCAL
    // ============================================================
    @PostMapping
    public ResponseEntity<?> crearVenta(@RequestBody Venta venta) {
        try {
            // Establecer dígito de verificación fijo como "1"
            venta.setDvCliente("1");

            // Validar campos requeridos
            if (venta.getNumeroDocumentoCliente() == null || venta.getNumeroDocumentoCliente().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El número de documento del cliente es requerido");
            }

            if (venta.getNombreCliente() == null || venta.getNombreCliente().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El nombre del cliente es requerido");
            }

            // Establecer valores por defecto si no están presentes
            if (venta.getPaymentMethodCode() == null) {
                venta.setPaymentMethodCode(10);
            }

            if (venta.getLegalOrganizationId() == null) {
                venta.setLegalOrganizationId(2);
            }

            if (venta.getTributeId() == null) {
                venta.setTributeId(21);
            }

            if (venta.getIdentificationDocumentId() == null) {
                venta.setIdentificationDocumentId(3);
            }

            if (venta.getMunicipalityId() == null) {
                venta.setMunicipalityId(980);
            }

            // Establecer datos del establecimiento si no están presentes
            if (venta.getEstablecimientoNombre() == null) {
                venta.setEstablecimientoNombre("SuperMarket");
            }

            if (venta.getEstablecimientoDireccion() == null) {
                venta.setEstablecimientoDireccion("calle 10 # 3-13");
            }

            if (venta.getEstablecimientoTelefono() == null) {
                venta.setEstablecimientoTelefono("0987654321");
            }

            if (venta.getEstablecimientoEmail() == null) {
                venta.setEstablecimientoEmail("supermarket@gmail.com");
            }

            if (venta.getEstablecimientoMunicipioId() == null) {
                venta.setEstablecimientoMunicipioId(980);
            }

            Venta v = ventaService.crearVenta(venta);
            return ResponseEntity.status(HttpStatus.CREATED).body(v);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear venta: " + e.getMessage());
        }
    }

    // ============================================================
    //                     OBTENER VENTA POR ID
    // ============================================================
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerVenta(@PathVariable Long id) {
        try {
            Optional<Venta> ventaOpt = ventaRepository.findById(id);
            if (ventaOpt.isPresent()) {
                VentaDTO ventaDTO = ventaService.mapToVentaDTO(ventaOpt.get());
                return ResponseEntity.ok(ventaDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Venta no encontrada con id: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener venta: " + e.getMessage());
        }
    }

    // ============================================================
    //                     LISTAR TODAS LAS VENTAS
    // ============================================================
    @GetMapping
    public ResponseEntity<?> listarVentas() {
        try {
            List<Venta> ventas = ventaRepository.findAll();
            List<VentaDTO> ventasDTO = ventas.stream()
                    .map(ventaService::mapToVentaDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ventasDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al listar ventas: " + e.getMessage());
        }
    }

    // ============================================================
    //                           FACTURAR
    // ============================================================
    @PostMapping("/{id}/facturar")
    public ResponseEntity<?> facturar(@PathVariable Long id) {
        try {
            Optional<Venta> ventaOpt = ventaRepository.findById(id);
            if (ventaOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Venta no encontrada con id " + id);
            }

            Venta venta = ventaOpt.get();

            // Verificar si ya existe una factura exitosa
            InvoiceRecord existing = invoiceRepo.findFirstByVentaIdAndStatus(id, "INVOICED");
            if (existing != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Esta venta ya fue facturada. Número: " + existing.getNumber());
            }

            // Validar estado de la venta
            if (!"CREATED".equals(venta.getEstado())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("La venta no puede ser facturada porque su estado es: " + venta.getEstado());
            }

            // Validar que tenga items
            if (venta.getItems() == null || venta.getItems().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("La venta no tiene items para facturar");
            }

            System.out.println("️ LLAMANDO AL CLIENTE FACTUS para venta ID: " + id);

            InvoiceRecord rec = ventaService.facturarVenta(id);

            if ("ERROR".equals(rec.getStatus())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error al facturar: " + rec.getRawResponse());
            }

            FacturaResponseDTO dto = new FacturaResponseDTO(
                    rec.getVentaId(),
                    rec.getFactusBillId(),
                    rec.getNumber(),
                    rec.getStatus(),
                    rec.getRawResponse()
            );

            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al facturar: " + e.getMessage());
        }
    }

    // ============================================================
    //                     HISTORIAL DE FACTURAS
    // ============================================================
    @GetMapping("/{id}/invoices")
    public ResponseEntity<?> obtenerFacturas(@PathVariable Long id) {
        try {
            // Verificar que la venta exista
            if (!ventaRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Venta no encontrada con id: " + id);
            }

            List<InvoiceRecord> invoices = invoiceRepo.findByVentaIdOrderByCreatedAtDesc(id);
            return ResponseEntity.ok(invoices);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener facturas: " + e.getMessage());
        }
    }

    // ============================================================
    //                     ACTUALIZAR VENTA
    // ============================================================
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarVenta(@PathVariable Long id, @RequestBody Venta ventaActualizada) {
        try {
            Optional<Venta> ventaExistenteOpt = ventaRepository.findById(id);
            if (ventaExistenteOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Venta no encontrada con id: " + id);
            }

            Venta ventaExistente = ventaExistenteOpt.get();

            // Actualizar solo campos permitidos
            if (ventaActualizada.getNota() != null) {
                ventaExistente.setNota(ventaActualizada.getNota());
            }
            if (ventaActualizada.getReferencia() != null) {
                ventaExistente.setReferencia(ventaActualizada.getReferencia());
            }
            if (ventaActualizada.getSubtotal() != null) {
                ventaExistente.setSubtotal(ventaActualizada.getSubtotal());
            }
            if (ventaActualizada.getTotalIva() != null) {
                ventaExistente.setTotalIva(ventaActualizada.getTotalIva());
            }
            if (ventaActualizada.getTotalFactura() != null) {
                ventaExistente.setTotalFactura(ventaActualizada.getTotalFactura());
            }

            Venta ventaActualizadaEntity = ventaRepository.save(ventaExistente);
            return ResponseEntity.ok(ventaActualizadaEntity);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar venta: " + e.getMessage());
        }
    }

    // ============================================================
    //                     ELIMINAR VENTA
    // ============================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarVenta(@PathVariable Long id) {
        try {
            Optional<Venta> ventaOpt = ventaRepository.findById(id);
            if (ventaOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Venta no encontrada con id: " + id);
            }

            Venta venta = ventaOpt.get();

            // Verificar que no esté facturada
            if ("INVOICED".equals(venta.getEstado())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("No se puede eliminar una venta ya facturada");
            }

            ventaRepository.delete(venta);
            return ResponseEntity.ok("Venta eliminada correctamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar venta: " + e.getMessage());
        }
    }
}