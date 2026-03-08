package lk.ijse.javafx.craftlankaproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDTO {

    private Long id;
    private String name;
    private double price;
    private int quantity;

}
