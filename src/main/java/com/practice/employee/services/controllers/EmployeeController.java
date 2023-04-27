package com.practice.employee.services.controllers;

import com.practice.employee.services.dto.EmployeeDto;
import com.practice.employee.services.service.EmployeeServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private  final EmployeeServiceI employeeService;

    @RequestMapping(value = {"/create","/"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EmployeeDto> create(@RequestBody EmployeeDto employee){
        return employeeService.create(employee);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Mono<EmployeeDto> findById(@PathVariable("id") String id){
       return employeeService.findById(id);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Flux<EmployeeDto> findByName(@RequestParam("name") String name){
       return employeeService.findByName(name);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Flux<EmployeeDto> findAll(){
        return employeeService.findAll();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Mono<EmployeeDto> update(@PathVariable("id") String id, @RequestBody EmployeeDto e) {
        return employeeService.update(id, e);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") String id){
        employeeService.delete(id).subscribe();
    }

    // stream all the employees in our system every 2 seconds
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EmployeeDto> streamAllEmployees() {
        return employeeService
                .findAll()
                .flatMap(employee -> Flux
                        .zip(Flux.interval(Duration.ofSeconds(2)),
                                Flux.fromStream(Stream.generate(() -> employee))
                        )
                        .map(Tuple2::getT2)
                );
    }


}
