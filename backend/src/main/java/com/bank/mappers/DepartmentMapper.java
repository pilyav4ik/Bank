package com.bank.mappers;


import com.bank.dto.DepartmentDto;
import com.bank.model.Department;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepartmentMapper {

    public DepartmentDto entityToDto(Department departmentEntity){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(departmentEntity, DepartmentDto.class);
    }

    public List<DepartmentDto> entityListToDto(List<Department> departmentsList) {
        return	departmentsList.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public Department dtoToEntity(DepartmentDto departmentDto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(departmentDto, Department.class);
    }

    public List<Department> dtoListToEntity(List<DepartmentDto> departmentDtoList){
        return departmentDtoList.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
