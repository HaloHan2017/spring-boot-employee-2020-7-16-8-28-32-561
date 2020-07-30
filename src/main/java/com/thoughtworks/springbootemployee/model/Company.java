package com.thoughtworks.springbootemployee.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;
    private String companyName;
    private Integer employeesNumber;
    @OneToMany(targetEntity = Employee.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Employee> employees;

    public Company() {
    }

    public Company(String companyName) {
        this.companyName = companyName;
    }

    public Company(int companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public Company(int companyId, String companyName, List<Employee> employees) {
        this.companyId = companyId;
        this.companyName = companyName;
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
