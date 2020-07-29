package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private final List<Employee> employeeDataList = getEmployeesData();

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

    public Employee findEmployeeById(Integer id) {
        return employeeDataList.stream().filter(employee -> employee.getId() == id).findFirst().get();
    }

    public boolean addEmployee(Employee employee) {
        employee.setId(this.employeeDataList.size() + 1);
        return this.employeeDataList.add(employee);
    }

    public boolean deleteEmployeeById(Integer id) {
        return false;

    }}
