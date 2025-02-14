package com.luv2code.springboot.cruddemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/*
    @Configuration annotation is used to define a configuration class in Spring.
*/
@Configuration
public class DemoSecurityConfig {

    /*
        JdbcUserDetailsManager is a built-in option in Spring Security that
        uses JDBC to manage user credentials and roles in a relational database.
        We will need to follow a specific schema for the tables for it to work.
        If you want to use a different schema, you can extend JdbcUserDetailsManager
        The datasource is automatically configured by Spring Boot.
    */
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // Tells Spring Security how to retrieve a user by username from our custom schema
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select user_id, pw, active from members where user_id=?");

        // Tells Spring Security how to retrieve the authorities/roles by username from our custom schema
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_id, role from roles where user_id=?");

        return jdbcUserDetailsManager;
    }

    /*
        The SecurityFilterChain is a filter chain that is responsible
        for processing HTTP requests in Spring Security.

        - GET /api/employees -> EMPLOYEE
        - GET /api/employees/** -> EMPLOYEE
        - POST /api/employees -> MANAGER
        - PUT /api/employees -> MANAGER
        - DELETE /api/employees/** -> ADMIN
    */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/employees").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/employees/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/api/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")
        );
        // Use HTTP Basic authentication (username and password)
        httpSecurity.httpBasic(Customizer.withDefaults());

        // Disable Cross Site Request Forgery (CSRF)
        // In general, not required for stateless REST APIs that use POST, PUT, DELETE and/or PATCH
        httpSecurity.csrf(csrf -> csrf.disable());

        return httpSecurity.build();
    }

    /*// In-memory authentication
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails john = User.builder()
                .username("john")
                .password("{noop}test123")  // {noop} (no operation) -> plaintext password
                .roles("EMPLOYEE")
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails susan = User.builder()
                .username("susan")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();

        // Add the users to the in-memory authentication
        return new InMemoryUserDetailsManager(john, mary, susan);
    }
    */
}
