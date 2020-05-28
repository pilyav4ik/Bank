package com.bank.service;

import com.bank.dto.EmployeeInformationDto;
import com.bank.exceptions.EmployeeInfoNotFoundException;
import com.bank.model.EmployeeInformation;
import com.bank.repository.EmployeeInformationRepository;
import com.bank.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
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

    public EmployeeInformation addEmployeeInformation(@Valid EmployeeInformationDto employeeInformationDto){

        EmployeeInformation employeeInformation = new EmployeeInformation();
        employeeInformation.setStreet(employeeInformationDto.getStreet());
        employeeInformation.setCity(employeeInformationDto.getCity());
        employeeInformation.setBankName(employeeInformationDto.getBankName());
        employeeInformation.setCardNumber(employeeInformationDto.getCardNumber());
        employeeInformation.setEmployee(employeeInformationDto.getEmployee());
        return employeeInformationRepository.save(employeeInformation);
    }

    public EmployeeInformation updateEmployeeInformation(Long id, EmployeeInformationDto employeeInformationDto){

        EmployeeInformation employeeInformation = employeeInformationRepository.findById(id).orElseThrow(() -> new EmployeeInfoNotFoundException(id));
        employeeInformation.setStreet(employeeInformationDto.getStreet());
        employeeInformation.setCity(employeeInformationDto.getCity());
        employeeInformation.setBankName(employeeInformationDto.getBankName());
        employeeInformation.setCardNumber(employeeInformationDto.getCardNumber());
        employeeInformation.setEmployee(employeeInformationDto.getEmployee());
        return employeeInformationRepository.save(employeeInformation);
    }
}
