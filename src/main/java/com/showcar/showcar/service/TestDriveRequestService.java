package com.showcar.showcar.service;

import com.showcar.showcar.dto.TestDriveRequestDTO;
import com.showcar.showcar.model.Car;
import com.showcar.showcar.model.Customer;
import com.showcar.showcar.model.TestDriveRequest;
import com.showcar.showcar.repository.CarRepository;
import com.showcar.showcar.repository.CustomerRepository;
import com.showcar.showcar.repository.TestDriveRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TestDriveRequestService {
    
    private final TestDriveRequestRepository testDriveRequestRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;
    private final EmailService emailService;
    
    // Lấy tất cả test drive requests
    public List<TestDriveRequestDTO> getAllTestDriveRequests() {
        return testDriveRequestRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Lấy test drive request theo ID
    public TestDriveRequestDTO getTestDriveRequestById(Integer id) {
        TestDriveRequest request = testDriveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test drive request not found with id: " + id));
        return convertToDTO(request);
    }
    
    // Tạo test drive request mới
    public TestDriveRequestDTO createTestDriveRequest(TestDriveRequestDTO requestDTO) {
        Customer customer = customerRepository.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        Car car = carRepository.findById(requestDTO.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found"));
        
        TestDriveRequest request = convertToEntity(requestDTO);
        request.setCustomer(customer);
        request.setCar(car);
        
        TestDriveRequest savedRequest = testDriveRequestRepository.save(request);
        
        // Send confirmation email
        if (customer.getEmail() != null && !customer.getEmail().isEmpty()) {
            try {
                String preferredDate = savedRequest.getPreferredDate() != null ? 
                    savedRequest.getPreferredDate().toString() : "Chưa xác định";
                emailService.sendTestDriveConfirmationEmail(
                    customer.getEmail(),
                    customer.getFullName(),
                    car.getName(),
                    preferredDate
                );
            } catch (Exception e) {
                // Log error but don't fail the transaction
                System.err.println("Failed to send email: " + e.getMessage());
            }
        }
        
        return convertToDTO(savedRequest);
    }
    
    // Cập nhật test drive request
    public TestDriveRequestDTO updateTestDriveRequest(Integer id, TestDriveRequestDTO requestDTO) {
        TestDriveRequest request = testDriveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test drive request not found with id: " + id));
        
        request.setPreferredDate(requestDTO.getPreferredDate());
        request.setStatus(TestDriveRequest.TestDriveStatus.valueOf(requestDTO.getStatus()));
        request.setNote(requestDTO.getNote());
        
        TestDriveRequest updatedRequest = testDriveRequestRepository.save(request);
        return convertToDTO(updatedRequest);
    }
    
    // Xóa test drive request
    public void deleteTestDriveRequest(Integer id) {
        if (!testDriveRequestRepository.existsById(id)) {
            throw new RuntimeException("Test drive request not found with id: " + id);
        }
        testDriveRequestRepository.deleteById(id);
    }
    
    // Lấy requests theo customer
    public List<TestDriveRequestDTO> getRequestsByCustomer(Integer customerId) {
        return testDriveRequestRepository.findByCustomer_CustomerId(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Lấy requests theo status
    public List<TestDriveRequestDTO> getRequestsByStatus(String status) {
        TestDriveRequest.TestDriveStatus requestStatus = TestDriveRequest.TestDriveStatus.valueOf(status);
        return testDriveRequestRepository.findByStatus(requestStatus).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Convert Entity to DTO
    private TestDriveRequestDTO convertToDTO(TestDriveRequest request) {
        TestDriveRequestDTO dto = new TestDriveRequestDTO();
        dto.setTestDriveRequestId(request.getTestDriveRequestId());
        dto.setCustomerId(request.getCustomer().getCustomerId());
        dto.setCustomerName(request.getCustomer().getFullName());
        dto.setCarId(request.getCar().getCarId());
        dto.setCarName(request.getCar().getName());
        dto.setPreferredDate(request.getPreferredDate());
        dto.setStatus(request.getStatus().name());
        dto.setNote(request.getNote());
        dto.setCreatedAt(request.getCreatedAt());
        return dto;
    }
    
    // Convert DTO to Entity
    private TestDriveRequest convertToEntity(TestDriveRequestDTO dto) {
        TestDriveRequest request = new TestDriveRequest();
        request.setTestDriveRequestId(dto.getTestDriveRequestId());
        request.setPreferredDate(dto.getPreferredDate());
        if (dto.getStatus() != null) {
            request.setStatus(TestDriveRequest.TestDriveStatus.valueOf(dto.getStatus()));
        }
        request.setNote(dto.getNote());
        return request;
    }
}
