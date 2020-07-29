package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CompanyServiceTest {
    @Test
    void should_return_companies_when_get_companies_given_no_parameter() {
        // given
        CompanyRepository mockedCompanyRepository = mock(CompanyRepository.class);
        given(mockedCompanyRepository.findAll()).willReturn(
                Arrays.asList(new Company(1, "alibaba", Arrays.asList(new Employee(1, "sss", 20, "male", 200), new Employee(2, "fff", 50, "male", 5000)))));
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        // when
        List<Company> companies = companyService.getAllCompanies();
        // then
        assertEquals(1, companies.size());
    }


    @Test
    void should_return_company_when_get_company_by_id_given_id() {
        // given
        CompanyRepository mockedCompanyRepository = mock(CompanyRepository.class);
        given(mockedCompanyRepository.findById(1)).willReturn(
                Optional.of(new Company(1, "alibaba",
                        Arrays.asList(new Employee(1, "sss", 20, "male", 200),
                                new Employee(2, "fff", 50, "male", 5000)))));
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        // when
        Company company = companyService.getCompanyById(1);
        // then
        assertEquals("alibaba", company.getCompanyName());
    }

    @Test
    void should_return_company_when_add_company_given_company() {
        // given
        Company company = new Company(1, "alibaba",
                Arrays.asList(new Employee(1, "sss", 20, "male", 200),
                        new Employee(2, "fff", 50, "male", 5000)));
        CompanyRepository mockedCompanyRepository = mock(CompanyRepository.class);
        given(mockedCompanyRepository.save(company)).willReturn(
                new Company(1, "alibaba",
                        Arrays.asList(new Employee(1, "sss", 20, "male", 200),
                                new Employee(2, "fff", 50, "male", 5000))));
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        // when
        Company newCompany = companyService.addCompany(company);
        // then
        assertEquals(company.getCompanyName(), newCompany.getCompanyName());
    }

    @Test
    void should_return_void_when_update_company_given_company_id_and_company() {
        // given
        Company company = new Company(1, "alibaba");
        CompanyRepository mockedCompanyRepository = mock(CompanyRepository.class);
        given(mockedCompanyRepository.findById(anyInt())).willReturn(Optional.of(new Company(1, "tencent")));
        given(mockedCompanyRepository.save(any())).willReturn(company);
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        // when
        Company updatedCompany = companyService.updateCompanyById(1, company);
        // then
        assertEquals(company.getCompanyName(), updatedCompany.getCompanyName());
    }

    //    @MockBean
//    CompanyRepository companyRepository;
    @Test
    void should_return_companies_when_get_companies_by_range_given_page_and_pageSize() {
        // given
        CompanyRepository mockCompanyRepository = mock(CompanyRepository.class);
        given(mockCompanyRepository.findAll(PageRequest.of(1, 2))).
                willReturn(new PageImpl<>(Arrays.asList(new Company(1, "alibaba"),
                        new Company(2, "tencent"))));
        CompanyService companyService = new CompanyService(mockCompanyRepository);
        // when
        List<Company> companies = companyService.getCompaniesByRange(1, 2).toList();
        // then
        assertEquals(2, companies.size());
    }
}
