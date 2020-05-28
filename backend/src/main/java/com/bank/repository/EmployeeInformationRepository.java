package com.bank.repository;

import com.bank.model.Employee;
import com.bank.model.EmployeeInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeInformationRepository extends JpaRepository<EmployeeInformation, Long> {
    Optional<Employee> getById(Long id);
}
