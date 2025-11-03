package com.showcar.showcar.service;

import com.showcar.showcar.dto.StatisticsDTO;
import com.showcar.showcar.model.Car;
import com.showcar.showcar.model.OrderDeposit;
import com.showcar.showcar.repository.CarRepository;
import com.showcar.showcar.repository.CustomerRepository;
import com.showcar.showcar.repository.OrderDepositRepository;
import com.showcar.showcar.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsService {

    private final OrderDepositRepository orderDepositRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final UserAccountRepository userAccountRepository;

    public StatisticsDTO getDashboardStatistics() {
        StatisticsDTO stats = new StatisticsDTO();

        // Sales Statistics
        List<OrderDeposit> allOrders = orderDepositRepository.findAll();
        List<OrderDeposit> completedOrders = allOrders.stream()
                .filter(o -> o.getStatus() == OrderDeposit.OrderStatus.Delivered)
                .toList();
        List<OrderDeposit> pendingOrders = allOrders.stream()
                .filter(o -> o.getStatus() == OrderDeposit.OrderStatus.Pending || 
                            o.getStatus() == OrderDeposit.OrderStatus.Deposited)
                .toList();

        BigDecimal totalRevenue = completedOrders.stream()
                .map(o -> o.getTotalPrice() != null ? o.getTotalPrice() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDeposits = allOrders.stream()
                .filter(o -> o.getDepositAmount() != null)
                .map(OrderDeposit::getDepositAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        stats.setTotalRevenue(totalRevenue);
        stats.setTotalDeposits(totalDeposits);
        stats.setTotalOrders((long) allOrders.size());
        stats.setCompletedOrders((long) completedOrders.size());
        stats.setPendingOrders((long) pendingOrders.size());

        // Car Statistics
        List<Car> allCars = carRepository.findAll();
        long availableCars = allCars.stream()
                .filter(c -> c.getStatus() == Car.CarStatus.Available)
                .count();
        long soldCars = allCars.stream()
                .filter(c -> c.getStatus() == Car.CarStatus.Sold)
                .count();

        stats.setTotalCars((long) allCars.size());
        stats.setAvailableCars(availableCars);
        stats.setSoldCars(soldCars);

        // Customer Statistics
        stats.setTotalCustomers(customerRepository.count());
        stats.setTotalRegisteredUsers(userAccountRepository.count());

        // Monthly Revenue (last 12 months)
        stats.setMonthlyRevenue(calculateMonthlyRevenue());

        // Top Selling Cars
        stats.setTopSellingCars(calculateTopSellingCars());

        // Sales by Brand
        stats.setSalesByBrand(calculateSalesByBrand());

        return stats;
    }

    private Map<String, BigDecimal> calculateMonthlyRevenue() {
        Map<String, BigDecimal> monthlyRevenue = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        
        LocalDateTime now = LocalDateTime.now();
        for (int i = 11; i >= 0; i--) {
            LocalDateTime monthStart = now.minusMonths(i).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime monthEnd = monthStart.plusMonths(1).minusSeconds(1);
            
            String monthKey = monthStart.format(formatter);
            
            BigDecimal revenue = orderDepositRepository.findAll().stream()
                    .filter(o -> o.getStatus() == OrderDeposit.OrderStatus.Delivered &&
                               o.getOrderDate() != null &&
                               o.getOrderDate().isAfter(monthStart) &&
                               o.getOrderDate().isBefore(monthEnd))
                    .map(o -> o.getTotalPrice() != null ? o.getTotalPrice() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            monthlyRevenue.put(monthKey, revenue.setScale(2, RoundingMode.HALF_UP));
        }
        
        return monthlyRevenue;
    }

    private Map<String, Long> calculateTopSellingCars() {
        return orderDepositRepository.findAll().stream()
                .filter(o -> o.getStatus() == OrderDeposit.OrderStatus.Delivered && o.getCar() != null)
                .collect(Collectors.groupingBy(
                        o -> o.getCar().getName(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private Map<String, BigDecimal> calculateSalesByBrand() {
        return orderDepositRepository.findAll().stream()
                .filter(o -> o.getStatus() == OrderDeposit.OrderStatus.Delivered &&
                           o.getCar() != null &&
                           o.getCar().getBrand() != null &&
                           o.getTotalPrice() != null)
                .collect(Collectors.groupingBy(
                        o -> o.getCar().getBrand().getName(),
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                o -> o.getTotalPrice(),
                                BigDecimal::add
                        )
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().setScale(2, RoundingMode.HALF_UP),
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}

