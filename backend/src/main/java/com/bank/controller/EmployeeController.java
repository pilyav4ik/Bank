package com.bank.controller;

import com.bank.dto.EmployeeDto;
import com.bank.message.ResponseMessage;
import com.bank.model.Employee;
import com.bank.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService service;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeController(EmployeeService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/employees")
    public List<EmployeeDto> employees() {
        return service.getAllEmployees();
    }

    @GetMapping("/employees-page")
    public Page<EmployeeDto> employeesPaging (Pageable pageable){
        return service.getAllEmployeesByPage(pageable);
    }

    @GetMapping("/employees/{id}")
    public EmployeeDto getEmployee(@PathVariable Long id) {
        return modelMapper.map(service.getEmployeeById(id), EmployeeDto.class);
    }

    @PostMapping("/employees")
    public EmployeeDto createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        return service.createEmployee(employeeDto);
    }

    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDto employee) {
        return service.updateEmployee(id, employee);
    }

    @PutMapping("/employees-add-info/{id}")
    public Employee updateEmployeeWithInfo(@PathVariable Long id,
                                           @Valid
@RequestBody EmployeeDto employeeWithInfo) {
        return service.updateEmployeeWithInfo(id, employeeWithInfo);
    }
    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
    }


    @GetMapping("/employees=by-salary-asc")
    public List<EmployeeDto> employeesBySalaryAsc() {
        return service.getAllEmployeesBySalaryAsc();
    }

    @GetMapping("/employees=by-salary-desc")
    public List<EmployeeDto> employeesBySalaryDesc() {
        return service.getAllEmployeesBySalaryDesc();
    }

    @GetMapping("/employees/department={departmentId}")
    public List<EmployeeDto> employeesByDepartment(@PathVariable Long departmentId) {
        return service.getEmployeesByDepartment(departmentId);
    }

    @GetMapping("/employees/street={street}")
    public List<EmployeeDto> employeesByStreet(@PathVariable String street) {
        return service.getEmployeesByStreet(street);
    }

    @GetMapping("/employees/salary={salary}")
    public List<EmployeeDto> employeesBySalary(@PathVariable double salary) {
        return service.getEmployeesBySalary(salary);
    }

    @GetMapping("/employees/bank={bankName}")
    public List<EmployeeDto> employeesByBank(@PathVariable String bankName) {
        return service.getEmployeesByBank(bankName);
    }

    @PostMapping("/employees/save-all")
    public List<EmployeeDto> saveListEmployees(@Valid @RequestBody List<EmployeeDto> employeeDtoList) {
        return service.saveListEmployeesService(employeeDtoList);

    }

    @PostMapping("/employees/save-from-csv")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        ResponseEntity<ResponseMessage> result = null;
        boolean finished = false;
        String message = "";

            try {
                service.csvToEmployees(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                result = ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
                finished = true;
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                message += e;
                result = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ResponseMessage(message));
                finished = true;
            }

        if (!finished) {
            message = "Please upload a csv file!";
            result = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
        return result;
    }
}