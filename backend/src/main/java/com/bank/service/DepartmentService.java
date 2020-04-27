package com.bank.service;

import com.bank.model.Department;
import com.bank.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@Service
public class DepartmentService {
    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }

    public Collection<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

    public ResponseEntity<?> getDepartmentById(@PathVariable Long id){
        Optional<Department> department = departmentRepository.findById(id);
        return department.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Department> createDepartment(@Valid @RequestBody Department department) throws URISyntaxException {
        Department result = departmentRepository.save(department);
        return ResponseEntity.created(new URI("/api/department" + result.getId())).body(result);
    }

    public ResponseEntity<Department> updateDepartment(@Valid @RequestBody Department department){
        Department result = departmentRepository.save(department);
        return ResponseEntity.ok().body(result);
    }

    public ResponseEntity<?> deleteDepartment(@PathVariable Long id){
        departmentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
