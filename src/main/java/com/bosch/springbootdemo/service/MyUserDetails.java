package com.bosch.springbootdemo.service;

import com.bosch.springbootdemo.domain.Employee;
import com.bosch.springbootdemo.exception.EmployeeNotFoundException;
import com.bosch.springbootdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetails implements UserDetailsService {


    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws EmployeeNotFoundException {
        final Employee emp = employeeRepository.findByName(username);

        if (emp == null) {
            throw new EmployeeNotFoundException(username);
        }

        return org.springframework.security.core.userdetails.User//
                .withUsername(username)//
                .password(emp.getPassword())//
//                .authorities(user.getRoles())//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }
}
