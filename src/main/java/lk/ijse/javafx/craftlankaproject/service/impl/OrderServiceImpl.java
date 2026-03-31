package lk.ijse.javafx.craftlankaproject.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.javafx.craftlankaproject.dto.OrderDTO;
import lk.ijse.javafx.craftlankaproject.entity.Order;
import lk.ijse.javafx.craftlankaproject.entity.OrderStatus;
import lk.ijse.javafx.craftlankaproject.entity.Payment;
import lk.ijse.javafx.craftlankaproject.entity.PaymentStatus;
import lk.ijse.javafx.craftlankaproject.repository.OrderRepository;
import lk.ijse.javafx.craftlankaproject.repository.PaymentRepository;
import lk.ijse.javafx.craftlankaproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public OrderDTO createPendingOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setCustomerEmail(orderDTO.getCustomerEmail());
        order.setItems(orderDTO.getItems());
        order.setAmount(orderDTO.getTotalAmount());
        order.setStatus(OrderStatus.PENDING);
        Order savedOrder = orderRepository.save(order);
        return mapToDTO(savedOrder);
    }

    @Override
    public boolean updatePaymentStatus(String orderId, String payhereStatus, String payherePaymentId) {
        Order order = orderRepository.findById(Long.parseLong(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if ("2".equals(payhereStatus)) {
            // Use the Enums here!
            order.setStatus(OrderStatus.PENDING); // Order is still pending shipping

            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setAmount(order.getAmount().doubleValue());
            payment.setStatus(PaymentStatus.COMPLETED); // Payment is done!
            payment.setPayherePaymentId(payherePaymentId);

            paymentRepository.save(payment);
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    @Override
    public List<OrderDTO> getCustomerOrderHistory(String email) {
        List<Order> orders = orderRepository.findByCustomerEmail(email);
        return orders.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        // 1. Fetch the order from the database using the repository
        Optional<Order> orderOptional = orderRepository.findById(id);

        // 2. Check if the order exists
        if (orderOptional.isPresent()) {
            // 3. Convert the Entity to a DTO and return it
            return mapToDTO(orderOptional.get());
        } else {
            // 4. Return null or throw a custom "OrderNotFoundException"
            // For a simple project, returning null is fine, but logging it is better.
            System.out.println("Order with ID " + id + " not found in database.");
            return null;
        }
    }

    private OrderDTO mapToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setCustomerEmail(order.getCustomerEmail());
        dto.setItems(order.getItems());
        dto.setTotalAmount(order.getAmount());

        if (order.getStatus() != null) {
            dto.setStatus(order.getStatus().name());
        }

        dto.setPayherePaymentId(order.getPayherePaymentId());

        return dto;
    }
}
