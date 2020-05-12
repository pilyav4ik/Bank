package com.bank.exceptions;

import lombok.Getter;

public class DepartmentNotFoundException extends RuntimeException {
    @Getter private final Long departmentId;

    public DepartmentNotFoundException(Long departmentId) {
        this.departmentId = departmentId;
    }
}
