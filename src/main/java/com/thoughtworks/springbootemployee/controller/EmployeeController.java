package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.mapper.EmployeeRequestMapper;
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
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping(params = {"gender"})
    public List<EmployeeResponse> getEmployeesByGender(@RequestParam(value = "gender") String gender) {
        return employeeService.getEmployeesByGender(gender);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<EmployeeResponse> getEmployeesByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {
        return employeeService.getEmployeesByPage(page, pageSize);
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.addEmployee(employeeRequestMapper.toEmployee(employeeRequest));
    }

    @PutMapping("/{id}")
    public EmployeeResponse updateEmployeeById(@PathVariable Integer id, @RequestBody EmployeeRequest employeeRequest) throws NoSuchDataException {
        return employeeService.updateEmployeeById(id, employeeRequestMapper.toEmployee(employeeRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable Integer id) throws IllegalOperationException {
        employeeService.deleteEmployeeById(id);
    }
}
