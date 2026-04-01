package lk.ijse.javafx.craftlankaproject.service;

import lk.ijse.javafx.craftlankaproject.dto.OrderDTO;
import java.util.List;

public interface OrderService {


    OrderDTO createPendingOrder(OrderDTO orderDTO);

    boolean updatePaymentStatus(String orderId, String payhereStatus, String payherePaymentId);

    List<OrderDTO> getCustomerOrderHistory(String email);

    OrderDTO getOrderById(Long id);

    List<OrderDTO> getAllOrders();

    void updateStatus(Long id, String newStatus);


}