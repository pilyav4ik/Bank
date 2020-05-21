package com.bank.service;

import com.bank.model.EmployeeInformation;
import com.bank.repository.EmployeeInformationRepository;
import com.bank.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class EmployeeInformationService {

    private final EmployeeInformationRepository employeeInformationRepository;
    private final EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeInformationService(EmployeeInformationRepository employeeInformationRepository, EmployeeRepository employeeRepository) {
        this.employeeInformationRepository = employeeInformationRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeInformation> getAll(){return  employeeInformationRepository.findAll(); }

    public EmployeeInformation  getEmployeeInformationById(@PathVariable Long id){
        return employeeInformationRepository.getOne(id);
    }

}
