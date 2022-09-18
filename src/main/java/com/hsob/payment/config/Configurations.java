package com.hsob.payment.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Configurations {

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
