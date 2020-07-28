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

    private List<Employee> getEmployeesData() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(4, "tom1", 20, "male", 6000));
        employees.add(new Employee(5, "tom2", 20, "female", 6001));
        employees.add(new Employee(6, "tom3", 20, "male", 6002));
        employees.add(new Employee(7, "tom4", 20, "male", 6003));
        return employees;
    }

    @GetMapping
    public List<Employee> getAllEmployees(@RequestParam(value = "gender", required = false) String gender) {
        List<Employee> employees = getEmployeesData();
        if (!gender.isEmpty()) {
            employees = employees.stream().filter(employee -> gender.equals(employee.getGender())).collect(Collectors.toList());
        }
        return employees;
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        List<Employee> employees = getEmployeesData();
        for (Employee employee : employees) {
            if (id == employee.getId()) {
                return employee;
            }
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

    // todo 405
    @PutMapping("/{id}")
    public String updateEmployeeById(@PathVariable Integer id, Employee newEmployee) {
        List<Employee> employees = getEmployeesData();
        Optional<Employee> employeeOptional = employees.stream().filter(employee1 -> id == employee1.getId()).findFirst();
        if (employeeOptional.isPresent()) {
            Employee employee1 = employeeOptional.get();
            employee1 = newEmployee;
            employees.add(employee1);
            return "update success";
        }
        return "employee is null";
    }

    @DeleteMapping("/{id}")
    public String deleteEmployeeById(@PathVariable int id) {
        List<Employee> employees = getEmployeesData();
        Employee findEmployeeById = employees.stream().filter(employee -> id == employee.getId()).findFirst().get();
        if(employees.remove(findEmployeeById)){
            return "delete success";
        }
        return "delete failed";
    }
}
