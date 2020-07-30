package com.thoughtworks.springbootemployee.integraationtest;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
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
public class EmployeeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;

    @AfterEach
    void clean() {
        System.out.println("begin Each");
        this.employeeRepository.deleteAll();
    }

    @Test
    void should_return_employee_when_add_employee_given_employee() throws Exception {
        String employee = "{\n" +
                "        \"name\":\"dasdas\",\n" +
                "        \"age\":23,\n" +
                "        \"gender\":\"male\",\n" +
                "        \"salary\":312321,\n" +
                "        \"companyId\":33\n" +
                "}";
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(employee))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("dasdas"));

        List<Employee> employees = employeeRepository.findAll();
        assertEquals(1, employees.size());
        assertEquals("dasdas", employees.get(0).getName());
    }

    @Test
    void should_return_all_employees_when_get_employees_given_none() throws Exception {
        employeeRepository.save(new Employee(1, "JACK", 18, "male", 1000));
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("JACK"));
    }

    @Test
    void should_return_all_male_employees_when_get_employees_given_gender() throws Exception {
        employeeRepository.save(new Employee(1, "JACK", 18, "male", 1000));
        employeeRepository.save(new Employee(2, "ROSE", 18, "female", 1000));
        mockMvc.perform(get("/employees")
                .param("gender", "male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].gender").value("male"));
    }

    @Test
    void should_return_employees_page_when_get_employees_given_page_and_pageSize() throws Exception {
        employeeRepository.save(new Employee("JACK", 18, "male", 1000));
        employeeRepository.save(new Employee("ROSE", 18, "female", 1000));
        employeeRepository.save(new Employee("xiaoming", 18, "female", 1000));
        employeeRepository.save(new Employee("xiaohong", 18, "female", 1000));
        mockMvc.perform(get("/employees")
                .param("page", "0").param("pageSize", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("JACK"))
                .andExpect(jsonPath("$[1].name").value("ROSE"));
    }

    @Test
    void should_update_employee_when_update_employees_given_employee_id_and_updated_employee() throws Exception {
        Employee createdEmployee = employeeRepository.save(new Employee(1, "JACK", 18, "male", 1000));
        String updatedEmployee = "{\n" +
                "        \"name\":\"rose\",\n" +
                "        \"age\":23,\n" +
                "        \"gender\":\"female\",\n" +
                "        \"salary\":321,\n" +
                "        \"companyId\":33\n" +
                "}";
        mockMvc.perform(put("/employees/" + createdEmployee.getId()).contentType(MediaType.APPLICATION_JSON).content(updatedEmployee))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("rose"))
                .andExpect(jsonPath("$.gender").value("female"));
        Employee employee = employeeRepository.findById(createdEmployee.getId()).orElse(null);
        assertNotNull(employee);
        assertEquals("rose", employee.getName());
    }

    @Test
    void should_delete_employee_when_delete_employees_given_right_employee_id() throws Exception {
        Employee createdEmployee = employeeRepository.save(new Employee("JACK", 18, "male", 1000));
        mockMvc.perform(delete("/employees/" + createdEmployee.getId()))
                .andExpect(status().isOk());
        Employee employee = employeeRepository.findById(createdEmployee.getId()).orElse(null);
        assertNull(employee);
    }


}
