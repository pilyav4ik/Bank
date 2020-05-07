package com.bank.repository;

import com.bank.model.Department;
import com.bank.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}