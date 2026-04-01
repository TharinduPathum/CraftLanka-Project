package lk.ijse.javafx.craftlankaproject.repository;

import lk.ijse.javafx.craftlankaproject.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    long countByOrderDateAfter(LocalDateTime date);

    @Query("SELECT SUM(o.amount) FROM Order o WHERE o.status IN (lk.ijse.javafx.craftlankaproject.entity.OrderStatus.PLACED, lk.ijse.javafx.craftlankaproject.entity.OrderStatus.SHIPPED, lk.ijse.javafx.craftlankaproject.entity.OrderStatus.DELIVERED)")
    BigDecimal sumTotalRevenue();
    List<Order> findByCustomerEmail(String customerEmail);

    Optional<Order> findById(Long id);
}
