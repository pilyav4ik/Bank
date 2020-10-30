package com.bank.service;

import com.bank.dto.DepartmentDto;
import com.bank.exceptions.DepartmentNotFoundException;
import com.bank.mappers.DepartmentMapper;
import com.bank.model.Department;
import com.bank.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper){
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    public List<DepartmentDto> getAllDepartments(){
        List<Department> findAll = departmentRepository.findAll();
        return departmentMapper.entityListToDto(findAll);
    }

    public Department getDepartmentById(@PathVariable Long id){
        return departmentRepository.getOne(id);
    }

    public DepartmentDto createDepartment(DepartmentDto departmentDto){
        Department department = departmentMapper.dtoToEntity(departmentDto);
        department = departmentRepository.save(department);
        return departmentMapper.entityToDto(department);
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
