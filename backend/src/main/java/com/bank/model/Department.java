package com.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "department")
public class Department {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", nullable = false)
    private Long id;

    private String departmentName;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    private Set<Employee> employees;
}
