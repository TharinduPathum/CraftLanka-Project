package lk.ijse.javafx.craftlankaproject.Controller;

import jakarta.validation.Valid;
import lk.ijse.javafx.craftlankaproject.dto.CraftProductDtO;
import lk.ijse.javafx.craftlankaproject.dto.APIResponse;
import lk.ijse.javafx.craftlankaproject.service.impl.CraftProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/products")
public class CraftProductController {

    @Autowired
    private CraftProductServiceImpl craftProductServiceImpl;

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addProduct(@RequestBody @Valid CraftProductDtO craftProductDtO) {
        craftProductServiceImpl.addProduct(craftProductDtO);

        return new ResponseEntity<>(
                new APIResponse(201,"Craft Product Added", null),
                HttpStatus.CREATED
        );
    }


    @PutMapping("/{id}")
    public void updateProduct(
            @PathVariable Long id,
            @RequestBody CraftProductDtO craftProductDtO) {

        craftProductServiceImpl.updateProduct(id, craftProductDtO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteProduct(@PathVariable CraftProductDtO id) {

        craftProductServiceImpl.deleteProductById(id.getId());

        return ResponseEntity.ok(
                new APIResponse(200, "Product Deleted", null)
        );
    }

    @GetMapping("my-products")
    public ResponseEntity<APIResponse> getAllProducts() {

        List<CraftProductDtO> products = craftProductServiceImpl.getAllProducts();

        return ResponseEntity.ok(
                new APIResponse(200, "Product List Retrieved", products)
        );
    }


    }