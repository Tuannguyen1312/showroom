package com.showcar.showcar.service;

import com.showcar.showcar.dto.JwtResponseDTO;
import com.showcar.showcar.dto.LoginRequestDTO;
import com.showcar.showcar.dto.RegisterRequestDTO;
import com.showcar.showcar.model.Customer;
import com.showcar.showcar.model.UserAccount;
import com.showcar.showcar.repository.CustomerRepository;
import com.showcar.showcar.repository.UserAccountRepository;
import com.showcar.showcar.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserAccountRepository userAccountRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public JwtResponseDTO login(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        UserAccount userAccount = userAccountRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwt = tokenProvider.generateToken(userAccount.getUsername(), userAccount.getRole().name());
        
        JwtResponseDTO response = new JwtResponseDTO();
        response.setToken(jwt);
        response.setUsername(userAccount.getUsername());
        response.setRole(userAccount.getRole().name());
        response.setCustomerId(userAccount.getCustomer() != null ? userAccount.getCustomer().getCustomerId() : null);
        
        return response;
    }

    public JwtResponseDTO register(RegisterRequestDTO registerRequest) {
        // Check if username already exists
        if (userAccountRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        // Check if email already exists
        if (customerRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        // Create Customer
        Customer customer = new Customer();
        customer.setFullName(registerRequest.getFullName());
        customer.setPhone(registerRequest.getPhone());
        customer.setEmail(registerRequest.getEmail());
        customer.setAddress(registerRequest.getAddress());
        customer = customerRepository.save(customer);

        // Create UserAccount
        UserAccount userAccount = new UserAccount();
        userAccount.setCustomer(customer);
        userAccount.setUsername(registerRequest.getUsername());
        userAccount.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        userAccount.setRole(UserAccount.UserRole.customer);
        userAccount = userAccountRepository.save(userAccount);

        // Generate JWT token
        String jwt = tokenProvider.generateToken(userAccount.getUsername(), userAccount.getRole().name());

        JwtResponseDTO response = new JwtResponseDTO();
        response.setToken(jwt);
        response.setUsername(userAccount.getUsername());
        response.setRole(userAccount.getRole().name());
        response.setCustomerId(customer.getCustomerId());

        return response;
    }
}

