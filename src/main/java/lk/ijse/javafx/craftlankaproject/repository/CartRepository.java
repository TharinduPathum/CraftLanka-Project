package lk.ijse.javafx.craftlankaproject.repository;

import lk.ijse.javafx.craftlankaproject.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    void deleteByCustomerEmail(String email);
}
