package com.bank.service;

import com.bank.dto.EmployeeDto;
import com.bank.exceptions.EmployeeNotFoundException;
import com.bank.model.Employee;
import com.bank.repository.EmployeePaginationRepository;
import com.bank.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeePaginationRepository employeePaginationRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper, EmployeePaginationRepository employeePaginationRepository) {
        this.employeeRepository = employeeRepository;
        this.employeePaginationRepository = employeePaginationRepository;
    }

    public Collection<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Page<Employee> getAllEmployeesByPage(Pageable pageable){
        return employeePaginationRepository.findAll(pageable);
    }
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployeesBySalaryAsc(){
        return employeeRepository.findAll(Sort.by("salary").ascending());
    }
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployeesBySalaryDesc(){
        return employeeRepository.findAll(Sort.by("salary").descending());
    }

    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByDepartment(@PathVariable Long department_id){
        return employeeRepository.findAllByDepartmentId(department_id);
    }

    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByStreet(@PathVariable String street){
        return employeeRepository.findAllByStreet(street);
    }

    @Transactional(readOnly = true)
    public List<Employee> getEmployeesBySalary(@PathVariable double salary){
        return employeeRepository.findAllBySalary(salary);
    }

    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByBank(@PathVariable String bankName){
        return employeeRepository.findAllByBankName(bankName);
    }

    public Employee getEmployeeById(@PathVariable Long id){
        return employeeRepository.getOne(id);
    }

    public Employee createEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, EmployeeDto employeeDto){
        Employee employee = employeeRepository.getById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        employee.setName(employeeDto.getName());
        employee.setDepartmentId(employeeDto.getDepartmentId());
        employee.setSalary(employeeDto.getSalary());
        return employeeRepository.save(employee);
    }

    public Employee updateEmployeeWithInfo(Long id, EmployeeDto employeeDto){
        Employee employee = employeeRepository.getById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        employee.setName(employeeDto.getName());
        employee.setDepartmentId(employeeDto.getDepartmentId());
        employee.setSalary(employeeDto.getSalary());

        employee.setCity(employeeDto.getCity());
        employee.setStreet(employeeDto.getStreet());
        employee.setCardNumber(employeeDto.getCardNumber());
        employee.setBankName(employeeDto.getBankName());
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(@PathVariable Long id){
        employeeRepository.deleteById(id);
    }


    public List<Employee> saveListEmployeesService(Iterable<EmployeeDto> employeeList) {
        List<Employee> newEmployeesList = new ArrayList<>();
        for (EmployeeDto employeeDto : employeeList){
            Employee employee = new Employee();
            employee.setName(employeeDto.getName());
            employee.setDepartmentId(employeeDto.getDepartmentId());
            employee.setSalary(employeeDto.getSalary());
            newEmployeesList.add(employee);
        }
        return employeeRepository.saveAll(newEmployeesList);
    }
}