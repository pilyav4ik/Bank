package com.bank.repository;

import com.bank.model.EmployeeInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeInformationRepository extends JpaRepository<EmployeeInformation, Long> {


}
