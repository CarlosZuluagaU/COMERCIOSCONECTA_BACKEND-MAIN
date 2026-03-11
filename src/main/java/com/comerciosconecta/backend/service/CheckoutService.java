package com.comerciosconecta.backend.service;

import com.comerciosconecta.backend.entity.Order;
import com.comerciosconecta.backend.entity.OrderItem;
import com.comerciosconecta.backend.entity.PaymentRecord;
import com.comerciosconecta.backend.repository.OrderRepository;
import com.comerciosconecta.backend.repository.PaymentRecordRepository;
import com.comerciosconecta.backend.repository.ProductoRepository;
import com.comerciosconecta.backend.entity.Producto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

@Service
public class CheckoutService {
    private final OrderRepository orderRepository;
    private final PaymentRecordRepository paymentRecordRepository;
    private final ProductoRepository productoRepository;

    public CheckoutService(OrderRepository orderRepository,
                           PaymentRecordRepository paymentRecordRepository,
                           ProductoRepository productoRepository) {
        this.orderRepository = orderRepository;
        this.paymentRecordRepository = paymentRecordRepository;
        this.productoRepository = productoRepository;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }


    @Transactional
    public Order createOrder(String customerName, String customerEmail, String customerPhone,
                             List<OrderItem> items, Long totalInCents) {
        Order o = new Order();
        o.setUuid(UUID.randomUUID().toString());
        o.setCustomerName(customerName);
        o.setCustomerEmail(customerEmail);
        o.setCustomerPhone(customerPhone);
        o.setTotalInCents(totalInCents);
        o.setCurrency("COP");
        o.setStatus("CREATED");
        o.setCreatedAt(LocalDateTime.now());
        o.setUpdatedAt(LocalDateTime.now());

        for (OrderItem item : items) {
            item.setOrder(o);
        }

        o.setItems(items);

        return orderRepository.save(o);
    }


    public PaymentRecord savePaymentRecord(PaymentRecord p) {
        return paymentRecordRepository.save(p);
    }

    @Transactional
    public void markOrderPaidAndDecreaseStock(String wompiTransactionId, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        // update order status
        order.setStatus("PAID");
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        // decrease stock for each item (best effort)
        for (OrderItem item : order.getItems()) {
            if (item.getProductoId() != null) {
                Producto producto = productoRepository.findById(item.getProductoId())
                        .orElse(null);
                if (producto != null) {
                    int newStock = producto.getStock() - item.getCantidad();
                    producto.setStock(Math.max(newStock, 0));
                    productoRepository.save(producto);
                }
            }
        }
    }

    public void markOrderFailed(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus("FAILED");
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
    }
}
