package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class CompanyServiceTest {
    @InjectMocks
    private CompanyService companyService;
    @Mock
    private CompanyRepository mockedCompanyRepository;

    @Test
    void should_return_companies_when_get_companies_given_no_parameter() {
        // given
        given(mockedCompanyRepository.findAll()).willReturn(
                Arrays.asList(new Company(1, "alibaba", Arrays.asList(new Employee(1, "sss", 20, "male", 200), new Employee(2, "fff", 50, "male", 5000)))));
        // when
        List<Company> companies = companyService.getAllCompanies();
        // then
        assertEquals(1, companies.size());
    }


    @Test
    void should_return_company_when_get_company_by_id_given_id() {
        // given
        given(mockedCompanyRepository.findById(1)).willReturn(
                Optional.of(new Company(1, "alibaba",
                        Arrays.asList(new Employee(1, "sss", 20, "male", 200),
                                new Employee(2, "fff", 50, "male", 5000)))));
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
        given(mockedCompanyRepository.save(company)).willReturn(
                new Company(1, "alibaba",
                        Arrays.asList(new Employee(1, "sss", 20, "male", 200),
                                new Employee(2, "fff", 50, "male", 5000))));
        // when
        Company newCompany = companyService.addCompany(company);
        // then
        assertEquals(company.getCompanyName(), newCompany.getCompanyName());
    }

    @Test
    void should_return_void_when_update_company_given_company_id_and_company() {
        // given
        Company company = new Company(1, "alibaba");
        given(mockedCompanyRepository.findById(anyInt())).willReturn(Optional.of(new Company(1, "tencent")));
        given(mockedCompanyRepository.save(any())).willReturn(company);
        // when
        Company updatedCompany = companyService.updateCompanyById(1, company);
        // then
        assertEquals(company.getCompanyName(), updatedCompany.getCompanyName());
    }

    @Test
    void should_return_companies_when_get_companies_by_range_given_page_and_pageSize() {
        // given
        given(mockedCompanyRepository.findAll(PageRequest.of(1, 2))).
                willReturn(new PageImpl<>(Arrays.asList(new Company(1, "alibaba"),
                        new Company(2, "tencent"))));
        // when
        List<Company> companies = companyService.getCompaniesByRange(1, 2).toList();
        // then
        assertEquals(2, companies.size());
    }
}
