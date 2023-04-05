package com.example.employeemgtsystem.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    public String id;
    public String name;
    public Long salary;

}
