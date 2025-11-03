package com.showcar.showcar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_account_id")
    private Integer userAccountId;
    
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;
    
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role = UserRole.customer;
    
    @OneToOne(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private Employee employee;
    
    public enum UserRole {
        customer, admin, sales, marketing
    }
}
