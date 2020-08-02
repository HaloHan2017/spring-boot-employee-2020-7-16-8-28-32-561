package com.thoughtworks.springbootemployee.dto;

import com.thoughtworks.springbootemployee.model.Employee;

import java.util.List;

public class CompanyResponse {
    private Integer companyId;
    private String companyName;
    private Integer employeesNumber;
    private List<Employee> employees;

    public CompanyResponse() {
    }

    public CompanyResponse(Integer companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public CompanyResponse(Integer companyId, String companyName, Integer employeesNumber, List<Employee> employees) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.employeesNumber = employeesNumber;
        this.employees = employees;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(Integer employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
