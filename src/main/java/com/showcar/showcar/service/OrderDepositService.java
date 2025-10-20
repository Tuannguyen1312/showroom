package com.showcar.showcar.service;

import com.showcar.showcar.dto.OrderDepositDTO;
import com.showcar.showcar.model.OrderDeposit;
import com.showcar.showcar.model.Customer;
import com.showcar.showcar.model.Car;
import com.showcar.showcar.model.Employee;
import com.showcar.showcar.repository.OrderDepositRepository;
import com.showcar.showcar.repository.CustomerRepository;
import com.showcar.showcar.repository.CarRepository;
import com.showcar.showcar.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDepositService {
    
    private final OrderDepositRepository orderDepositRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;
    private final EmployeeRepository employeeRepository;
    
    public List<OrderDepositDTO> getAllOrders() {
        return orderDepositRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public OrderDepositDTO getOrderById(String id) {
        OrderDeposit order = orderDepositRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return convertToDTO(order);
    }
    
    public List<OrderDepositDTO> getOrdersByCustomerId(String customerId) {
        return orderDepositRepository.findByCustomerId(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<OrderDepositDTO> getOrdersByStatus(OrderDeposit.OrderStatus status) {
        return orderDepositRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public OrderDepositDTO createOrder(OrderDepositDTO orderDTO) {
        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        Car car = carRepository.findById(orderDTO.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found"));
        
        OrderDeposit order = new OrderDeposit();
        order.setCustomer(customer);
        order.setCar(car);
        
        if (orderDTO.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(orderDTO.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            order.setEmployee(employee);
        }
        
        order.setDepositAmount(orderDTO.getDepositAmount());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setStatus(orderDTO.getStatus());
        
        OrderDeposit savedOrder = orderDepositRepository.save(order);
        return convertToDTO(savedOrder);
    }
    
    @Transactional
    public OrderDepositDTO updateOrderStatus(String id, OrderDeposit.OrderStatus status) {
        OrderDeposit order = orderDepositRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setStatus(status);
        OrderDeposit updatedOrder = orderDepositRepository.save(order);
        return convertToDTO(updatedOrder);
    }
    
    @Transactional
    public void deleteOrder(String id) {
        if (!orderDepositRepository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }
        orderDepositRepository.deleteById(id);
    }
    
    private OrderDepositDTO convertToDTO(OrderDeposit order) {
        OrderDepositDTO dto = new OrderDepositDTO();
        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setCustomerName(order.getCustomer().getFullName());
        dto.setCarId(order.getCar().getId());
        dto.setCarName(order.getCar().getName());
        
        if (order.getEmployee() != null) {
            dto.setEmployeeId(order.getEmployee().getId());
            dto.setEmployeeName(order.getEmployee().getFullName());
        }
        
        dto.setOrderDate(order.getOrderDate());
        dto.setDepositAmount(order.getDepositAmount());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus());
        return dto;
    }
}
