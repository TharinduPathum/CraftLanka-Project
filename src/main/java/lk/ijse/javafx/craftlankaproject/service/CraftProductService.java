package lk.ijse.javafx.craftlankaproject.service;

import lk.ijse.javafx.craftlankaproject.dto.CraftProductDtO;
import lk.ijse.javafx.craftlankaproject.entity.CraftProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface CraftProductService {
    CraftProduct addProduct(CraftProductDtO craftProductDtO);

    CraftProduct updateProduct(Long id, CraftProductDtO craftProductDtO);

    void deleteProduct(CraftProductDtO craftProductDtO);

    List<CraftProductDtO> getAllProducts();
}
