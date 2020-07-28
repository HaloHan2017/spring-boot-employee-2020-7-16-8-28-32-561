package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @GetMapping()
    public List<Company> getAllCompanies(@RequestParam(value = "page", required = false) Integer page,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Company> companies = getCompaniesData();
        if (Objects.nonNull(page) && Objects.nonNull(pageSize)) {
            companies = companies.stream().skip((page - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
        }
        return companies;
    }

    @GetMapping("/{companyId}")
    public Company getCompanyByNumber(@PathVariable int companyId) {
        List<Company> companies = getCompaniesData();
        for (Company company : companies) {
            if (companyId == company.getCompanyId()) {
                return company;
            }
        }
        return null;
    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> getEmployeesOfCompanyByNumber(@PathVariable int companyId) {
        List<Company> companies = getCompaniesData();
        Company oocl = new Company(11, "oocl");
        List<Employee> employeesOfOocl = new ArrayList<>();
        employeesOfOocl.add(new Employee(4, "tom1", 20, "male", 6000));
        employeesOfOocl.add(new Employee(5, "tom2", 20, "male", 6001));
        employeesOfOocl.add(new Employee(6, "tom3", 20, "male", 6002));
        employeesOfOocl.add(new Employee(7, "tom4", 20, "male", 6003));
        oocl.setEmployees(employeesOfOocl);
        companies.add(oocl);
        Optional<Company> companyOptional = companies.stream().filter(company -> companyId == company.getCompanyId()).findFirst();

        return companyOptional.isPresent() ? companyOptional.get().getEmployees() : null;
    }

    @PostMapping()
    public String addCompany(Company company) {
        List<Company> companies = getCompaniesData();
        companies.add(company);
        return "add success!";
    }

    // todo bad request
    @PutMapping("/{companyId}")
    public String updateCompanyByNumber(@PathVariable Integer companyId, @RequestParam("companyName") String companyName) {
        List<Company> companies = getCompaniesData();
        Company company = null;
        for (Company tempCompany : companies) {
            if (companyId == tempCompany.getCompanyId()) {
                company = tempCompany;
            }
        }
        if (Objects.isNull(company)) {
            return "companyId is not exist";
        }
        company.setCompanyName(companyName);
        return "update success!";
    }

    @DeleteMapping("/{companyId}")
    public String deleteCompanyById(@PathVariable int companyId) {
        List<Company> companies = getCompaniesData();
        int deleteIndex = -1;
        for (Company company : companies) {
            if (companyId == company.getCompanyId()) {
                deleteIndex = companies.indexOf(company);
            }
        }
        if (deleteIndex < 0) {
            return "companyId is not exist";
        }
        companies.remove(deleteIndex);
        return "delete success!";
    }

    private List<Company> getCompaniesData() {
        List<Company> companies = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            companies.add(new Company(i + 1, "oocl" + (i + 1)));
        }
        return companies;
    }
}
