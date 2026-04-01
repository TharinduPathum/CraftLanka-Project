package lk.ijse.javafx.craftlankaproject.service.impl;

import lk.ijse.javafx.craftlankaproject.dto.DashboardStatsDTO;
import lk.ijse.javafx.craftlankaproject.repository.CraftProductRepository;
import lk.ijse.javafx.craftlankaproject.repository.OrderRepository;
import lk.ijse.javafx.craftlankaproject.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private CraftProductRepository craftProductRepository;

    @Autowired
    private OrderRepository orderRepository;

    public DashboardStatsDTO getSellerStats() {

        long totalProducts = craftProductRepository.count();

        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        long ordersThisMonth = orderRepository.countByOrderDateAfter(startOfMonth);

        BigDecimal totalRevenue = orderRepository.sumTotalRevenue();

        return new DashboardStatsDTO(totalProducts, ordersThisMonth, totalRevenue);
    }
}
