package com.episen.ing3.tpmra.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.episen.ing3.tpmra.mvc.RestControllerAdvice;

@Configuration
@Import({RestControllerAdvice.class})
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class ServiceAutoConfiguration {
	
}
