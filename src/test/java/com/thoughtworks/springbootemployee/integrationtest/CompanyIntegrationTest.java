package com.thoughtworks.springbootemployee.integrationtest;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CompanyRepository companyRepository;

    @AfterEach
    private void afterAll() {
        companyRepository.deleteAll();
    }

    @Test
    void should_return_companies_when_get_all_companies_given_no_parameter() throws Exception {
        companyRepository.save(new Company("alibaba"));
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].companyName").value("alibaba"));
    }

    @Test
    void should_return_companies_by_page_when_get_companies_by_page_given_page_and_pageSize() throws Exception {
        companyRepository.save(new Company("alibaba1"));
        companyRepository.save(new Company("alibaba2"));
        companyRepository.save(new Company("alibaba3"));
        companyRepository.save(new Company("alibaba4"));
        mockMvc.perform(get("/companies").param("page", "1").param("pageSize", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].companyName").value("alibaba3"));
    }

    @Test
    void should_return_company_when_add_company_given_company() throws Exception {
        String company = "{\"companyName\":\"tencent\"}";
        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(company))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("tencent"));
        List<Company> companies = companyRepository.findAll();
        assertEquals(1, companies.size());
        assertEquals("tencent", companies.get(0).getCompanyName());
    }

    @Test
    void should_return_company_when_get_company_by_id_given_id() throws Exception {
        Company company = companyRepository.save(new Company(1, "huawei"));
        mockMvc.perform(get("/companies/" + company.getCompanyId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("huawei"));
    }

    @Test
    void should_delete_company_when_delete_company_by_id_given_right_company_id() throws Exception {
        Company createdCompany = companyRepository.save(new Company("wangyi"));
        mockMvc.perform(delete("/companies/" + createdCompany.getCompanyId()))
                .andExpect(status().isOk());
        Company company = companyRepository.findById(createdCompany.getCompanyId()).orElse(null);
        assertNull(company);
    }

    @Test
    void should_update_company_when_update_company_by_id_given_company_id_and_updated_company() throws Exception {
        Company createdCompany = companyRepository.save(new Company("QQ"));
        String updatedCompany = "{\"companyName\":\"wechat\"}";
        mockMvc.perform(put("/companies/" + createdCompany.getCompanyId()).contentType(MediaType.APPLICATION_JSON).content(updatedCompany))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("wechat"));
        Company company = companyRepository.findById(createdCompany.getCompanyId()).orElse(null);
        assertNotNull(company);
        assertEquals("wechat", company.getCompanyName());
    }
}
