package com.thoughtworks.springbootemployee.mapper.request;

import com.thoughtworks.springbootemployee.dto.request.CompanyRequest;
import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CompanyRequestMapper {
    public Company toCompany(CompanyRequest companyRequest) {
        Company company = new Company();
        BeanUtils.copyProperties(companyRequest, company);
        return company;
    }
}
