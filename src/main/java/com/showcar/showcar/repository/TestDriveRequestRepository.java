package com.showcar.showcar.repository;

import com.showcar.showcar.model.TestDriveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestDriveRequestRepository extends JpaRepository<TestDriveRequest, Integer> {
    
    List<TestDriveRequest> findByCustomer_CustomerId(Integer customerId);
    
    List<TestDriveRequest> findByCar_CarId(Integer carId);
    
    List<TestDriveRequest> findByStatus(TestDriveRequest.TestDriveStatus status);
    
    List<TestDriveRequest> findByCustomer_CustomerIdAndStatus(Integer customerId, TestDriveRequest.TestDriveStatus status);
}
