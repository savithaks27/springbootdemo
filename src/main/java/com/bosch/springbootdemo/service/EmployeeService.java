package com.bosch.springbootdemo.service;

import com.bosch.springbootdemo.domain.Employee;
import com.bosch.springbootdemo.dto.EmployeeDto;
import com.bosch.springbootdemo.exception.EmployeeNotFoundException;
import com.bosch.springbootdemo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService (EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(EmployeeDto newEmployeeDto) {
        Employee employee = new Employee();
        employee.setName(newEmployeeDto.getName());
        employee.setFirstname(newEmployeeDto.getFirstname());
        employee.setLastname(newEmployeeDto.getLastname());
        employee.setPassword(newEmployeeDto.getPassword());
        employee.setEmail(newEmployeeDto.getEmail());
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(EmployeeDto newEmployeeDto, Long id) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployeeDto.getName());
                    employee.setFirstname(newEmployeeDto.getFirstname());
                    employee.setLastname(newEmployeeDto.getLastname());
                    employee.setPassword(newEmployeeDto.getPassword());
                    employee.setEmail(newEmployeeDto.getEmail());
                    return employeeRepository.save(employee);
                })
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }


}
