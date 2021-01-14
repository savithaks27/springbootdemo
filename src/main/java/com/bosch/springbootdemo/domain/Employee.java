package com.bosch.springbootdemo.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Table(name="EMPLOYEE")
@Entity
@Data
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "PASSWORD", nullable = false)
    @Size(min = 8, message = "Minimum password length: 8 characters")
    private String password;

    @NotNull(message = "First Name cannot be null")
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstname;

    @NotNull(message = "Last Name cannot be null")
    @Column(name = "LAST_NAME", nullable = false)
    private String lastname;

    @Email(message = "Email should be valid")
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    List<Role> roles;

}