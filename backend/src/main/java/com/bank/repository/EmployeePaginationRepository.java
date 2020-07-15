package com.bank.repository;

import com.bank.model.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeePaginationRepository extends PagingAndSortingRepository<Employee, Long> {

}
