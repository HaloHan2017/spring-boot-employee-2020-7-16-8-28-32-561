package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Integer id) {
        return companyRepository.findById(id).orElse(null);
    }

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public void deleteCompanyById(Integer id) throws NoSuchDataException {
        Company company = companyRepository.findById(id).orElse(null);
        if(Objects.isNull(company)){
            throw new NoSuchDataException();
        }
        companyRepository.deleteById(id);
    }

    public Page<Company> getCompaniesByRange(int page, int pageSize) {
        if(page < 0 || pageSize <= 0){
            return null;
        }
        return companyRepository.findAll(PageRequest.of(page, pageSize));
    }

    public List<Company> getCompaniesByConditions(Integer page, Integer pageSize) {
        List<Company> companies = getAllCompanies();
        if (Objects.nonNull(page) && Objects.nonNull(pageSize)) {
            companies = getCompaniesByRange(page, pageSize).getContent();
        }
        return companies;
    }

    public Company updateCompanyById(Integer id, Company updateCompany) throws IllegalOperationException {
        Company company = companyRepository.findById(id).orElse(null);
        if (Objects.isNull(company)) {
            throw new IllegalOperationException();
        }
        company.setCompanyName(updateCompany.getCompanyName());
        return companyRepository.save(company);
    }
}
