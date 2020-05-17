package com.bank.controller;

import com.bank.dto.EmployeeDto;
import com.bank.model.Employee;
import com.bank.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService service;
    private final ModelMapper modelMapper;

    public EmployeeController(EmployeeService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/employees")
    public Collection<Employee> employees() {
        return service.getAllEmployees();
    }

    @GetMapping("/employees/{id}")
    public EmployeeDto getEmployee(@PathVariable Long id) {
        return modelMapper.map(service.getEmployeeById(id), EmployeeDto.class);
    }

    @PostMapping("/employees")
    public Employee createEmployee(@Valid @RequestBody EmployeeDto employee){
        return service.createEmployee(employee);
    }

    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDto employee) {
        return service.updateEmployee(id, employee);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Long id) {
         service.deleteEmployee(id);
    }



}
