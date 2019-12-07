package com.example.tryme.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class RoomCleaningPrimer implements CommandLineRunner {
    @Value("${amqp.queue.name}")
    private String queueName;

    private final static Logger LOGGER = LoggerFactory.getLogger(RoomCleaningPrimer.class);

    private RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final ConfigurableApplicationContext context;
    private ObjectMapper objectMapper;




    @Autowired
    public RoomCleaningPrimer(RabbitTemplate rabbitTemplate, ConfigurableApplicationContext context, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.context = context;
        this.objectMapper = objectMapper;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void run(String... args) throws Exception {
        String url = "http://localhost:8080/api/rooms";
        Room [] roomArray = this.restTemplate.getForObject(url,Room[].class);

        List<Room> roomList = Arrays.asList(roomArray);
        roomList.forEach(room -> {
            LOGGER.info("Sending message");
            try {
                String jsonString = objectMapper.writeValueAsString(room);
                rabbitTemplate.convertAndSend(queueName,jsonString);
            } catch (JsonProcessingException e) {
                LOGGER.error("EXCEPTION");
            }
        });

        context.close();

    }
}
