package com.showcar.showcar.controller;

import com.showcar.showcar.dto.OrderDepositDTO;
import com.showcar.showcar.model.OrderDeposit;
import com.showcar.showcar.service.OrderDepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderDepositController {
    
    private final OrderDepositService orderDepositService;
    
    @GetMapping
    public ResponseEntity<List<OrderDepositDTO>> getAllOrders() {
        return ResponseEntity.ok(orderDepositService.getAllOrders());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderDepositDTO> getOrderById(@PathVariable String id) {
        return ResponseEntity.ok(orderDepositService.getOrderById(id));
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDepositDTO>> getOrdersByCustomer(@PathVariable String customerId) {
        return ResponseEntity.ok(orderDepositService.getOrdersByCustomerId(customerId));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDepositDTO>> getOrdersByStatus(@PathVariable OrderDeposit.OrderStatus status) {
        return ResponseEntity.ok(orderDepositService.getOrdersByStatus(status));
    }
    
    @PostMapping
    public ResponseEntity<OrderDepositDTO> createOrder(@RequestBody OrderDepositDTO orderDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderDepositService.createOrder(orderDTO));
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDepositDTO> updateOrderStatus(
            @PathVariable String id,
            @RequestParam OrderDeposit.OrderStatus status) {
        return ResponseEntity.ok(orderDepositService.updateOrderStatus(id, status));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        orderDepositService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
