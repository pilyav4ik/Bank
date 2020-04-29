package com.bank.controller;

import com.bank.model.Department;
import com.bank.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Collection;

@RestController
@RequestMapping("/api")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping("/departments")
    Collection<Department> departments(){
        return service.getAllDepartments();
    }

    @GetMapping("departments/{id}")
    ResponseEntity<?> getDepartment(@PathVariable Long id){
        return service.getDepartmentById(id);
    }

    @PostMapping("/departments")
    ResponseEntity<Department> createDepartment(@Valid @RequestBody Department department) throws URISyntaxException{
        return service.createDepartment(department);
    }

    @PutMapping("departments/{id}")
    ResponseEntity<Department> updateDepartment(@Valid @RequestBody Department department){
        return service.updateDepartment(department);
    }

    @DeleteMapping("departments/{id}")
    ResponseEntity<?> deleteDepartment(@PathVariable Long id){
        return service.deleteDepartment(id);
    }
}
