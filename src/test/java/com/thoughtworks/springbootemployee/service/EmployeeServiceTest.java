package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.mapper.EmployeeResponseMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository mockedEmployeeRepository;
    @InjectMocks
    private EmployeeService mockedEmployeeService;
    @Mock
    private EmployeeResponseMapper employeeResponseMapper;

    @Test
    void should_return_updated_employee_when_update_given_employee_id_and_employee_info() throws NoSuchDataException {
        // given
        Employee employee = new Employee(1, "lisi", 15, "female", 12200);
        given(mockedEmployeeRepository.findById(anyInt())).willReturn(Optional.of(new Employee(1, "zhangsan", 12, "male", 1200)));
        given(mockedEmployeeRepository.save(any())).willReturn(employee);
        given(employeeResponseMapper.toEmployeeResponse(any())).willReturn(new EmployeeResponse(1, "lisi", 15, "female", 12200));

        // when
        EmployeeResponse employeeResult = mockedEmployeeService.updateEmployeeById(1, employee);
        // then
        assertEquals(1, employeeResult.getId());
        assertEquals("lisi", employeeResult.getName());
    }

    @Test
    void should_throw_NoSuchDataException_when_update_given_employee_id_and_employee_info() {
        //given
        given(mockedEmployeeRepository.findById(anyInt())).willReturn(Optional.empty());
        given(mockedEmployeeRepository.save(any())).willReturn(null);
        NoSuchDataException noSuchDataException = assertThrows(NoSuchDataException.class, () -> {
            mockedEmployeeService.updateEmployeeById(1, new Employee());
        });
        //then
        assertNotNull(noSuchDataException);
    }

    @Test
    void should_return_employee_when_get_employee_by_id_given_id() {
        // given
        given(mockedEmployeeRepository.findById(1)).willReturn(Optional.of(new Employee(1, "lisi", 15, "female", 12200)));
        given(employeeResponseMapper.toEmployeeResponse(any())).willReturn(new EmployeeResponse(1, "lisi", 15, "female", 12200));
        // when
        EmployeeResponse employee = mockedEmployeeService.getEmployeeById(1);
        // then
        assertEquals(1, employee.getId());
    }

    @Test
    void should_return_1_when_add_employee_given_employee() {
        // given
        Employee employee = new Employee(11, "tom", 49, "male", 1000);
        given(mockedEmployeeRepository.save(employee)).willReturn(new Employee(11, "tom", 49, "male", 1000));
        given(employeeResponseMapper.toEmployeeResponse(any())).willReturn(new EmployeeResponse(11, "tom", 49, "male", 1000));
        // when
        EmployeeResponse result = mockedEmployeeService.addEmployee(employee);
        // then
        assertEquals(employee.getId(), result.getId());
    }

    @Test
    void should_return_1_when_delete_employee_by_id_given_employee_id() {
        // given
        // when
        IllegalOperationException illegalOperationException = assertThrows(IllegalOperationException.class, () -> {
            mockedEmployeeService.deleteEmployeeById(anyInt());
        });
        // then
        assertEquals(IllegalOperationException.class, illegalOperationException.getClass());
    }

    @Test
    void should_return_employees_when_get_employees_by_gender_given_gender() {
        // given
        given(mockedEmployeeRepository.findByGender("male")).willReturn(Arrays.asList(new Employee(2, "bruno", 20, "male", 2000),
                new Employee(3, "xiaosun", 21, "male", 5000)));
        // when
        List<EmployeeResponse> employees = mockedEmployeeService.getEmployeesByGender("male");
        // then
        assertEquals(2, employees.size());
    }

    @Test
    void should_return_employees_when_get_all_employee_given_no_parameter() {
        // given
        given(mockedEmployeeRepository.findAll()).willReturn(
                Arrays.asList(new Employee(2, "bruno", 20, "male", 2000),
                        new Employee(3, "xiaosun", 21, "male", 5000)));
        // when
        List<Employee> employees = mockedEmployeeService.getAllEmployees();
        // then
        assertEquals(2, employees.size());
    }

    @Test
    void should_return_employees_when_get_employees_by_range_given_page_and_pageSize() {
        // given
        given(mockedEmployeeRepository.findAll(PageRequest.of(1, 5))).
                willReturn(new PageImpl<>(Arrays.asList(
                        new Employee(2, "bruno", 20, "male", 2000),
                        new Employee(3, "xiaosun", 21, "male", 5000))));
        // when
        List<EmployeeResponse> employees = mockedEmployeeService.getEmployeesByPage(1, 5);
        // then
        assertEquals(2, employees.size());
    }
}
