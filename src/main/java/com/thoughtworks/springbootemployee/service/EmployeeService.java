package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee updateEmployeeById(int id, Employee updatedEmployee) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            BeanUtils.copyProperties(updatedEmployee, employee, "id");
            return employeeRepository.save(employee);
        }
        return null;
    }

    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).get();
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployeeById(Integer id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public Page<Employee> getEmployeesByPage(Integer page, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(page, pageSize));
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
