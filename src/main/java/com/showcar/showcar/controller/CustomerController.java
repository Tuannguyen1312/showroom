package com.showcar.showcar.controller;

import com.showcar.showcar.dto.CustomerDTO;
import com.showcar.showcar.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API Controller cho Customer (Khách hàng)
 * Base URL: /api/customers
 * 
 * Chức năng:
 * - Đăng ký khách hàng
 * - Quản lý thông tin khách hàng
 * - Danh sách khách hàng (ADMIN)
 */
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerController {
    
    private final CustomerService customerService;
    
    /**
     * Lấy tất cả khách hàng (ADMIN)
     * GET /api/customers
     */
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
    
    /**
     * Lấy thông tin khách hàng theo ID
     * GET /api/customers/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer id) {
        try {
            CustomerDTO customer = customerService.getCustomerById(id);
            return ResponseEntity.ok(customer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Tìm khách hàng theo email
     * GET /api/customers/email/{email}
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerDTO> findByEmail(@PathVariable String email) {
        try {
            CustomerDTO customer = customerService.findByEmail(email);
            return ResponseEntity.ok(customer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Tìm khách hàng theo số điện thoại
     * GET /api/customers/phone/{phone}
     */
    @GetMapping("/phone/{phone}")
    public ResponseEntity<CustomerDTO> findByPhone(@PathVariable String phone) {
        try {
            CustomerDTO customer = customerService.findByPhone(phone);
            return ResponseEntity.ok(customer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Đăng ký khách hàng mới
     * POST /api/customers/register
     */
    @PostMapping("/register")
    public ResponseEntity<CustomerDTO> registerCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            CustomerDTO created = customerService.createCustomer(customerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Cập nhật thông tin khách hàng
     * PUT /api/customers/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable Integer id,
            @RequestBody CustomerDTO customerDTO) {
        try {
            CustomerDTO updated = customerService.updateCustomer(id, customerDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Xóa khách hàng (ADMIN)
     * DELETE /api/customers/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
