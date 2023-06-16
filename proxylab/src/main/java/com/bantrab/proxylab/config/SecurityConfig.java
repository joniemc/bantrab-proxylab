package com.bantrab.proxylab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/api/proxy/security/v1/login").permitAll()
                .antMatchers("/api/proxy/customers/v1/get/all").permitAll()
                .antMatchers("/api/proxy/customers/v1/get/single/{customerId}").permitAll()
                .antMatchers("/api/proxy/products/v1/get/all").permitAll()
                .antMatchers("/api/proxy/products/v1/get/single/{customerId}").permitAll()
                .antMatchers("/api/proxy/orders/v1/get/all").permitAll()
                .antMatchers("/api/proxy/orders/v1/get/single/{orderId}").permitAll()
                .antMatchers("/api/proxy/orders/v1/payment").permitAll()
                .antMatchers("/api/proxy/orders/v1/add").permitAll()
                .anyRequest().authenticated() 
                .and()
            .csrf().disable();
        return http.build(); 
       
    }
	
	
}
