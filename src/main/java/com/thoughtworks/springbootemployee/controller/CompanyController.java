package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.mapper.CompanyRequestMapper;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyRequestMapper companyRequestMapper;

    public CompanyController(CompanyService companyService, CompanyRequestMapper companyRequestMapper) {
        this.companyService = companyService;
        this.companyRequestMapper = companyRequestMapper;
    }

    @GetMapping
    public List<CompanyResponse> getCompaniesByConditions(@RequestParam(value = "page", required = false) Integer page,
                                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return companyService.getCompaniesByConditions(page, pageSize);
    }

    @GetMapping("/{companyId}")
    public CompanyResponse getCompanyById(@PathVariable int companyId) {
        return companyService.getCompanyById(companyId);
    }

    @PostMapping
    public CompanyResponse addCompany(@RequestBody CompanyRequest companyRequest) {
        return companyService.addCompany(companyRequestMapper.toCompany(companyRequest));
    }

    @PutMapping("/{companyId}")
    public CompanyResponse updateCompanyByNumber(@PathVariable Integer companyId, @RequestBody CompanyRequest companyRequest) throws IllegalOperationException {
        return companyService.updateCompanyById(companyId, companyRequestMapper.toCompany(companyRequest));
    }

    @DeleteMapping("/{companyId}")
    public void deleteCompanyById(@PathVariable Integer companyId) throws NoSuchDataException {
        companyService.deleteCompanyById(companyId);
    }
}
