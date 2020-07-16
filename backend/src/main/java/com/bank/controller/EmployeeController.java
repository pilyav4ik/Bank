package com.bank.controller;

import com.bank.dto.EmployeeDto;
import com.bank.model.Employee;
import com.bank.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService service;
    private final ModelMapper modelMapper;

    private static final int BUTTONS_TO_SHOW = 5;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = {5, 10, 20};

    @Autowired
    public EmployeeController(EmployeeService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/employees")
    public List<EmployeeDto> employees() {
        return service.getAllEmployees()
                .stream()
                .map(e -> modelMapper.map(e, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/employees-page")
    public Page<Employee> employeesPaging (Pageable pageable){
        return service.getAllEmployeesByPage(pageable);
    }

    @GetMapping("/employees/{id}")
    public EmployeeDto getEmployee(@PathVariable Long id) {
        return modelMapper.map(service.getEmployeeById(id), EmployeeDto.class);
    }

    @PostMapping("/employees")
    public EmployeeDto createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setDepartmentId(employeeDto.getDepartmentId());
        employee.setSalary(employeeDto.getSalary());
        Employee employeeEntity = modelMapper.map(employeeDto, Employee.class);
        return modelMapper.map(service.createEmployee(employeeEntity), EmployeeDto.class);
    }

    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDto employee) {
        return service.updateEmployee(id, employee);
    }

    @PutMapping("/employees-add-info/{id}")
    public Employee updateEmployeeWithInfo(@PathVariable Long id,
                                           @Valid @RequestBody EmployeeDto employeeWithInfo) {
        return service.updateEmployeeWithInfo(id, employeeWithInfo);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
    }


    @GetMapping("/employees=by-salary-asc")
    public List<Employee> employeesBySalaryAsc() {
        return service.getAllEmployeesBySalaryAsc();
    }

    @GetMapping("/employees=by-salary-desc")
    public List<Employee> employeesBySalaryDesc() {
        return service.getAllEmployeesBySalaryDesc();
    }

    @GetMapping("/employees/department={departmentId}")
    public List<Employee> employeesByDepartment(@PathVariable Long departmentId) {
        return service.getEmployeesByDepartment(departmentId);
    }

    @GetMapping("/employees/street={street}")
    public List<Employee> employeesByStreet(@PathVariable String street) {
        return service.getEmployeesByStreet(street);
    }

    @GetMapping("/employees/salary={salary}")
    public List<Employee> employeesBySalary(@PathVariable double salary) {
        return service.getEmployeesBySalary(salary);
    }

    @GetMapping("/employees/bank={bankName}")
    public List<Employee> employeesByBank(@PathVariable String bankName) {
        return service.getEmployeesByBank(bankName);
    }

    @PostMapping("/employees/save-all")
    public List<Employee> saveListEmployees(@Valid @RequestBody List<EmployeeDto> employeeDtoList) {
        return service.saveListEmployeesService(employeeDtoList);

    }
}