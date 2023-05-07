package com.ufcg.psoft.mercadofacil;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.ufcg.psoft.mercadofacil.util.MyModelMapper;

@SpringBootApplication
public class MercadoFacilApplication {
	
	private MyModelMapper modelMapper = new MyModelMapper();

    @Bean
    public ModelMapper getModelMapper() {
    	return modelMapper.getModelMapper();
    }
    
    public static void main(String[] args) {
        SpringApplication.run(MercadoFacilApplication.class, args);
    }
}
