package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
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

    @GetMapping
    public List<Company> getCompaniesByConditions(@RequestParam(value = "page", required = false) Integer page,
                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return companyService.getCompaniesByConditions(page, pageSize);
    }

    @GetMapping("/{companyId}")
    public Company getCompanyById(@PathVariable int companyId) {
        return companyService.getCompanyById(companyId);
    }

    @PostMapping
    public Company addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    @PutMapping("/{companyId}")
    public Company updateCompanyByNumber(@PathVariable Integer companyId, @RequestBody Company updateCompany) throws IllegalOperationException {
        return companyService.updateCompanyById(companyId, updateCompany);
    }

    @DeleteMapping("/{companyId}")
    public void deleteCompanyById(@PathVariable Integer companyId) throws NoSuchDataException {
        companyService.deleteCompanyById(companyId);
    }
}
