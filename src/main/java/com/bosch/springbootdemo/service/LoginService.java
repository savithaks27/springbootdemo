package com.bosch.springbootdemo.service;

import com.bosch.springbootdemo.domain.Employee;
import com.bosch.springbootdemo.exception.CustomException;
import com.bosch.springbootdemo.repository.EmployeeRepository;
import com.bosch.springbootdemo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class LoginService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, employeeRepository.findByName(username).getRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String signup(Employee user) {
        if (!employeeRepository.existsByName(user.getName())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            employeeRepository.save(user);
            return jwtTokenProvider.createToken(user.getName(), user.getRoles());
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void delete(String username) {
        employeeRepository.deleteByName(username);
    }

    public Employee search(String username) {
        Employee user = employeeRepository.findByName(username);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public Employee whoami(HttpServletRequest req) {
        return employeeRepository.findByName(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    public String refresh(String username) {
        return jwtTokenProvider.createToken(username, employeeRepository.findByName(username).getRoles());
    }

}
