package lk.ijse.javafx.craftlankaproject.dto;

import lk.ijse.javafx.craftlankaproject.entity.OrderStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

    private Long id;
    private double totalAmount;
    private OrderStatus status;
}
