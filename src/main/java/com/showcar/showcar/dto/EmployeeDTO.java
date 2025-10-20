package com.showcar.showcar.dto;

import com.showcar.showcar.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private Employee.EmployeePosition position;
    private LocalDate hireDate;
    private String userAccountId;
}
