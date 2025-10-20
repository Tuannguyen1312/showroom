package com.showcar.showcar.repository;

import com.showcar.showcar.model.TestDriveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestDriveRequestRepository extends JpaRepository<TestDriveRequest, String> {
    
    List<TestDriveRequest> findByCustomerId(String customerId);
    
    List<TestDriveRequest> findByCarId(String carId);
    
    List<TestDriveRequest> findByStatus(TestDriveRequest.TestDriveStatus status);
    
    List<TestDriveRequest> findByCustomerIdAndStatus(String customerId, TestDriveRequest.TestDriveStatus status);
}
