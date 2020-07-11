package com.bank.repository;

import com.bank.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> getById(Long id);

    List<Employee> findAllByDepartmentId(Long department_id);
    List<Employee> findAllByStreet(String street);
    List<Employee> findAllBySalary(double salary);
    List<Employee> findAllByBankName(String bankName);
}