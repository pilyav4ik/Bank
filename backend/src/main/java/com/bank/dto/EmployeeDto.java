package com.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmployeeDto extends AbstractDto{

    private Long id;

    private String name;
    private Long departmentId;
    private double salary;
    private String city;
    private String street;
    private String bankName;
    private String cardNumber;
}
