package com.episen.ing3.tpmra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan({ "com.episen.ing3.tpmra.*" })
public class TpmraApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(TpmraApplication.class, args);
    }
}
