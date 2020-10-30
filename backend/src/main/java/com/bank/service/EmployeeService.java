package com.bank.service;

import com.bank.dto.EmployeeDto;
import com.bank.exceptions.EmployeeNotFoundException;
import com.bank.mappers.EmployeeMapper;
import com.bank.model.Employee;
import com.bank.repository.EmployeePaginationRepository;
import com.bank.repository.EmployeeRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final EmployeePaginationRepository employeePaginationRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, EmployeePaginationRepository employeePaginationRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.employeePaginationRepository = employeePaginationRepository;
    }

    public List<EmployeeDto> getAllEmployees(){
        List<Employee> findAll = employeeRepository.findAll();
        return employeeMapper.entityListToDto(findAll);
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

    public EmployeeDto createEmployee(EmployeeDto employeeDto){
        Employee employee = employeeMapper.dtoToEntity(employeeDto);
        employee = employeeRepository.save(employee);
        return employeeMapper.entityToDto(employee);
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


    public String csvToEmployees(@RequestParam("file") MultipartFile file, Model model) {
        // validate file
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        } else {

            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Employee.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                List users = csvToBean.parse();
                employeeRepository.saveAll(users);

                model.addAttribute("users", users);
                model.addAttribute("status", true);

            } catch (Exception ex) {
                model.addAttribute("message", "An error occurred while processing the CSV file.");
                model.addAttribute("status", false);
            }
        }

        return "file-upload-status";
    }

    /*public List<Employee> saveListEmployeesFromCSVToDBService(Iterable<Employee> employeeList) {
        List<Employee> newEmployeesList = new ArrayList<>();
        for (Employee employeeInput : employeeList){
            Employee employee = new Employee();
            employee.setName(employeeInput.getName());
            employee.setDepartmentId(employeeInput.getDepartmentId());
            employee.setSalary(employeeInput.getSalary());

            employee.setCity(employeeInput.getCity());
            employee.setStreet(employeeInput.getStreet());
            employee.setCardNumber(employeeInput.getCardNumber());
            employee.setBankName(employeeInput.getBankName());
            newEmployeesList.add(employee);
        }
        return employeeRepository.saveAll(newEmployeesList);
    }*/

}