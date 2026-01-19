package com.softka.customer_service.service;


import com.softka.customer_service.events.ClientEvent;
import com.softka.customer_service.events.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Event<?>> producer;

    public void sendPublish(String topic, ClientEvent event) {
        producer.send(topic, event);
    }
}
