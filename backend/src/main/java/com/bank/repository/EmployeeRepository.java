package com.bank.repository;

import com.bank.model.Department;
import com.bank.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Collection<Employee> findEmployeeByDepartment(Department department);

    @Query(value = "SELECT * FROM Employee e WHERE e.salary ORDER BY e.salary ASC ", nativeQuery = true)
    Collection<Employee> sortEmployeeBySalaryAsc();

    @Query(value = "SELECT * FROM Employee e WHERE e.salary ORDER BY e.salary DESC", nativeQuery = true)
    Collection<Employee> sortEmployeeBySalaryDesc();
}