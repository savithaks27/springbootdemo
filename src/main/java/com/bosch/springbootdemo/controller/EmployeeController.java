package com.bosch.springbootdemo.controller;

import com.bosch.springbootdemo.domain.Employee;
import com.bosch.springbootdemo.dto.EmployeeDto;
import com.bosch.springbootdemo.exception.EmployeeNotFoundException;
import com.bosch.springbootdemo.repository.EmployeeRepository;
import com.bosch.springbootdemo.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeRepository repository;

    private final EmployeeService service;

    public EmployeeController(EmployeeRepository repository, EmployeeService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return repository.findAll();
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody EmployeeDto newEmployeeDto) {
        return service.createEmployee(newEmployeeDto);
    }

    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@RequestBody EmployeeDto newEmployeeDto, @PathVariable Long id) {
        return service.updateEmployee(newEmployeeDto, id);
    }

    @PatchMapping("/employees/{id}")
    public Employee partialUpdateEmployee(@RequestBody EmployeeDto newEmployeeDto, @PathVariable Long id) {
        return service.updateEmployee(newEmployeeDto, id);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
