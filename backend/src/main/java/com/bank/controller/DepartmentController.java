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
    public  DepartmentDto createDepartment(@Valid @RequestBody DepartmentDto departmentDto){
        Department department = new Department();
        department.setDepartmentName(departmentDto.getDepartmentName());
        Department departmentEntity = modelMapper.map(departmentDto, Department.class);
        return modelMapper.map(service.createDepartment(departmentEntity), DepartmentDto.class);
    }

    @PutMapping("/departments/{id}")
    public DepartmentDto updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentDto departmentDto){
        Department department = new Department();
        department.setDepartmentName(departmentDto.getDepartmentName());
        return modelMapper.map(service.updateDepartment(id, department), DepartmentDto.class);
    }

    @DeleteMapping("/departments/{id}")
    public void deleteDepartment(@PathVariable Long id){
        service.deleteDepartment(id);
    }
}
