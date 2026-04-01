package lk.ijse.javafx.craftlankaproject.Controller;

import lk.ijse.javafx.craftlankaproject.dto.APIResponse;
import lk.ijse.javafx.craftlankaproject.dto.CraftProductDtO;
import lk.ijse.javafx.craftlankaproject.dto.DashboardStatsDTO;
import lk.ijse.javafx.craftlankaproject.service.CraftProductService;
import lk.ijse.javafx.craftlankaproject.service.DashboardService;
import lk.ijse.javafx.craftlankaproject.service.impl.CraftProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seller")
@CrossOrigin(origins = "*")
public class SellerController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private CraftProductServiceImpl craftProductService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        // This will return the 3 stats (Products, Orders, Revenue)
        return ResponseEntity.ok(dashboardService.getSellerStats());
    }

    @GetMapping("my-products")
    public ResponseEntity<APIResponse> getAllProducts() {

        List<CraftProductDtO> products = craftProductService.getAllProducts();

        return ResponseEntity.ok(
                new APIResponse(200, "Product List Retrieved", products)
        );
    }
}
