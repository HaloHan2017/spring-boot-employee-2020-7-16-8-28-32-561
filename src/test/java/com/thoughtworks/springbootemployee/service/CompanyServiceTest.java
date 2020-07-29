package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Company company = companyService.findCompanyById(1);

        // then
        assertEquals("alibaba", company.getCompanyName());
    }
}
