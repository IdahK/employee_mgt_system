package com.practice.employee.services.repository;

import com.practice.employee.services.entity.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {

    Flux<Employee> findByNameContainingIgnoreCase(final String name);

}
