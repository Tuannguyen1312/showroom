package com.showcar.showcar.controller;

import com.showcar.showcar.dto.TestDriveRequestDTO;
import com.showcar.showcar.service.TestDriveRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API Controller cho Test Drive Request (Yêu cầu lái thử)
 * Base URL: /api/test-drives
 * 
 * Chức năng:
 * - Đặt lịch lái thử
 * - Quản lý yêu cầu lái thử
 * - Theo dõi trạng thái lái thử
 */
@RestController
@RequestMapping("/api/test-drives")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TestDriveRequestController {
    
    private final TestDriveRequestService testDriveRequestService;
    
    /**
     * Lấy tất cả yêu cầu lái thử (ADMIN)
     * GET /api/test-drives
     */
    @GetMapping
    public ResponseEntity<List<TestDriveRequestDTO>> getAllTestDriveRequests() {
        List<TestDriveRequestDTO> requests = testDriveRequestService.getAllTestDriveRequests();
        return ResponseEntity.ok(requests);
    }
    
    /**
     * Lấy chi tiết yêu cầu lái thử theo ID
     * GET /api/test-drives/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TestDriveRequestDTO> getTestDriveRequestById(@PathVariable Integer id) {
        try {
            TestDriveRequestDTO request = testDriveRequestService.getTestDriveRequestById(id);
            return ResponseEntity.ok(request);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Lấy yêu cầu lái thử của khách hàng
     * GET /api/test-drives/customer/{customerId}
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<TestDriveRequestDTO>> getRequestsByCustomer(@PathVariable Integer customerId) {
        List<TestDriveRequestDTO> requests = testDriveRequestService.getRequestsByCustomer(customerId);
        return ResponseEntity.ok(requests);
    }
    
    /**
     * Lấy yêu cầu theo trạng thái (Pending, Confirmed, Completed, Canceled)
     * GET /api/test-drives/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TestDriveRequestDTO>> getRequestsByStatus(@PathVariable String status) {
        try {
            List<TestDriveRequestDTO> requests = testDriveRequestService.getRequestsByStatus(status);
            return ResponseEntity.ok(requests);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Tạo yêu cầu lái thử mới
     * POST /api/test-drives
     */
    @PostMapping
    public ResponseEntity<TestDriveRequestDTO> createTestDriveRequest(@RequestBody TestDriveRequestDTO requestDTO) {
        try {
            TestDriveRequestDTO created = testDriveRequestService.createTestDriveRequest(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Cập nhật yêu cầu lái thử (ADMIN/SALES)
     * PUT /api/test-drives/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TestDriveRequestDTO> updateTestDriveRequest(
            @PathVariable Integer id,
            @RequestBody TestDriveRequestDTO requestDTO) {
        try {
            TestDriveRequestDTO updated = testDriveRequestService.updateTestDriveRequest(id, requestDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Hủy yêu cầu lái thử
     * DELETE /api/test-drives/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestDriveRequest(@PathVariable Integer id) {
        try {
            testDriveRequestService.deleteTestDriveRequest(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
