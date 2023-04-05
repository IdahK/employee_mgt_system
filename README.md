# Getting Started

This is an **Employee Management REST Service** created using:
* Spring Webflux Application (Spring Reactive) - Flux vs Mono
* Spring Data Reactive Mongodb
* Mongodb Database

This project implements the following concepts:
* [Reactive Programming](https://www.baeldung.com/cs/reactive-programming)
* [Profiling](https://zetcode.com/springboot/profile/#:~:text=Spring%20Boot%20allows%20to%20define,only%20for%20the%20specified%20profile.)
* [Integration Testing](https://www.baeldung.com/spring-boot-testing)
* [REST Applications](https://spring.io/guides/gs/rest-service/)

**Reactive programming** is a design paradigm that allows development of **event-driven web applications** which are responsive and scalable. It is based on Reactive Streams which handles streams of data which are asynchronous.

Why Spring Webflux? Well, reactive programming is achieved in Spring Webflux through the use of **Mono and Flux** classes which represent a **single or multiple values over time respectively**. 

Reactive programming helps to improve application performance by reducing thread blocking and minimal resource usage.
For example:

``
        public Flux<EmployeeDto> findAll() {
        return employeeRepository
        .findAll()
        .map(EmployeeMapper::mapToEmployeeDto)
        .switchIfEmpty(Flux.empty());
        }
``

This is a sample service method within this project that fetches all employees from our database.Here we are returning a Flux of Employees from Employees repository. This means that the method will stream employees as they become available rather than waiting for all employee data to be loaded before returning them (synchronous).

### Run Application
**_Prerequisite_**: Ensure you have Mongodb installed on your machine. Can change the database and port details for your Mongodb instance in `application.properties` file located within the resources folder.

**_Instructions_**: Clone this project and run the application either using Eclipse or Intellij, the default port has been set to port 8081

Sample Endpoints:
1. Searching an employee by Name:

    ![img.png](img.png)

2. Find an employee by their Id:
    
    ![img_1.png](img_1.png)

Note:
For my tests, I have generated test data using Instancio Library as shown below:
![img_2.png](img_2.png)

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.4/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.0.4/reference/htmlsingle/#using.devtools)
* [Spring Data Reactive MongoDB](https://docs.spring.io/spring-boot/docs/3.0.4/reference/htmlsingle/#data.nosql.mongodb)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/3.0.4/reference/htmlsingle/#web.reactive)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)
