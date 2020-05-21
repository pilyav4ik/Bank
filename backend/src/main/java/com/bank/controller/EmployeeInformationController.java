package com.bank.controller;

import com.bank.dto.EmployeeInformationDto;
import com.bank.model.EmployeeInformation;
import com.bank.service.EmployeeInformationService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/employees")
public class EmployeeInformationController {

    private final EmployeeInformationService service;
    private final ModelMapper modelMapper;

    public EmployeeInformationController(EmployeeInformationService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }
    @GetMapping("/all/info")
    public Collection<EmployeeInformation> getAll(){ return service.getAll(); }

    @GetMapping("/{id}/info")
    public EmployeeInformationDto getEmployeeInformation(@PathVariable Long id) {
        return modelMapper.map(service.getEmployeeInformationById(id), EmployeeInformationDto.class);
    }

}
