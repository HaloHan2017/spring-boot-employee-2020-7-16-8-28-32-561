package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @GetMapping()
    public List<Company> getAllCompanies(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
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
//    @GetMapping()
    public List<Employee> getEmployeesOfCompanyByPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        List<Company> companies = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            companies.add(new Company(i + 1, "oocl" + (i + 1)));
        }

        return null;
    }

    @PostMapping()
    public String addCompany(Company company) {

        return "add success! " + company.getCompanyName();
    }

    // todo bad request
    @PutMapping("/{companyId}")
    public String updateCompanyByNumber(@PathVariable Integer companyId, @RequestParam("companyName") String companyName) {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1, "oocl"));
        companies.add(new Company(2, "alibaba"));
        companies.add(new Company(3, "tencent"));
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
    public String deleteCompanyById(@PathVariable int companyId){
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1, "oocl"));
        companies.add(new Company(2, "alibaba"));
        companies.add(new Company(3, "tencent"));
        int deleteIndex = -1;
        for (Company company : companies) {
            if(companyId == company.getCompanyId()){
                deleteIndex = companies.indexOf(company);
            }
        }
        if (deleteIndex < 0){
            return "companyId is not exist";
        }
        companies.remove(deleteIndex);
        return "delete success!";
    }
}
