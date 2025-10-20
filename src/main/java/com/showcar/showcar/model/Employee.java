package com.showcar.showcar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", length = 36)
    private String id;
    
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;
    
    @Column(name = "email", unique = true, length = 100)
    private String email;
    
    @Column(name = "phone", length = 20)
    private String phone;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private EmployeePosition position;
    
    @Column(name = "hire_date")
    private LocalDate hireDate;
    
    @OneToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<OrderDeposit> orderDeposits;
    
    public enum EmployeePosition {
        Administrator, 
        Sales_Consultant, 
        Marketing_Staff
    }
}
