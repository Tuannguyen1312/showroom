package com.showcar.showcar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", length = 36)
    private String id;
    
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;
    
    @Column(name = "phone", unique = true, length = 20)
    private String phone;
    
    @Column(name = "email", unique = true, length = 100)
    private String email;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private UserAccount userAccount;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<TestDriveRequest> testDriveRequests;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<OrderDeposit> orderDeposits;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<FavoriteCar> favoriteCars;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
