package lk.ijse.javafx.craftlankaproject.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User customer;

    @Column(name = "email", nullable = false)
    private String customerEmail;

    @Column(columnDefinition = "TEXT")
    private String items;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "payhere_payment_id", length = 100)
    private String payherePaymentId;

    @CreationTimestamp
    @Column(name = "order_date", updatable = false)
    private Timestamp orderDate;
}