package lk.ijse.javafx.craftlankaproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CraftProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private int quantity;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;
}
