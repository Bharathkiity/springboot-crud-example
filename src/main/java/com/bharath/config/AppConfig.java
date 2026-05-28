package com.bharath.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class AppConfig {
	// This method creates and returns a ModelMapper bean that can be used for mapping between objects in the application.
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    //add redis configuration for github confirmation email
    
    
    //add eureka server configuration for service discovery and load balancing
    
}