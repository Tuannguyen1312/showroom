package com.showcar.showcar.service;

import com.showcar.showcar.dto.*;
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

    // REGISTER
    public RegisterResponseDTO register(RegisterRequestDTO request) {

        if (userAccountRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }

        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        // Create Customer
        Customer customer = new Customer();
        customer.setFullName(request.getFullName());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());
        customer.setAddress(request.getAddress());
        customer = customerRepository.save(customer);

        // Create UserAccount
        UserAccount userAccount = new UserAccount();
        userAccount.setCustomer(customer);
        userAccount.setUsername(request.getUsername());
        userAccount.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        userAccount.setRole(UserAccount.UserRole.customer);
        userAccount = userAccountRepository.save(userAccount);

        String token = tokenProvider.generateToken(userAccount.getUsername(), userAccount.getRole().name());

        return new RegisterResponseDTO(customer.getCustomerId(), userAccount.getUsername(),
                userAccount.getRole().name(), token);
    }

    // LOGIN
    public LoginResponseDTO login(LoginRequestDTO request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserAccount userAccount = userAccountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = tokenProvider.generateToken(userAccount.getUsername(), userAccount.getRole().name());

        return new LoginResponseDTO(
                userAccount.getCustomer() != null ? userAccount.getCustomer().getCustomerId() : null,
                userAccount.getUsername(),
                userAccount.getRole().name(),
                token
        );
    }
}
