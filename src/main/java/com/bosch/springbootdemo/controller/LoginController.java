package com.bosch.springbootdemo.controller;

import org.modelmapper.ModelMapper;
import com.bosch.springbootdemo.domain.Employee;
import com.bosch.springbootdemo.dto.EmployeeDto;
import com.bosch.springbootdemo.dto.LoginUserDto;
import com.bosch.springbootdemo.service.LoginService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class LoginController {

    @Value("${welcome.message}")
    private String welcomeMessage;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/welcome")
    public String showWelcomeMessage() {
        return welcomeMessage;
    }

    @RequestMapping(value = { "/", "/login1" })
    public String staticResource(Model model) {
        return "login1";
    }

    @PostMapping("/signin")
    @ApiOperation(value = "${LoginController.signin}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 422, message = "Invalid username/password supplied")})
    public String login(//
                        @ApiParam("Username") @RequestParam String username, //
                        @ApiParam("Password") @RequestParam String password) {
        return loginService.signin(username, password);
    }

    @PostMapping("/signup")
    @ApiOperation(value = "${LoginController.signup}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 422, message = "Username is already in use")})
    public String signup(@ApiParam("Signup User") @RequestBody EmployeeDto user) {
        return loginService.signup(modelMapper.map(user, Employee.class));
    }

    @DeleteMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${LoginController.delete}", authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 404, message = "The user doesn't exist"), //
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public String delete(@ApiParam("Username") @PathVariable String username) {
        loginService.delete(username);
        return username;
    }

    @GetMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${LoginController.search}", response = LoginUserDto.class, authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 404, message = "The user doesn't exist"), //
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public LoginUserDto search(@ApiParam("Username") @PathVariable String username) {
        return modelMapper.map(loginService.search(username), LoginUserDto.class);
    }

    @GetMapping(value = "/me")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${LoginController.me}", response = LoginUserDto.class, authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public LoginUserDto whoami(HttpServletRequest req) {
        return modelMapper.map(loginService.whoami(req), LoginUserDto.class);
    }

    @GetMapping("/refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public String refresh(HttpServletRequest req) {
        return loginService.refresh(req.getRemoteUser());
    }
}
