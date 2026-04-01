package lk.ijse.javafx.craftlankaproject.Controller;

import lk.ijse.javafx.craftlankaproject.dto.OrderDTO;
import lk.ijse.javafx.craftlankaproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 1. NEW METHOD: Create the initial PENDING order.
     * Call this from your checkout page before starting the PayHere popup.
     */
    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO, Authentication authentication) {
        // Automatically set the email from the JWT token for security
        orderDTO.setCustomerEmail(authentication.getName());

        OrderDTO savedOrder = orderService.createPendingOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }


    @GetMapping("/my-activities")
    public ResponseEntity<List<OrderDTO>> getMyOrders(Authentication authentication) {
        // authentication.getName() returns the email from the logged-in user's token
        String userEmail = authentication.getName();

        List<OrderDTO> myOrders = orderService.getCustomerOrderHistory(userEmail);
        return ResponseEntity.ok(myOrders);
    }

    @PostMapping("/payment-notification")
    public ResponseEntity<String> handlePayHereNotification(@RequestParam Map<String, String> params) {
        String orderId = params.get("order_id");
        String payhereStatus = params.get("status_code"); // '2' is success
        String paymentId = params.get("payment_id");

        boolean isUpdated = orderService.updatePaymentStatus(orderId, payhereStatus, paymentId);

        if (isUpdated) {
            return ResponseEntity.ok("Payment Processed Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        // Fetch all orders and convert them to DTOs
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> statusUpdate) {
        String newStatus = statusUpdate.get("status");
        orderService.updateStatus(id, newStatus);
        return ResponseEntity.ok("Status Updated!");
    }
}