package lk.ijse.javafx.craftlankaproject.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lk.ijse.javafx.craftlankaproject.dto.CraftProductDtO;
import lk.ijse.javafx.craftlankaproject.entity.CraftProduct;
import lk.ijse.javafx.craftlankaproject.repository.CraftProductRepository;
import lk.ijse.javafx.craftlankaproject.repository.UserRepository;
import lk.ijse.javafx.craftlankaproject.service.CraftProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CraftProductServiceImpl implements CraftProductService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CraftProductRepository craftProductRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CraftProduct addProduct(CraftProductDtO craftProductDtO) {

        CraftProduct product = modelMapper.map(craftProductDtO, CraftProduct.class);


        if (craftProductDtO.getId() != null) {
            if (craftProductRepository.existsById(craftProductDtO.getId())) {
                throw new EntityExistsException("Product already exists!");
            }
        }

        return craftProductRepository.save(product);
    }

    @Override
    @Transactional
    public CraftProduct updateProduct(Long id, CraftProductDtO craftProductDtO) {

        CraftProduct existingProduct = craftProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        existingProduct.setName(craftProductDtO.getName());
        existingProduct.setDescription(craftProductDtO.getDescription());
        existingProduct.setPrice(craftProductDtO.getPrice());
        existingProduct.setQuantity(craftProductDtO.getQuantity());

        return craftProductRepository.save(existingProduct);
    }

    public void deleteProductById(Long id) {
        if (craftProductRepository.existsById(id)) {
            craftProductRepository.deleteById(id);
        } else {
            throw new NullPointerException("Product not available to delete");
        }
    }

    @Override
    public List<CraftProductDtO> getAllProducts() {

        List<CraftProduct> products = craftProductRepository.findAll();

        return products.stream().map(product -> {
            CraftProductDtO dto = new CraftProductDtO();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
            dto.setImageUrl(product.getImageUrl());
            dto.setQuantity(product.getQuantity());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void decrementQuantity(Long productId) {
        CraftProduct product = craftProductRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getQuantity() > 0) {
            product.setQuantity(product.getQuantity() - 1);
            craftProductRepository.save(product);
        } else {
            throw new RuntimeException("Out of stock!");
        }
    }
}