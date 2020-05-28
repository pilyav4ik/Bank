package com.bank.controller;

import com.bank.dto.EmployeeInformationDto;
import com.bank.model.EmployeeInformation;
import com.bank.service.EmployeeInformationService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/employees/info")
public class EmployeeInformationController {

    private final EmployeeInformationService service;
    private final ModelMapper modelMapper;

    public EmployeeInformationController(EmployeeInformationService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }
    @GetMapping
    public Collection<EmployeeInformation> getAll(){ return service.getAll(); }


    @GetMapping("/{id}")
    public EmployeeInformationDto getEmployeeInformation(@PathVariable Long id) {
        return modelMapper.map(service.getEmployeeInformationById(id), EmployeeInformationDto.class);
    }
    @PostMapping()
    public EmployeeInformation createEmployeeInformation(@Valid @RequestBody EmployeeInformationDto employeeInformationDto) {
        return service.addEmployeeInformation(employeeInformationDto);
    }

    @PutMapping("/{id}")
    public EmployeeInformation updateEmployeeInformation(@PathVariable Long id, @Valid @RequestBody EmployeeInformationDto employeeInformationDto){
        return service.updateEmployeeInformation(id, employeeInformationDto);
    }

}
