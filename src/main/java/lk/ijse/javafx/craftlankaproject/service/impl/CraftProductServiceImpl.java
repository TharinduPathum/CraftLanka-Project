package lk.ijse.javafx.craftlankaproject.service.impl;

import lk.ijse.javafx.craftlankaproject.dto.CraftProductDtO;
import lk.ijse.javafx.craftlankaproject.entity.CraftProduct;
import lk.ijse.javafx.craftlankaproject.repository.CraftProductRepository;
import lk.ijse.javafx.craftlankaproject.service.CraftProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CraftProductServiceImpl implements CraftProductService {

    private final CraftProductRepository craftProductRepository;

    public CraftProductServiceImpl(CraftProductRepository craftProductRepository) {
        this.craftProductRepository = craftProductRepository;
    }

    @Override
    public CraftProduct addProduct(CraftProductDtO craftProductDtO) {
        CraftProduct product = new CraftProduct();
        product.setName(craftProductDtO.getName());
        product.setDescription(craftProductDtO.getDescription());
        product.setPrice(craftProductDtO.getPrice());
        product.setQuantity(craftProductDtO.getQuantity());
        product.setImageUrl(craftProductDtO.getImageUrl());

        return craftProductRepository.save(product);
    }

    @Override
    public CraftProduct updateProduct(Long id, CraftProductDtO craftProductDtO) {
        Optional<CraftProduct> optionalProduct = craftProductRepository.findById(id);

        if (optionalProduct.isPresent()) {

            CraftProduct product = optionalProduct.get();

            product.setName(craftProductDtO.getName());
            product.setDescription(craftProductDtO.getDescription());
            product.setPrice(craftProductDtO.getPrice());
            product.setQuantity(craftProductDtO.getQuantity());
            product.setImageUrl(craftProductDtO.getImageUrl());

            return craftProductRepository.save(product);
        }
        return null;
    }

    @Override
    public void deleteProduct(CraftProductDtO craftProductDtO) {

        Optional<CraftProduct> product =
                craftProductRepository.findById(craftProductDtO.getId());

        product.ifPresent(craftProductRepository::delete);
    }

    @Override
    public List<CraftProductDtO> getAllProducts() {
        List<CraftProduct> products = craftProductRepository.findAll();

        return products.stream().map(product ->
                new CraftProductDtO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getQuantity(),
                        product.getImageUrl()
                )
        ).collect(Collectors.toList());
    }
}
