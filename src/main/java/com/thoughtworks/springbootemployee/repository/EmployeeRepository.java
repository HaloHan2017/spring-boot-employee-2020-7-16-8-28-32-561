package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {
    public Employee findEmployeeById(int id) {
        return new Employee(1, "zhangsan", 20, "male", 20000);
    }
}
