package com.thoughtworks.springbootemployee.integraationtest;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
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
}
