package lk.ijse.javafx.craftlankaproject.repository;

import lk.ijse.javafx.craftlankaproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
