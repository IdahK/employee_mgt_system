package com.practice.employee.services.service;

import com.practice.employee.services.dto.EmployeeDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeServiceI {

    Mono<EmployeeDto> create(EmployeeDto employeeDto);
    Mono<EmployeeDto> findById(String id);
    Flux<EmployeeDto> findByName(String name);
    Flux<EmployeeDto> findAll();
    Mono<EmployeeDto> update(String id, EmployeeDto employeeDto);
    Mono<Void> delete(String id);
}
