package com.bank.controller;

import com.bank.dto.DepartmentDto;
import com.bank.model.Department;
import com.bank.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api")
public class DepartmentController {

    private final DepartmentService service;
    private final ModelMapper modelMapper;

    public DepartmentController(DepartmentService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/departments")
    public Collection<Department> departments(){
        return service.getAllDepartments();
    }

    @GetMapping("/departments/{id}")
    public DepartmentDto getDepartment(@PathVariable Long id){
        return modelMapper.map(service.getDepartmentById(id), DepartmentDto.class);
    }

    @PostMapping("/departments")
    public  Department createDepartment(@RequestBody DepartmentDto department){
        return service.createDepartment(department);
    }

    @PutMapping("/departments/{id}")
    public Department updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentDto department){
        return service.updateDepartment(id, department);
    }

    @DeleteMapping("/departments/{id}")
    public void deleteDepartment(@PathVariable Long id){
        service.deleteDepartment(id);
    }
}
