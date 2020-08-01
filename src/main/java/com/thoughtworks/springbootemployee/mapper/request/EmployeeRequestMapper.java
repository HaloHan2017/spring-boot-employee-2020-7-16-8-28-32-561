package com.thoughtworks.springbootemployee.mapper.request;

import com.thoughtworks.springbootemployee.dto.request.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployeeRequestMapper {
    public Employee toEmployee(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequest, employee);
        return employee;
    }
}
