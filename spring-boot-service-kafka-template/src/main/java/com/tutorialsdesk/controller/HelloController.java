package com.tutorialsdesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tutorialsdesk.producer.Sender;

@RestController
public class HelloController {

	private static final String HELLOWORLD_TOPIC = "helloworld.t";

	@Autowired
    private Sender sender;
	
    @GetMapping("/hello")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String greetings() {
    	sender.send(HELLOWORLD_TOPIC, "Hello from Kafka!");
        
        return " Sent to KAFKA";
        
    }
}
