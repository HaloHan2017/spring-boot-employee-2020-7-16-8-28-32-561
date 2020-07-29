package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class EmployeeServiceTest {
    @Test
    void should_return_updated_employee_when_update_given_employee_id_and_employee_info() {
        // given
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
        given(mockedEmployeeRepository.findEmployeeById(1)).willReturn(new Employee(1, "lisi", 15, "female", 12200));
        EmployeeService employeeService = new EmployeeService(new EmployeeRepository());
        // when
        Employee employee = employeeService.updateEmployeeById(1, new Employee(1, "zhangsan", 12, "male", 1200));
        // then
        assertEquals(1, employee.getId());
        assertEquals("zhangsan", employee.getName());
    }

    @Test
    void should_return_employee_when_get_employee_by_id_given_id() {
        // given
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);

        given(mockedEmployeeRepository.findEmployeeById(1)).willReturn(new Employee(1, "lisi", 15, "female", 12200));

        EmployeeService employeeService = new EmployeeService(new EmployeeRepository());

        // when
        Employee employee = employeeService.getEmployeeById(1);
        // then
        assertEquals(1, employee.getId());
    }
}
