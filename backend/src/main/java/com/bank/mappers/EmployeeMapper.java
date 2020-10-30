package com.bank.mappers;

import com.bank.dto.EmployeeDto;
import com.bank.model.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    public EmployeeDto entityToDto(Employee employeeEntity){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(employeeEntity, EmployeeDto.class);
    }

    public  List<EmployeeDto> entityListToDto(List<Employee> employeeList) {
        return	employeeList.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public Employee dtoToEntity(EmployeeDto employeeDto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(employeeDto, Employee.class);
    }

    public List<Employee> dtoListToEntity(List<EmployeeDto> employeeDtoList){
        return employeeDtoList.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
