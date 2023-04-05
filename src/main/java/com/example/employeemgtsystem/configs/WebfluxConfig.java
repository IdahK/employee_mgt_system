package com.example.employeemgtsystem.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux // imports the Spring WebFlux configuration from WebFluxConfigurationSupport that enables the use of annotated controllers and functional endpoints.
public class WebfluxConfig implements WebFluxConfigurer {

}
