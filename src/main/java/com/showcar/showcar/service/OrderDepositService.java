package com.showcar.showcar.service;

import com.showcar.showcar.dto.OrderDepositDTO;
import com.showcar.showcar.model.*;
import com.showcar.showcar.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderDepositService {
    
    private final OrderDepositRepository orderDepositRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;
    
    // Lấy tất cả orders
    public List<OrderDepositDTO> getAllOrders() {
        return orderDepositRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Lấy order theo ID
    public OrderDepositDTO getOrderById(Integer id) {
        OrderDeposit order = orderDepositRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return convertToDTO(order);
    }
    
    // Tạo order mới
    public OrderDepositDTO createOrder(OrderDepositDTO orderDTO) {
        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        Car car = carRepository.findById(orderDTO.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found"));
        
        OrderDeposit order = convertToEntity(orderDTO);
        order.setCustomer(customer);
        order.setCar(car);
        
        if (orderDTO.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(orderDTO.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            order.setEmployee(employee);
        }
        
        OrderDeposit savedOrder = orderDepositRepository.save(order);
        
        // Send confirmation email
        if (customer.getEmail() != null && !customer.getEmail().isEmpty()) {
            try {
                String depositAmount = savedOrder.getDepositAmount() != null ? 
                    savedOrder.getDepositAmount().toString() : "0";
                emailService.sendOrderConfirmationEmail(
                    customer.getEmail(),
                    customer.getFullName(),
                    car.getName(),
                    savedOrder.getOrderDepositId().toString(),
                    depositAmount
                );
            } catch (Exception e) {
                // Log error but don't fail the transaction
                System.err.println("Failed to send email: " + e.getMessage());
            }
        }
        
        return convertToDTO(savedOrder);
    }
    
    // Cập nhật order
    public OrderDepositDTO updateOrder(Integer id, OrderDepositDTO orderDTO) {
        OrderDeposit order = orderDepositRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        
        order.setDepositAmount(orderDTO.getDepositAmount());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setStatus(OrderDeposit.OrderStatus.valueOf(orderDTO.getStatus()));
        
        if (orderDTO.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(orderDTO.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            order.setEmployee(employee);
        }
        
        OrderDeposit updatedOrder = orderDepositRepository.save(order);
        return convertToDTO(updatedOrder);
    }
    
    // Xóa order
    public void deleteOrder(Integer id) {
        if (!orderDepositRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderDepositRepository.deleteById(id);
    }
    
    // Lấy orders theo customer
    public List<OrderDepositDTO> getOrdersByCustomer(Integer customerId) {
        return orderDepositRepository.findByCustomer_CustomerId(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Lấy orders theo status
    public List<OrderDepositDTO> getOrdersByStatus(String status) {
        OrderDeposit.OrderStatus orderStatus = OrderDeposit.OrderStatus.valueOf(status);
        return orderDepositRepository.findByStatus(orderStatus).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Lấy orders theo employee
    public List<OrderDepositDTO> getOrdersByEmployee(Integer employeeId) {
        return orderDepositRepository.findByEmployee_EmployeeId(employeeId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Convert Entity to DTO
    private OrderDepositDTO convertToDTO(OrderDeposit order) {
        OrderDepositDTO dto = new OrderDepositDTO();
        dto.setOrderDepositId(order.getOrderDepositId());
        dto.setCustomerId(order.getCustomer().getCustomerId());
        dto.setCustomerName(order.getCustomer().getFullName());
        dto.setCarId(order.getCar().getCarId());
        dto.setCarName(order.getCar().getName());
        
        if (order.getEmployee() != null) {
            dto.setEmployeeId(order.getEmployee().getEmployeeId());
            dto.setEmployeeName(order.getEmployee().getFullName());
        }
        
        dto.setOrderDate(order.getOrderDate());
        dto.setDepositAmount(order.getDepositAmount());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus().name());
        return dto;
    }
    
    // Convert DTO to Entity
    private OrderDeposit convertToEntity(OrderDepositDTO dto) {
        OrderDeposit order = new OrderDeposit();
        order.setOrderDepositId(dto.getOrderDepositId());
        order.setOrderDate(dto.getOrderDate());
        order.setDepositAmount(dto.getDepositAmount());
        order.setTotalPrice(dto.getTotalPrice());
        if (dto.getStatus() != null) {
            order.setStatus(OrderDeposit.OrderStatus.valueOf(dto.getStatus()));
        }
        return order;
    }
}
