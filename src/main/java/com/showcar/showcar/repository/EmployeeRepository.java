package com.showcar.showcar.repository;

import com.showcar.showcar.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    
    Optional<Employee> findByEmail(String email);
    
    List<Employee> findByPosition(Employee.EmployeePosition position);
    
    boolean existsByEmail(String email);
}
