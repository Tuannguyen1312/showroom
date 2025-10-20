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
public class CustomerService {
    
    private final CustomerRepository customerRepository;
    
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public CustomerDTO getCustomerById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        return convertToDTO(customer);
    }
    
    public CustomerDTO getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found with email: " + email));
        return convertToDTO(customer);
    }
    
    public CustomerDTO getCustomerByPhone(String phone) {
        Customer customer = customerRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("Customer not found with phone: " + phone));
        return convertToDTO(customer);
    }
    
    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        if (customerDTO.getEmail() != null && customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new RuntimeException("Customer already exists with email: " + customerDTO.getEmail());
        }
        
        if (customerDTO.getPhone() != null && customerRepository.existsByPhone(customerDTO.getPhone())) {
            throw new RuntimeException("Customer already exists with phone: " + customerDTO.getPhone());
        }
        
        Customer customer = new Customer();
        customer.setFullName(customerDTO.getFullName());
        customer.setPhone(customerDTO.getPhone());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());
        
        Customer savedCustomer = customerRepository.save(customer);
        return convertToDTO(savedCustomer);
    }
    
    @Transactional
    public CustomerDTO updateCustomer(String id, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        if (customerDTO.getFullName() != null) customer.setFullName(customerDTO.getFullName());
        if (customerDTO.getPhone() != null) customer.setPhone(customerDTO.getPhone());
        if (customerDTO.getEmail() != null) customer.setEmail(customerDTO.getEmail());
        if (customerDTO.getAddress() != null) customer.setAddress(customerDTO.getAddress());
        
        Customer updatedCustomer = customerRepository.save(customer);
        return convertToDTO(updatedCustomer);
    }
    
    @Transactional
    public void deleteCustomer(String id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found");
        }
        customerRepository.deleteById(id);
    }
    
    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setFullName(customer.getFullName());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        dto.setAddress(customer.getAddress());
        dto.setCreatedAt(customer.getCreatedAt());
        return dto;
    }
}
