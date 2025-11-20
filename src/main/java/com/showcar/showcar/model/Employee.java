package com.showcar.showcar.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer employeeId;


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


    // Employee is the owner of the 1-1 relation to UserAccount
    @OneToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;


    // NO cascade on order deposits (business data)
    @OneToMany(mappedBy = "employee")
    private List<OrderDeposit> orderDeposits;


    public enum EmployeePosition {
        Administrator,
        Sales_Consultant,
        Marketing_Staff
    }
}