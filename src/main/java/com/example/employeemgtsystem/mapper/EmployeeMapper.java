package com.example.employeemgtsystem.mapper;

import com.example.employeemgtsystem.dto.EmployeeDto;
import com.example.employeemgtsystem.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDto mapToEmployeeDto(Employee employee){
        return EmployeeDto.builder()
                .id(employee.getId())
                .salary(employee.getSalary())
                .name(employee.getName())
                .build();
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto){
        return Employee.builder()
                .id(employeeDto.getId())
                .name(employeeDto.getName())
                .salary(employeeDto.getSalary())
                .build();
    }

}
