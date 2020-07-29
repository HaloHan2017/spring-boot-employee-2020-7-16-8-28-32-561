package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CompanyServiceTest {
    @Test
    void should_return_updated_employee_when_update_given_employee_id_and_employee_info(){
        // given
        CompanyRepository mockedCompanyRepository = mock(CompanyRepository.class);
        given(mockedCompanyRepository.findAll()).willReturn(
                Arrays.asList());
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        // when
        List<Company> companies = companyService.getAllCompanies();
        // then
        assertEquals(2, companies.size());
    }
}
