package com.springboot.chatgpt.repository;

import com.springboot.chatgpt.dto.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
