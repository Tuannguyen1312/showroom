package com.showcar.showcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Integer employeeId;
    private String fullName;
    private String email;
    private String phone;
    private String position; // "Administrator", "Sales_Consultant", "Marketing_Staff"
    private LocalDate hireDate;
    private Integer userAccountId;
}
