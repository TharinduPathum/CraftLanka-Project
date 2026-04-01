package lk.ijse.javafx.craftlankaproject.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.javafx.craftlankaproject.dto.OrderDTO;
import lk.ijse.javafx.craftlankaproject.entity.*;
import lk.ijse.javafx.craftlankaproject.repository.OrderRepository;
import lk.ijse.javafx.craftlankaproject.repository.PaymentRepository;
import lk.ijse.javafx.craftlankaproject.repository.UserRepository;
import lk.ijse.javafx.craftlankaproject.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public OrderDTO createPendingOrder(OrderDTO orderDTO) {

        User existingUser = userRepository.findByEmail(orderDTO.getCustomerEmail())
                .orElseThrow(() -> new RuntimeException("User not found: " + orderDTO.getCustomerEmail()));

        Order order = new Order();
        order.setCustomer(existingUser);
        order.setStatus(OrderStatus.PENDING);
        order.setAmount(orderDTO.getTotalAmount());
        order.setOrderDate(Timestamp.valueOf(LocalDateTime.now()));

        Order savedOrder = orderRepository.saveAndFlush(order);

        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    @Override
    public boolean updatePaymentStatus(String orderId, String payhereStatus, String payherePaymentId) {
        Order order = orderRepository.findById(Long.parseLong(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if ("2".equals(payhereStatus)) {

            order.setStatus(OrderStatus.PENDING);

            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setAmount(order.getAmount().doubleValue());
            payment.setStatus(PaymentStatus.COMPLETED);
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
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isPresent()) {
            return mapToDTO(orderOptional.get());
        } else {

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

    @Override
    public List<OrderDTO> getAllOrders() {

        List<Order> orders = orderRepository.findAll();


        return orders.stream().map(order -> {
            OrderDTO dto = new OrderDTO();

            dto.setId(order.getId());
            dto.setItems(order.getItems());
            dto.setPayherePaymentId(order.getPayherePaymentId());


            dto.setTotalAmount(order.getAmount() != null ? order.getAmount() : BigDecimal.ZERO);

            dto.setCustomerEmail(order.getCustomerEmail());


            if (order.getOrderDate() != null) {
                dto.setOrderDate(Timestamp.valueOf(order.getOrderDate().toLocalDateTime()));
            }

            if (order.getStatus() != null) {
                dto.setStatus(order.getStatus().name());
            }


            if (order.getCustomer() != null) {
                dto.setUserId(order.getCustomer().getId());
            }

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void updateStatus(Long id, String newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));

        try {

            OrderStatus status = OrderStatus.valueOf(newStatus.toUpperCase());
            order.setStatus(status);

            orderRepository.save(order);

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value: " + newStatus);
        }
    }

}
