package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EmployeeResponseMapper {
    public EmployeeResponse toEmployeeResponse(Employee employee) {
        if (Objects.isNull(employee)) {
            return null;
        }
        EmployeeResponse employeeResponse = new EmployeeResponse();
        BeanUtils.copyProperties(employee, employeeResponse);
        return employeeResponse;
    }
}
