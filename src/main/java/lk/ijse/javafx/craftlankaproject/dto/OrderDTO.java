package lk.ijse.javafx.craftlankaproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private String customerEmail;
    private String items;
    private BigDecimal totalAmount;
    private String status;
    private String payherePaymentId;
    private Timestamp orderDate;
    private Long userId;
}