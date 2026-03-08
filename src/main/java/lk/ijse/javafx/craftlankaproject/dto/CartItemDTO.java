package lk.ijse.javafx.craftlankaproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {

    private Long id;
    private String name;
    private int quantity;
    private Double price;

}
