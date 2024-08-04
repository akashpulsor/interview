package com.example.Interview.producer.service;

import com.example.Interview.config.ApplicationConstant;
import com.example.Interview.dto.CreditTransactionDto;
import com.example.Interview.exception.CreditTransactionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreditProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    public CreditProducerService(KafkaTemplate<String, Object> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }



    public void addCreditTransaction(CreditTransactionDto creditTransactionDto){
        try {
            log.info("Credit transaction received - {}", creditTransactionDto);
            kafkaTemplate.send(ApplicationConstant.TOPIC_NAME, creditTransactionDto);
            log.info("Credit transaction added to topic");
        } catch (Exception e) {
            throw  new CreditTransactionException("Credit transaction Failed");
        }
    }
}
