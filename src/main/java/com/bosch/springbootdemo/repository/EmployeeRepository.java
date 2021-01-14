package com.bosch.springbootdemo.repository;

import com.bosch.springbootdemo.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByName(String name);

    boolean existsByName(String username);

    @Transactional
    void deleteByName(String username);
}
