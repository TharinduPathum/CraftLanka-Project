package lk.ijse.javafx.craftlankaproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    private String paymentMethod;

    private String payherePaymentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    private LocalDateTime paymentDate;

    @OneToOne
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    private Order order;
}
