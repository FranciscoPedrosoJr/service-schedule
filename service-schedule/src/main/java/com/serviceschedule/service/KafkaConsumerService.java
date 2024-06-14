package com.serviceschedule.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @KafkaListener(topics = "agendamentos", groupId = "group_id")
    public void consumeAgendamentos(String message) {
        System.out.println("Mensagem de agendamento recebida: " + message);
    }

    @KafkaListener(topics = "cancelamentos", groupId = "group_id")
    public void consumeCancelamentos(String message) {
        System.out.println("Mensagem de cancelamento recebida: " + message);
    }
}
