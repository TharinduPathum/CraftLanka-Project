package lk.ijse.javafx.craftlankaproject.repository;

import lk.ijse.javafx.craftlankaproject.entity.CraftProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CraftProductRepository extends JpaRepository<CraftProduct, Long> {
    List<CraftProduct> findByCraftId(Long craftId);
}
