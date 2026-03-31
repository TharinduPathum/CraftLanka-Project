package lk.ijse.javafx.craftlankaproject.repository;

import lk.ijse.javafx.craftlankaproject.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
