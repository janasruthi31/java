package com.wipro.empapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the Employee Management REST API application.
 *
 * @SpringBootApplication is a convenience annotation that combines:
 *   - @Configuration      : marks this class as a source of bean definitions
 *   - @EnableAutoConfiguration : tells Spring Boot to auto-configure the app
 *                                based on the dependencies on the classpath
 *                                (e.g. it sees spring-boot-starter-web and
 *                                automatically configures an embedded Tomcat
 *                                server + Spring MVC)
 *   - @ComponentScan      : tells Spring to scan this package and sub-packages
 *                           (controller, service, exception, model) for
 *                           components such as @RestController, @Service, etc.
 */
@SpringBootApplication
public class EmpApiApplication {

    /**
     * Standard Java main method.
     * SpringApplication.run() bootstraps the entire Spring context,
     * starts the embedded web server, and keeps the application running.
     */
    public static void main(String[] args) {
        SpringApplication.run(EmpApiApplication.class, args);
    }
}
