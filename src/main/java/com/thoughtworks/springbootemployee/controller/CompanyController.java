package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @GetMapping()
    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1, "oocl"));
        return companies;
    }

    @GetMapping("/{companyId}")
    public Company getCompanyByNumber(@PathVariable int companyId) {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1, "oocl"));
        companies.add(new Company(2, "alibaba"));
        companies.add(new Company(3, "tencent"));
        for (Company company : companies) {
            if (companyId == company.getCompanyId()) {
                return company;
            }
        }
        return null;
    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> getEmployeesOfCompanyByNumber(@PathVariable int companyId) {
        List<Company> companies = new ArrayList<>();
        Company oocl = new Company(1, "oocl");
        List<Employee> employeesOfOocl = new ArrayList<>();
        employeesOfOocl.add(new Employee(4, "tom1", 20, "male", 6000));
        employeesOfOocl.add(new Employee(5, "tom2", 20, "male", 6001));
        employeesOfOocl.add(new Employee(6, "tom3", 20, "male", 6002));
        employeesOfOocl.add(new Employee(7, "tom4", 20, "male", 6003));
        oocl.setEmployees(employeesOfOocl);
        companies.add(oocl);

        companies.add(new Company(2, "alibaba"));
        companies.add(new Company(3, "tencent"));
        for (Company company : companies) {
            if (companyId == company.getCompanyId()) {
                return company.getEmployees();
            }
        }
        return null;
    }

    // todo unfinished
    @GetMapping()
    public List<Employee> getEmployeesOfCompanyByPage(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        List<Company> companies = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            companies.add(new Company(i + 1, "oocl" + (i + 1)));
        }

        return null;
    }
}
