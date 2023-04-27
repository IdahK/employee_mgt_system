package com.practice.employee.services.utils;

import com.practice.employee.services.entity.Employee;
import com.mongodb.Function;
import org.springframework.stereotype.Component;

@Component
public interface EmployeeUtils {

    Function<Employee, String> toId = Employee::getId;
}
