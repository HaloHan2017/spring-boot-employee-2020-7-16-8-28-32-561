package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.mapper.EmployeeRequestMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private EmployeeRequestMapper employeeRequestMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeRequestMapper employeeRequestMapper) {
        this.employeeService = employeeService;
        this.employeeRequestMapper = employeeRequestMapper;
    }

    @GetMapping
    public List<Employee> getEmployeesByConditions(@RequestParam(value = "gender", required = false) String gender,
                                                   @RequestParam(value = "page", required = false) Integer page,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return employeeService.getEmployeesByConditions(gender, page, pageSize);
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.addEmployee(employeeRequestMapper.toEmployee(employeeRequest));
    }

    @PutMapping("/{id}")
    public Employee updateEmployeeById(@PathVariable Integer id, @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.updateEmployeeById(id, employeeRequestMapper.toEmployee(employeeRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable Integer id) throws IllegalOperationException {
        employeeService.deleteEmployeeById(id);
    }
}
