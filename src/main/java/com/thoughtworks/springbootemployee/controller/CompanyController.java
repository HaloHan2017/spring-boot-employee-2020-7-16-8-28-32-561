package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping()
    public List<Company> getCompaniesByConditions(@RequestParam(value = "page", required = false) Integer page,
                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return companyService.getCompaniesByConditions(page, pageSize);
    }

    @GetMapping("/{companyId}")
    public Company getCompanyByNumber(@PathVariable int companyId) {
        return companyService.getCompanyById(companyId);
    }

//    @GetMapping("/{companyId}/employees")
//    public List<Employee> getEmployeesOfCompanyByNumber(@PathVariable int companyId) {
//        return null;
//    }

    @PostMapping()
    public Company addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    @PutMapping("/{companyId}")
    public String updateCompanyByNumber(@PathVariable Integer companyId, Company newCompany) {
        return "update success!";
    }

    @DeleteMapping("/{companyId}")
    public void deleteCompanyById(@PathVariable Integer companyId) {
        companyService.deleteCompanyById(companyId);
    }
}
