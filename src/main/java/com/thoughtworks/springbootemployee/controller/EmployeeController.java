package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees(@RequestParam(value = "gender", required = false) String gender,
                                          @RequestParam(value = "page", required = false) Integer page,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Employee> employees = getEmployeesData();
        if (Objects.nonNull(gender) && !gender.isEmpty()) {
            employees = employees.stream().filter(employee -> gender.equals(employee.getGender())).collect(Collectors.toList());
        }
        if (Objects.nonNull(page) && Objects.nonNull(pageSize)) {
            employees = employees.stream().skip((page - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
        }
        return employees;
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public String addEmployee(Employee employee) {
       if (employeeService.addEmployee(employee) > 0) {
           return " add success";
       }
       return " add failed";
    }

    @PutMapping("/{id}")
    public Employee updateEmployeeById(@PathVariable Integer id, @RequestBody Employee updatedEmployee) {
        return employeeService.updateEmployeeById(id, updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployeeById(@PathVariable int id) {
        List<Employee> employees = getEmployeesData();
        Employee findEmployeeById = employees.stream().filter(employee -> id == employee.getId()).findFirst().get();
        if (employees.remove(findEmployeeById)) {
            return "delete success";
        }
        return "delete failed";
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
