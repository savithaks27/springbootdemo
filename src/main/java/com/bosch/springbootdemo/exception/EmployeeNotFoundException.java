package com.bosch.springbootdemo.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super("Could not find employee " + id);
    }

    public EmployeeNotFoundException(String name) {
        super("Could not find employee " + name);
    }
}
