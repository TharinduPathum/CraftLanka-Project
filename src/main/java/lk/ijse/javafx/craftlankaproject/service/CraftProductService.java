package lk.ijse.javafx.craftlankaproject.service;

import lk.ijse.javafx.craftlankaproject.dto.CraftProductDtO;
import lk.ijse.javafx.craftlankaproject.entity.CraftProduct;

import java.util.List;

public interface CraftProductService {
    CraftProduct addProduct(CraftProductDtO craftProductDtO);

    CraftProduct updateProduct(Long id, CraftProductDtO craftProductDtO);

    void deleteProduct(Long craftProductDtO);

    List<CraftProductDtO> getAllProducts();
}
