package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // todo
    public Employee updateEmployeeById(int id, Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (Objects.nonNull(employee)) {
            employee.setName(updatedEmployee.getName());
            employee.setAge(updatedEmployee.getAge());
            employee.setGender(updatedEmployee.getGender());
            employee.setSalary(updatedEmployee.getSalary());
//            BeanUtils.copyProperties();
            return employeeRepository.save(employee);
        }
        return null;
    }

    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployeeById(Integer id) throws IllegalOperationException {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if(Objects.isNull(employee)){
            throw new IllegalOperationException();
        }
        employeeRepository.deleteById(id);
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public Page<Employee> getEmployeesByPage(Integer page, Integer pageSize) {
        if(Objects.nonNull(page) && Objects.nonNull(pageSize)){
            return employeeRepository.findAll(PageRequest.of(page, pageSize));
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeesByConditions(String gender, Integer page, Integer pageSize) {
        // todo code
        List<Employee> employees = getAllEmployees();
        if (Objects.nonNull(gender) && !gender.isEmpty()) {
            employees = getEmployeesByGender(gender);
        }
        Page<Employee> employeesByPage = getEmployeesByPage(page, pageSize);
        if (Objects.nonNull(page) && Objects.nonNull(pageSize) && Objects.nonNull(employeesByPage)) {
            employees = employeesByPage.getContent();
        }
        return employees;
    }
}
