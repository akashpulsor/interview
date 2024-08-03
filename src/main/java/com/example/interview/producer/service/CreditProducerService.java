package com.example.Interview.producer.service;

import com.example.Interview.config.ApplicationConstant;
import com.example.Interview.dto.CreditTransactionDto;
import com.example.Interview.exception.CreditTransactionException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CreditProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    public CreditProducerService(KafkaTemplate<String, Object> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }



    public void addCreditTransaction(CreditTransactionDto creditTransactionDto){
        try {
            kafkaTemplate.send(ApplicationConstant.TOPIC_NAME, creditTransactionDto);
        } catch (Exception e) {
            throw  new CreditTransactionException("Credit transaction Failed");
        }
    }
}
