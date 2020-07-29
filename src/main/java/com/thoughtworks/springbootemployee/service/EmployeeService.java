package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee updateEmployeeById(int id, Employee updatedEmployee) {
        Employee employee = employeeRepository.findEmployeeById(id);
        BeanUtils.copyProperties(updatedEmployee, employee, "id");
        return employee;
    }

    public Employee getEmployeeById(int id) {
        return null;
    }
}
