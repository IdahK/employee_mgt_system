package com.example.employeemgtsystem.controllers;

import com.example.employeemgtsystem.dto.EmployeeDto;
import com.example.employeemgtsystem.service.EmployeeServiceI;
import lombok.extern.slf4j.Slf4j;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(SpringExtension.class) // register SpringExtension
@WebFluxTest(controllers = EmployeeController.class) // test Spring Webflux Controller EmployeeController
class EmployeeControllerTest {

    @MockBean
    private EmployeeServiceI employeeService; //create a mock instance of employeeService , add it to application context and inject to controller

    @Autowired
    private WebTestClient webTestClient; // for testing the reactive endpoints

    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;

    @BeforeEach
    void setUp() {

        employeeDto1 =  Instancio.of(EmployeeDto.class)
                .create();
        employeeDto2 = Instancio.of(EmployeeDto.class)
                .create();
    }

    @Test
    @DisplayName("Should return a single stream of the created employee")
    void create() {
        when(employeeService.create(any(EmployeeDto.class))).thenReturn(Mono.just(employeeDto1));

        webTestClient.post().uri("/employees/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(employeeDto1)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(EmployeeDto.class)
                .consumeWith(System.out::println)
                .isEqualTo(employeeDto1);
    }

    @Test
    @DisplayName("Should return a single stream of the employee by their Id")
    void findById() {
        String employeeId = employeeDto1.getId();
        given(employeeService.findById(employeeId)).willReturn(Mono.just(employeeDto1));

        webTestClient.get()
                .uri("/employees/{id}", employeeId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.name").isEqualTo(employeeDto1.getName())
                .jsonPath("$.salary").isEqualTo(employeeDto1.getSalary());
    }

    @Test
    @DisplayName("Should return the employee by their name")
    void findByName() {
        employeeDto1.setName("John Doe");
        employeeDto2.setName("Jane Doe");
        when(employeeService.findByName("Doe")).thenReturn(Flux.just(employeeDto1, employeeDto2));

        webTestClient.get().uri("/employees/search?name=Doe")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .consumeWith(System.out::println)
                .hasSize(2);
    }

    @Test
    @DisplayName("Should return all employees")
    void findAll() {
        when(employeeService.findAll()).thenReturn(Flux.just(employeeDto1, employeeDto2));

        webTestClient.get().uri("/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .consumeWith(System.out::println)
                .hasSize(2);

    }

    @Test
    @DisplayName("Should update an employee")
    void update() {
        when(employeeService.update(anyString(), any(EmployeeDto.class)))
                .thenReturn(Mono.just(employeeDto2));
        employeeDto2.setId(employeeDto1.getId());

        webTestClient.put().uri("/employees/update/{id}", employeeDto1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(employeeDto2)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class)
                .consumeWith(System.out::println)
                .isEqualTo(employeeDto2);
    }

    @Test
    @DisplayName("Should delete an employee record")
    void deleteById() {
        when(employeeService.delete(any(String.class))).thenReturn(Mono.empty());

        webTestClient.delete().uri("/employees/delete/{id}", employeeDto1.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @DisplayName("Should stream all employees")
    void streamAllEmployees() {
        when(employeeService.findAll()).thenReturn(Flux.just(employeeDto1, employeeDto2));

        webTestClient.get().uri("/employees/stream")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM);

    }
}