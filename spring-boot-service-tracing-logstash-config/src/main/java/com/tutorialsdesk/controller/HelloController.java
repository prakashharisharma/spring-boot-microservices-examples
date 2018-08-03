package com.tutorialsdesk.controller;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class HelloController {

	private Logger LOG = (Logger)LoggerFactory.getLogger(HelloController.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${logging.spring-customer-service.file}")
	private String loggingFileName;
	
	@RequestMapping("/")
	public String home() {
		LOG.info( "you called home");
		LOG.debug("debug message slf4j");
		LOG.warn("warn message slf4j");
		LOG.error("error message slf4j - log file name is {}", loggingFileName);
		return "Hello World";
	}

	@RequestMapping("/callhome")
	public String callHome() {
		LOG.info( "calling home");
		return restTemplate.getForObject("http://localhost:8080", String.class);
	}

	
	@Bean
	public RestTemplate getRestTemplate() {
	    return new RestTemplate();
	}
}
