package com.example.attendance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/attendance/**").hasAnyRole("EMPLOYEE", "MANAGER")
            .antMatchers("/reports/**").hasRole("MANAGER")
            .antMatchers("/employees/**").hasRole("MANAGER")
            .anyRequest().authenticated()
            .and()
            .httpBasic();
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails employee = User.builder()
                .username("employee")
                .password("{noop}password")
                .roles("EMPLOYEE")
                .build();
        UserDetails manager = User.builder()
                .username("manager")
                .password("{noop}password")
                .roles("MANAGER")
                .build();
        return new InMemoryUserDetailsManager(employee, manager);
    }
}