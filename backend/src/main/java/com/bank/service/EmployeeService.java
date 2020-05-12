package com.bank.service;

import com.bank.model.Employee;
import com.bank.repository.EmployeeRepository;
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
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Collection<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public ResponseEntity<?> getEmployeeById(@PathVariable Long id){
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) throws URISyntaxException {
        Employee result = employeeRepository.save(employee);
        return ResponseEntity.created(new URI("/api/employees" + result.getId())).body(result);
    }

    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody Employee employee){
        Employee result = employeeRepository.save(employee);
        return ResponseEntity.ok().body(result);
    }

    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        employeeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
