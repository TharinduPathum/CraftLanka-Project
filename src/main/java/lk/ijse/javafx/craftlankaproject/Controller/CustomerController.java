package lk.ijse.javafx.craftlankaproject.Controller;

import lk.ijse.javafx.craftlankaproject.dto.APIResponse;
import lk.ijse.javafx.craftlankaproject.service.impl.CraftProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@CrossOrigin(origins = "http://localhost:63342")
public class CustomerController {

    @Autowired
    private CraftProductServiceImpl craftProductService; // Reuse the same service

    // CUSTOMER ENDPOINT: Get everything for the shop gallery
    @GetMapping("/products")
    public ResponseEntity<APIResponse> getPublicGallery() {
        return ResponseEntity.ok(
                new APIResponse(200, "Gallery Loaded", craftProductService.getAllProducts())
        );
    }

    @PutMapping("/products/{id}/reduce")
    public ResponseEntity<APIResponse> reduceStock(@PathVariable Long id) {
        craftProductService.decrementQuantity(id);
        return ResponseEntity.ok(new APIResponse(200, "Quantity reduced", null));
    }
}