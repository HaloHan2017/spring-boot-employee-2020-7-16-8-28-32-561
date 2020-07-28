package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
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
        companies.add(new Company(1,"oocl"));
        return companies;
    }

    @GetMapping("/{companyId}")
    public Company getCompanyByNumber(@PathVariable int companyId) {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1,"oocl"));
        companies.add(new Company(2,"alibaba"));
        companies.add(new Company(3,"tencent"));

        for (Company company : companies) {
            if(companyId == company.getCompanyId()){
                return company;
            }
        }
        return null;
    }
}
