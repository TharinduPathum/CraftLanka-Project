package lk.ijse.javafx.craftlankaproject.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardStatsDTO {
    private long totalProducts;
    private long ordersThisMonth;
    private BigDecimal totalRevenue;

    public DashboardStatsDTO(long totalProducts, long ordersThisMonth, BigDecimal totalRevenue) {
        this.totalProducts = totalProducts;
        this.ordersThisMonth = ordersThisMonth;
        this.totalRevenue = totalRevenue != null ? totalRevenue : BigDecimal.ZERO;
    }
}
