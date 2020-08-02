package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.mapper.CompanyResponseMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;
    private CompanyResponseMapper companyResponseMapper;

    public CompanyService(CompanyRepository companyRepository, CompanyResponseMapper companyResponseMapper) {
        this.companyRepository = companyRepository;
        this.companyResponseMapper = companyResponseMapper;
    }

    public List<CompanyResponse> getAllCompanies() {
        List<Company> allEmployees = companyRepository.findAll();
        return allEmployees.stream().map(employee -> companyResponseMapper.toCompanyResponse(employee)).collect(Collectors.toList());
    }

    public CompanyResponse getCompanyById(Integer id) {
        return companyResponseMapper.toCompanyResponse(companyRepository.findById(id).orElse(null));
    }

    public CompanyResponse addCompany(Company company) {
        return companyResponseMapper.toCompanyResponse(companyRepository.save(company));
    }

    public void deleteCompanyById(Integer id) throws NoSuchDataException {
        Company company = companyRepository.findById(id).orElse(null);
        if (Objects.isNull(company)) {
            throw new NoSuchDataException();
        }
        companyRepository.deleteById(id);
    }

    public List<CompanyResponse> getCompaniesByRange(int page, int pageSize) {
        if (page < 0 || pageSize <= 0) {
            return null;
        }
        Page<Company> companyPages = companyRepository.findAll(PageRequest.of(page, pageSize));
        return companyPages.stream().map(companyPage -> companyResponseMapper.toCompanyResponse(companyPage)).collect(Collectors.toList());
    }

    public List<CompanyResponse> getCompaniesByConditions(Integer page, Integer pageSize) {
        List<Company> companies = companyRepository.findAll();
        if (Objects.nonNull(page) && Objects.nonNull(pageSize)) {
            companies = companyRepository.findAll(PageRequest.of(page, pageSize)).getContent();
        }
        return companies.stream().map(company -> companyResponseMapper.toCompanyResponse(company)).collect(Collectors.toList());
    }

    public CompanyResponse updateCompanyById(Integer id, Company updateCompany) throws IllegalOperationException {
        Company company = companyRepository.findById(id).orElse(null);
        if (Objects.isNull(company)) {
            throw new IllegalOperationException();
        }
        company.setCompanyName(updateCompany.getCompanyName());
        return companyResponseMapper.toCompanyResponse(companyRepository.save(company));
    }
}
