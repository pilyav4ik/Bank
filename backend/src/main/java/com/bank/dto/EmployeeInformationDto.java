package com.bank.dto;

import com.bank.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeInformationDto{

    private Long id;

    private Employee employee;

    private String city;
    private String street;
    private String bankName;
    private String cardNumber;

}
