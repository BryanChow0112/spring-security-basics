package com.luv2code.springboot.cruddemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/*
    @Configuration annotation is used to define a configuration class in Spring.
*/
@Configuration
public class DemoSecurityConfig {

    // In-memory authentication
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
}
