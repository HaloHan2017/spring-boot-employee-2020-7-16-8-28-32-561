package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

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
    public Employee getEmployeeById(@PathVariable int id) {
        List<Employee> employees = getEmployeesData();
        Optional<Employee> employeeOptional = employees.stream().filter(employee -> id == employee.getId()).findFirst();
        if (employeeOptional.isPresent()) {
            return employeeOptional.get();
        }
        return null;
    }

    @PostMapping
    public String addEmployee(Employee employee) {
        List<Employee> employees = getEmployeesData();
        if (!Objects.isNull(employee)) {
            employees.add(employee);
            return "add success";
        }
        return "employee is null";
    }

    // todo
    @PutMapping("/{id}")
    public String updateEmployeeById(@PathVariable Integer id, @RequestBody Employee newEmployee) {
        List<Employee> employees = getEmployeesData();
        Optional<Employee> employeeOptional = employees.stream().filter(employee1 -> id == employee1.getId()).findFirst();
        if (employeeOptional.isPresent()) {
            employees.set(employees.indexOf(employeeOptional.get()), newEmployee);
            return "update success";
        }
        return "employee is null";
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
