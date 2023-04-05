package com.example.employeemgtsystem.service;

import com.example.employeemgtsystem.dto.EmployeeDto;
import com.example.employeemgtsystem.mapper.EmployeeMapper;
import com.example.employeemgtsystem.repository.EmployeeRepository;
import com.example.employeemgtsystem.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmployeeService implements EmployeeServiceI {

    private final EmployeeRepository employeeRepository;

    @Override
    public Mono<EmployeeDto> create(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Mono<Employee> savedEmployee = employeeRepository.save(employee);
        return savedEmployee
                .map(EmployeeMapper::mapToEmployeeDto);
    }

    @Override
    public Mono<EmployeeDto> findById(String id) {
        return employeeRepository.findById(id)
                .map(EmployeeMapper::mapToEmployeeDto);
    }

    @Override
    public Flux<EmployeeDto> findByName(String name) {
        return employeeRepository
                .findByNameContainingIgnoreCase(name)
                .map(EmployeeMapper::mapToEmployeeDto)
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Flux<EmployeeDto> findAll() {
        return employeeRepository
                .findAll()
                .map(EmployeeMapper::mapToEmployeeDto)
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<EmployeeDto> update(String employeeId, EmployeeDto employeeDto) {
        Mono<Employee> employeeMono = employeeRepository.findById(employeeId);

        return employeeMono.flatMap(
                (existingEmployee) -> {
                    existingEmployee = this.updateEmployee(existingEmployee, employeeDto);
                    return employeeRepository.save(existingEmployee);
                }
        ).map(EmployeeMapper::mapToEmployeeDto);

//        return employeeRepository
//                .
//                findById(id)
//                .flatMap( employee -> {
//                    employee.setName(employeeDto.getName());
//                    employee.setSalary(employeeDto.getSalary());
//                    return employeeRepository.save(employee);
//                });
////        return employeeRepository.save(e);
    }

    private Employee updateEmployee(Employee existingEmployee, EmployeeDto employeeDto) {
        existingEmployee.setName(employeeDto.getName());
        existingEmployee.setSalary(employeeDto.getSalary());
        return existingEmployee;
    }

    @Override
    public Mono<Void> delete(String id) {
        return employeeRepository.findById(id)
                .flatMap(employee -> employeeRepository.deleteById(id));
//                .then(Mono.just(employee));
    }
}
