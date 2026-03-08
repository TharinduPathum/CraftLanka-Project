package lk.ijse.javafx.craftlankaproject.Controller;

import jakarta.validation.Valid;
import lk.ijse.javafx.craftlankaproject.dto.CraftProductDtO;
import lk.ijse.javafx.craftlankaproject.service.CraftProductService;
import lk.ijse.javafx.craftlankaproject.util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/products")
public class CraftProductController {

    @Autowired
    private CraftProductService craftProductService;

    @PostMapping
    public ResponseEntity<APIResponse<String>> addProduct(@RequestBody @Valid CraftProductDtO craftProductDtO) {
        craftProductService.addProduct(craftProductDtO);

        return new ResponseEntity<>(
                new APIResponse<>(201,"Craft Product Added", null),
                HttpStatus.CREATED
        );
    }


    @PutMapping("/{id}")
    public void updateProduct(
            @PathVariable Long id,
            @RequestBody CraftProductDtO craftProductDtO) {

        craftProductService.updateProduct(id, craftProductDtO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteProduct(@PathVariable Long id) {

        craftProductService.deleteProduct(id);

        return ResponseEntity.ok(
                new APIResponse<>(200, "Product Deleted", null)
        );
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<CraftProductDtO>>> getAllProducts() {

        List<CraftProductDtO> products = craftProductService.getAllProducts();

        return ResponseEntity.ok(
                new APIResponse<>(200, "Product List Retrieved", products)
        );
    }


    }