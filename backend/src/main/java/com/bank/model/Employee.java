package com.bank.model;

import com.bank.dto.AbstractDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="employee")
public class Employee extends AbstractDto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    @CsvBindByName
    private Long id;

    @CsvBindByName
    private String name;
    @Column(name = "department_id")
    @CsvBindByName
    private Long departmentId;
    @CsvBindByName
    private double salary;

    @CsvBindByName
    private String city;
    @CsvBindByName
    private String street;
    @CsvBindByName
    private String bankName;
    @CsvBindByName
    private String cardNumber;
}
