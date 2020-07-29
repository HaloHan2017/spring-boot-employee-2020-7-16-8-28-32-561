package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class EmployeeServiceTest {
//    @Test
//    void should_return_updated_employee_when_update_given_employee_id_and_employee_info() {
//        // given
//        Employee employee = new Employee(1, "lisi", 15, "female", 12200);
//        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
//        given(mockedEmployeeRepository.findById(1)).willReturn(Optional.of(new Employee(1, "zhangsan", 12, "male", 1200)));
//        given(mockedEmployeeRepository.save(employee)).willReturn(employee);
//        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
//        // when
//        Employee employeeResult = employeeService.updateEmployeeById(1, employee);
//        // then
//        assertEquals(1, employeeResult.getId());
//        assertEquals("lisi", employeeResult.getName());
//    }

    @Test
    void should_return_employee_when_get_employee_by_id_given_id() {
        // given
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);

        given(mockedEmployeeRepository.findById(1)).willReturn(Optional.of(new Employee(1, "lisi", 15, "female", 12200)));

        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);

        // when
        Employee employee = employeeService.getEmployeeById(1);
        // then
        assertEquals(1, employee.getId());
    }

    @Test
    void should_return_1_when_add_employee_given_employee() {
        // given
        Employee employee = new Employee(11, "tom", 49, "male", 1000);
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
        given(mockedEmployeeRepository.save(employee)).willReturn(new Employee(11, "tom", 49, "male", 1000));
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        // when
        Employee result = employeeService.addEmployee(employee);
        // then
        assertEquals(employee.getId(), result.getId());
    }

    @Test
    void should_return_1_when_delete_employee_by_id_given_employee_id() {
        // given
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
//        given(mockedEmployeeRepository.deleteById(1));
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        // when
        employeeService.deleteEmployeeById(1);

        // then
//        assertEquals(1, result);
    }

    @Test
    void should_return_employees_when_get_employees_by_gender_given_gender() {
        // given
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
//        List<Employee> employeeDataList = getEmployeeDataList();
        given(mockedEmployeeRepository.findByGender("gender")).willReturn(Arrays.asList());
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);

        // when
//        List<Employee> employees = employeeService.getEmployeeByConditions(null);
        // then
        // assertEquals(employeeDataList.size(), employees.size());
    }

//    private List<Employee> getEmployeeDataList() {
//        List<Employee> employeeDataList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            String gender = "male";
//            if (i % 2 == 0) {
//                gender = "female";
//            }
//            employeeDataList.add(new Employee(i + 1, "tom" + (i + 1), 20 + (i + 1), gender, 6000 + (i + 1)));
//        }
//        return employeeDataList;
//    }
}
