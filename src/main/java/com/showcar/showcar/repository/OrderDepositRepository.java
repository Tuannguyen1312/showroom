package com.showcar.showcar.repository;

import com.showcar.showcar.model.OrderDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDepositRepository extends JpaRepository<OrderDeposit, Integer> {
    
    List<OrderDeposit> findByCustomer_CustomerId(Integer customerId);
    
    List<OrderDeposit> findByCar_CarId(Integer carId);
    
    List<OrderDeposit> findByEmployee_EmployeeId(Integer employeeId);
    
    List<OrderDeposit> findByStatus(OrderDeposit.OrderStatus status);
    
    @Query("SELECT o FROM OrderDeposit o WHERE o.customer.customerId = :customerId AND o.status = :status")
    List<OrderDeposit> findByCustomerAndStatus(@Param("customerId") Integer customerId, 
                                                @Param("status") OrderDeposit.OrderStatus status);
}
