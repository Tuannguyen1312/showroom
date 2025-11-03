package com.showcar.showcar.controller;

import com.showcar.showcar.dto.OrderDepositDTO;
import com.showcar.showcar.service.OrderDepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API Controller cho Order Deposit (Đơn đặt cọc)
 * Base URL: /api/orders
 * 
 * Chức năng:
 * - Đặt cọc trực tuyến
 * - Quản lý đơn đặt cọc
 * - Theo dõi trạng thái đơn hàng
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderDepositController {
    
    private final OrderDepositService orderDepositService;
    
    /**
     * Lấy tất cả đơn hàng (ADMIN)
     * GET /api/orders
     */
    @GetMapping
    public ResponseEntity<List<OrderDepositDTO>> getAllOrders() {
        List<OrderDepositDTO> orders = orderDepositService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    
    /**
     * Lấy chi tiết đơn hàng theo ID
     * GET /api/orders/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDepositDTO> getOrderById(@PathVariable Integer id) {
        try {
            OrderDepositDTO order = orderDepositService.getOrderById(id);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Lấy đơn hàng của khách hàng
     * GET /api/orders/customer/{customerId}
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDepositDTO>> getOrdersByCustomer(@PathVariable Integer customerId) {
        List<OrderDepositDTO> orders = orderDepositService.getOrdersByCustomer(customerId);
        return ResponseEntity.ok(orders);
    }
    
    /**
     * Lấy đơn hàng theo trạng thái (Pending, Deposited, Delivered, Canceled)
     * GET /api/orders/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDepositDTO>> getOrdersByStatus(@PathVariable String status) {
        try {
            List<OrderDepositDTO> orders = orderDepositService.getOrdersByStatus(status);
            return ResponseEntity.ok(orders);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Lấy đơn hàng của nhân viên (ADMIN/SALES)
     * GET /api/orders/employee/{employeeId}
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<OrderDepositDTO>> getOrdersByEmployee(@PathVariable Integer employeeId) {
        List<OrderDepositDTO> orders = orderDepositService.getOrdersByEmployee(employeeId);
        return ResponseEntity.ok(orders);
    }
    
    /**
     * Tạo đơn đặt cọc mới
     * POST /api/orders
     */
    @PostMapping
    public ResponseEntity<OrderDepositDTO> createOrder(@RequestBody OrderDepositDTO orderDTO) {
        try {
            OrderDepositDTO created = orderDepositService.createOrder(orderDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Cập nhật đơn hàng (ADMIN/SALES)
     * PUT /api/orders/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderDepositDTO> updateOrder(
            @PathVariable Integer id,
            @RequestBody OrderDepositDTO orderDTO) {
        try {
            OrderDepositDTO updated = orderDepositService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Hủy đơn hàng
     * DELETE /api/orders/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        try {
            orderDepositService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
