package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    void should_return_1_when_add_employee_given_employee() {
        // given
        Employee employee = new Employee();
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
        given(mockedEmployeeRepository.addEmployee(employee)).willReturn(true);
        EmployeeService employeeService = new EmployeeService(new EmployeeRepository());
        // when
        int result = employeeService.addEmployee(employee);
        // then
        assertEquals(1, result);
    }

    @Test
    void should_return_1_when_delete_employee_by_id_given_employee_id() {
        // given
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
        given(mockedEmployeeRepository.deleteEmployeeById(1)).willReturn(true);
        EmployeeService employeeService = new EmployeeService(new EmployeeRepository());
        // when
        int result = employeeService.deleteEmployeeById(1);

        // then
        assertEquals(1, result);
    }

    @Test
    void should_return_employees_when_get_employees_by_condition_given_no_condition() {
        // given
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
        List<Employee> employeeDataList = getEmployeeDataList();
        given(mockedEmployeeRepository.getEmployeeByConditions(null)).willReturn(employeeDataList);
        EmployeeService employeeService = new EmployeeService(new EmployeeRepository());

        // when
        List<Employee> employees = employeeService.getEmployeeByConditions(null);
        // then
        assertEquals(employeeDataList.size(), employees.size());
    }

    private List<Employee> getEmployeeDataList() {
        List<Employee> employeeDataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String gender = "male";
            if (i % 2 == 0) {
                gender = "female";
            }
            employeeDataList.add(new Employee(i + 1, "tom" + (i + 1), 20 + (i + 1), gender, 6000 + (i + 1)));
        }
        return employeeDataList;
    }
}
