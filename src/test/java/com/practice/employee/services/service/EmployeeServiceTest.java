package com.practice.employee.services.service;

import com.practice.employee.services.dto.EmployeeDto;
import com.practice.employee.services.entity.Employee;
import com.practice.employee.services.mapper.EmployeeMapper;
import com.practice.employee.services.repository.EmployeeRepository;
import com.practice.employee.services.utils.EmployeeUtils;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.instancio.Select.field;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    public EmployeeRepository employeeRepository;

    @InjectMocks
    public EmployeeService employeeService;

    @Captor
    public ArgumentCaptor<List<Employee>> employeeCaptor;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp() {
//        employeeService = new EmployeeServicesApplication(employeeRepository);

        employee1 = Instancio.of(Employee.class)
                .create();
        employee2 = Instancio.of(Employee.class)
                .create();
    }

    @Test
    void create() {
        ArgumentCaptor<Employee> saveEmployeeCaptor = ArgumentCaptor.forClass(Employee.class);
        EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee1);
        when(employeeRepository.save(employee1)).thenReturn(Mono.just(employee1));

        StepVerifier
                .create(employeeService.create(employeeDto))
                .assertNext(employee -> {
                    Assertions.assertThat(employee.getName()).isEqualTo(employee1.getName());
                    // captor assertions
                    Assertions.assertThat(EmployeeUtils.toId.apply(saveEmployeeCaptor.getValue())).isEqualTo(employee1.getId());
                })
                .verifyComplete();

//        Mono<EmployeeDto> savedEmployee = employeeService.create(employeeDto);
//
//        StepVerifier.create(savedEmployee)
//                .expectNextMatches( e -> e.getName().equals(employee1.getName()))
//                .verifyComplete();
    }

    @Test
    void findById() {
    }

    @Test
    void findByName() {
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}