package lk.ijse.javafx.craftlankaproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CraftProductDtO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String imageUrl;
}
