package com.bank.exceptions;

import lombok.Getter;

public class EmployeeNotFoundException extends RuntimeException {
    @Getter
    private final Long id;

    public EmployeeNotFoundException(Long id) {
        super("Employee not found");
        this.id = id;
    }
}
