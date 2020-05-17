package com.bank.service;

import com.bank.dto.DepartmentDto;
import com.bank.exceptions.DepartmentNotFoundException;
import com.bank.model.Department;
import com.bank.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }

    public Collection<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(@PathVariable Long id){
        return departmentRepository.getOne(id);
    }

    public Department createDepartment(DepartmentDto departmentDto){
        Department department = new Department();
        department.setDepartmentName(departmentDto.getDepartmentName());
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, DepartmentDto departmentDto){
        Department department = departmentRepository.getById(id).orElseThrow(() -> new DepartmentNotFoundException(id));
        department.setDepartmentName(departmentDto.getDepartmentName());
        return departmentRepository.save(department);
    }

    public void deleteDepartment(@PathVariable Long id){
        departmentRepository.deleteById(id);
    }
}
