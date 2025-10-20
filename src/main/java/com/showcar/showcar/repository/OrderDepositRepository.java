package com.showcar.showcar.repository;

import com.showcar.showcar.model.OrderDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDepositRepository extends JpaRepository<OrderDeposit, String> {
    
    List<OrderDeposit> findByCustomerId(String customerId);
    
    List<OrderDeposit> findByCarId(String carId);
    
    List<OrderDeposit> findByEmployeeId(String employeeId);
    
    List<OrderDeposit> findByStatus(OrderDeposit.OrderStatus status);
    
    @Query("SELECT o FROM OrderDeposit o WHERE o.customer.id = :customerId AND o.status = :status")
    List<OrderDeposit> findByCustomerIdAndStatus(String customerId, OrderDeposit.OrderStatus status);
}
