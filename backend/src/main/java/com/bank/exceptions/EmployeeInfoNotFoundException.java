package com.bank.exceptions;

import lombok.Getter;

public class EmployeeInfoNotFoundException extends RuntimeException {
    @Getter
    private final Long id;

    public EmployeeInfoNotFoundException(Long id) {
        super("Employee not found");
        this.id = id;
    }
}
