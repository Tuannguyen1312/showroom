package com.showcar.showcar.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;


@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;


    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;


    @Column(name = "phone", unique = true, length = 20)
    private String phone;


    @Column(name = "email", unique = true, length = 100)
    private String email;


    @Column(name = "address", length = 255)
    private String address;


    // NO cascade on domain collections (option A - safe)
    @OneToOne(mappedBy = "customer")
    private UserAccount userAccount;


    @OneToMany(mappedBy = "customer")
    private List<TestDriveRequest> testDriveRequests;


    @OneToMany(mappedBy = "customer")
    private List<OrderDeposit> orderDeposits;


    @OneToMany(mappedBy = "customer")
    private List<FavoriteCar> favoriteCars;
}