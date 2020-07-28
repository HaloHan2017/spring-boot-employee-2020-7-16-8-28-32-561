package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees2")
public class Employee2Controller {
    @GetMapping
    public List<Employee> getAllEmployees() {
        return getEmployeesData();
    }

    private List<Employee> getEmployeesData() {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String gender = "male";
            if (i % 2 == 0) {
                gender = "female";
            }
            employees.add(new Employee(i + 1, "tom" + (i + 1), 20 + (i + 1), gender, 6000 + (i + 1)));
        }
        return employees;
    }
}
