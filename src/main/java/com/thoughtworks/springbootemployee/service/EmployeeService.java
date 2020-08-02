package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.mapper.EmployeeRequestMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeeResponseMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private CompanyService companyService;
    private EmployeeResponseMapper employeeResponseMapper;

    public EmployeeService(EmployeeRepository employeeRepository, CompanyService companyService, EmployeeResponseMapper employeeResponseMapper) {
        this.employeeRepository = employeeRepository;
        this.companyService = companyService;
        this.employeeResponseMapper = employeeResponseMapper;
    }

    public EmployeeResponse updateEmployeeById(int id, Employee updatedEmployee) throws NoSuchDataException {
        EmployeeResponse employeeResponse = employeeResponseMapper.toEmployeeResponse(employeeRepository.findById(id).orElse(null));
        if (Objects.isNull(employeeResponse)) {
            throw new NoSuchDataException();
        }
        addEmployeeToCompanyByCompanyId(updatedEmployee);
        updatedEmployee.setId(id);
        return employeeResponseMapper.toEmployeeResponse(employeeRepository.save(updatedEmployee));
    }

    private void addEmployeeToCompanyByCompanyId(Employee updatedEmployee) {
        if (Objects.isNull(updatedEmployee.getCompanyId())) {
            return;
        }
        CompanyResponse companyResponse = companyService.getCompanyById(updatedEmployee.getCompanyId());
        if (Objects.nonNull(companyResponse)) {
            List<Employee> employees = companyResponse.getEmployees();
            employees.add(updatedEmployee);
            companyResponse.setEmployees(employees);
            Company company = new Company();
            BeanUtils.copyProperties(companyResponse, company);
            companyService.addCompany(company);
        }
    }

    public EmployeeResponse getEmployeeById(Integer id) {
        return employeeResponseMapper.toEmployeeResponse(employeeRepository.findById(id).orElse(null));
    }

    public EmployeeResponse addEmployee(Employee employee) {
        addEmployeeToCompanyByCompanyId(employee);
        return employeeResponseMapper.toEmployeeResponse(employeeRepository.save(employee));
    }

    public void deleteEmployeeById(Integer id) throws IllegalOperationException {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (Objects.isNull(employee)) {
            throw new IllegalOperationException();
        }
        employeeRepository.deleteById(id);
    }

    public List<EmployeeResponse> getEmployeesByGender(String gender) {
        List<Employee> employeesByGender = employeeRepository.findByGender(gender);
        return employeesByGender.stream().map(employee -> employeeResponseMapper.toEmployeeResponse(employee)).collect(Collectors.toList());
    }

    public List<EmployeeResponse> getEmployeesByPage(Integer page, Integer pageSize) {
        if (Objects.nonNull(page) && Objects.nonNull(pageSize)) {
            Page<Employee> employeePages = employeeRepository.findAll(PageRequest.of(page, pageSize));
            return employeePages.stream().map(employeePage -> employeeResponseMapper.toEmployeeResponse(employeePage)).collect(Collectors.toList());
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<EmployeeResponse> getEmployeesByConditions(String gender, Integer page, Integer pageSize) {
        List<Employee> employees = getAllEmployees();
        if (Objects.nonNull(gender) && !gender.isEmpty()) {
            employees = employeeRepository.findByGender(gender);
        }
        Page<Employee> employeesByPage = employeeRepository.findAll(PageRequest.of(page, pageSize));
        if (Objects.nonNull(page) && Objects.nonNull(pageSize) && Objects.nonNull(employeesByPage)) {
            employees = employeesByPage.getContent();
        }
        return employees.stream().map(employee -> employeeResponseMapper.toEmployeeResponse(employee)).collect(Collectors.toList());
    }
}
