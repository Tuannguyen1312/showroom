package com.showcar.showcar.service;

import com.showcar.showcar.dto.CustomerDTO;
import com.showcar.showcar.model.Customer;
import com.showcar.showcar.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {
    
    private final CustomerRepository customerRepository;
    
    // Lấy tất cả customers
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Lấy customer theo ID
    public CustomerDTO getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        return convertToDTO(customer);
    }
    
    // Tạo customer mới
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        if (customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new RuntimeException("Customer with email " + customerDTO.getEmail() + " already exists");
        }
        
        if (customerRepository.existsByPhone(customerDTO.getPhone())) {
            throw new RuntimeException("Customer with phone " + customerDTO.getPhone() + " already exists");
        }
        
        Customer customer = convertToEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return convertToDTO(savedCustomer);
    }
    
    // Cập nhật customer
    public CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        
        customer.setFullName(customerDTO.getFullName());
        customer.setPhone(customerDTO.getPhone());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());
        
        Customer updatedCustomer = customerRepository.save(customer);
        return convertToDTO(updatedCustomer);
    }
    
    // Xóa customer
    public void deleteCustomer(Integer id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
    
    // Tìm customer theo email
    public CustomerDTO findByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found with email: " + email));
        return convertToDTO(customer);
    }
    
    // Tìm customer theo phone
    public CustomerDTO findByPhone(String phone) {
        Customer customer = customerRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("Customer not found with phone: " + phone));
        return convertToDTO(customer);
    }
    
    // Convert Entity to DTO
    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerId(customer.getCustomerId());
        dto.setFullName(customer.getFullName());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        dto.setAddress(customer.getAddress());
        return dto;
    }
    
    // Convert DTO to Entity
    private Customer convertToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setCustomerId(dto.getCustomerId());
        customer.setFullName(dto.getFullName());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        return customer;
    }
}
