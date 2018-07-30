package com.tutorialsdesk.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;

import com.tutorialsdesk.stream.GreetingsStreams;


@EnableBinding(GreetingsStreams.class)
//nableBinding(Processor.class)
public class StreamsConfig {
}
