package com.tutorialsdesk;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.integration.annotation.Transformer;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeTypeUtils;

import com.tutorialsdesk.model.Greetings;
import com.tutorialsdesk.stream.GreetingsStreams;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmbeddedKafkaApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedKafkaApplicationTests.class);

	@Autowired
	private GreetingsStreams greetingsStreams;


	@Autowired
	private MessageCollector messageCollector;

	//@Test
	//@SuppressWarnings("unchecked")
	public void testProcessing() {

		Greetings greetings = new Greetings();

		greetings.setMessage("Hello");

		MessageChannel outboundMessageChannel = greetingsStreams.outboundGreetings();

		outboundMessageChannel.send(MessageBuilder.withPayload(greetings)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());

		Message<Greetings> receivedGreetings = (Message<Greetings>) messageCollector
				.forChannel(greetingsStreams.outboundGreetings()).poll();

		LOGGER.info("Greetings response received: {}", receivedGreetings.getPayload());

		assertNotNull(receivedGreetings.getPayload());

	}

	//@Test
	//@SuppressWarnings("unchecked")
	public void testWiring() {

		Greetings greetings = new Greetings();

		greetings.setMessage("Hello");

		MessageChannel inboundMessageChannel = greetingsStreams.inboundGreetings();

		inboundMessageChannel.send(MessageBuilder.withPayload(greetings).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());

		Message<Greetings> received = (Message<Greetings>) messageCollector.forChannel(greetingsStreams.outboundGreetings()).poll();

	}

	//  @SpringBootApplication
	 // @EnableBinding(GreetingsStreams.class)
	  public static class MyProcessor {

	    @Autowired
	    private GreetingsStreams channels;

	    @Transformer(inputChannel = GreetingsStreams.INPUT, outputChannel = GreetingsStreams.OUTPUT)
	    public Greetings transform(Greetings in) {
	      return in;
	    }
	  }
}