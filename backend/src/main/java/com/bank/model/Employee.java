package com.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="employee")
public class Employee {
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

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

}
