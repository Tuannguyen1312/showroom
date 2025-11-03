package com.showcar.showcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDTO {
    
    // Sales Statistics
    private BigDecimal totalRevenue;
    private BigDecimal totalDeposits;
    private Long totalOrders;
    private Long completedOrders;
    private Long pendingOrders;
    
    // Car Statistics
    private Long totalCars;
    private Long availableCars;
    private Long soldCars;
    
    // Customer Statistics
    private Long totalCustomers;
    private Long totalRegisteredUsers;
    
    // Monthly Revenue (Map: month -> revenue)
    private Map<String, BigDecimal> monthlyRevenue;
    
    // Top Selling Cars (Map: carName -> salesCount)
    private Map<String, Long> topSellingCars;
    
    // Sales by Brand (Map: brandName -> revenue)
    private Map<String, BigDecimal> salesByBrand;
}

