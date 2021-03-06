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
import org.springframework.web.bind.annotation.PathVariable;
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

    public Page<EmployeeDto> getAllEmployeesByPage(Pageable pageable){
        Page<Employee> employeePage = employeePaginationRepository.findAll(pageable);
        return employeeMapper.employeeListToDtoPagible(employeePage);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> getAllEmployeesBySalaryAsc(){
        List<Employee> employeeList = employeeRepository.findAll(Sort.by("salary").ascending());
        return employeeMapper.entityListToDto(employeeList);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> getAllEmployeesBySalaryDesc(){
        List<Employee> employeeList = employeeRepository.findAll(Sort.by("salary").descending());
        return employeeMapper.entityListToDto(employeeList);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> getEmployeesByDepartment(@PathVariable Long department_id){
        List<Employee> employeeList =  employeeRepository.findAllByDepartmentId(department_id);
        return employeeMapper.entityListToDto(employeeList);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> getEmployeesByStreet(@PathVariable String street){
        List<Employee> employeeList = employeeRepository.findAllByStreet(street);
        return employeeMapper.entityListToDto(employeeList);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> getEmployeesBySalary(@PathVariable double salary){
        List<Employee> employeeList = employeeRepository.findAllBySalary(salary);
        return employeeMapper.entityListToDto(employeeList);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> getEmployeesByBank(@PathVariable String bankName){
        List<Employee> employeeList = employeeRepository.findAllByBankName(bankName);
        return employeeMapper.entityListToDto(employeeList);
    }

    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeById(Long id){
        Employee employee = employeeRepository.getOne(id);
        return employeeMapper.entityToDto(employee);
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
        employee.setUpdateDateTime(employeeDto.getUpdateDateTime());
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


    public List<EmployeeDto> saveListEmployeesService(Iterable<EmployeeDto> employeeList) {
        List<Employee> newEmployeesList = new ArrayList<>();
        for (EmployeeDto employeeDto : employeeList){
            Employee employee = new Employee();
            employee.setName(employeeDto.getName());
            employee.setDepartmentId(employeeDto.getDepartmentId());
            employee.setSalary(employeeDto.getSalary());
            employee.setCity(employeeDto.getCity());
            employee.setStreet(employeeDto.getStreet());
            employee.setCardNumber(employeeDto.getCardNumber());
            employee.setBankName(employeeDto.getBankName());
            newEmployeesList.add(employee);
        }
        employeeRepository.saveAll(newEmployeesList);
        return employeeMapper.entityListToDto(newEmployeesList);
    }


    public List<EmployeeDto> csvToEmployees( MultipartFile file) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Employee.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                List<Employee> users = csvToBean.parse();
                employeeRepository.saveAll(users);
                return employeeMapper.entityListToDto(users);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            return csvToEmployees(file);
    }


}