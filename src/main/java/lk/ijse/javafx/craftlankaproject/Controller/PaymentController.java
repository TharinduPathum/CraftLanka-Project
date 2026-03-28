package lk.ijse.javafx.craftlankaproject.Controller;

import lk.ijse.javafx.craftlankaproject.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Value; // ADD THIS
//import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Value("${payhere.merchant.id}")
    private String merchantId;

    @Value("${payhere.secret}")
    private String secret;

    @PostMapping("/checkout")
    public ResponseEntity<Map<String, Object>> generatePaymentData(@RequestBody OrderDTO orderDto) {
        // 1. Generate a unique Order ID
        String orderId = "ORDER_" + System.currentTimeMillis();
        double amount = orderDto.getTotalAmount();
        String currency = "LKR";

        // 2. Create the MD5 Hash (Security Requirement)
        // Format: MerchantID + OrderID + Amount + Currency + MD5(Secret)
        String hash = generatePayHereHash(merchantId, orderId, amount, currency, secret);

        // 3. Send this data back to the Frontend
        Map<String, Object> response = new HashMap<>();
        response.put("merchant_id", merchantId);
        response.put("order_id", orderId);
        response.put("amount", amount);
        response.put("currency", currency);
        response.put("hash", hash);
        response.put("items", "CraftLanka Order");

        return ResponseEntity.ok(response);
    }

    private String generatePayHereHash(String mId, String oId, double amt, String curr, String secret) {
        // 1. Format the amount to 2 decimal places (MANDATORY)
        DecimalFormat df = new DecimalFormat("0.00");
        String amountFormatted = df.format(amt);

        // 2. Create the MD5 Secret (This is the MD5 of your Merchant Secret)
        String secretHash = md5(secret).toUpperCase();

        // 3. Concatenate the fields in the EXACT order PayHere requires
        // MerchantID + OrderID + Amount + Currency + MD5(Secret)
        String source = mId + oId + amountFormatted + curr + secretHash;

        // 4. Return the MD5 of the whole string
        return md5(source).toUpperCase();
    }

    // Helper method to perform MD5 hashing
    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    @PostMapping("/notify")
    public void handlePaymentNotification(@RequestParam Map<String, String> params) {
        // 1. PayHere sends data like 'order_id', 'status_code', 'md5sig'
        String orderId = params.get("order_id");
        String statusCode = params.get("status_code"); // '2' usually means Success

        System.out.println("Payment received for Order: " + orderId);

        if ("2".equals(statusCode)) {
            // 2. THIS IS WHERE THE MAGIC HAPPENS
            // Logic to:
            // - Change Order Status to 'PAID'
            // - Reduce Product Quantity in Database
            System.out.println("Payment Successful! Updating inventory...");
        } else {
            System.out.println("Payment failed or was canceled. Status: " + statusCode);
        }
    }
}
