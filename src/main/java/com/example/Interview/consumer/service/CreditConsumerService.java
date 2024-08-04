package com.example.Interview.consumer.service;


import com.example.Interview.config.ApplicationConstant;
import com.example.Interview.consumer.service.CreditManager;
import com.example.Interview.dto.CreditTransactionDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CreditConsumerService {

    public final CreditManager creditManager;

    public CreditConsumerService(CreditManager creditManager) {
        this.creditManager = creditManager;
    }

    @RetryableTopic(
            attempts = "3",
            kafkaTemplate = "retryableTopicKafkaTemplate",
            dltStrategy = DltStrategy.ALWAYS_RETRY_ON_ERROR)
    @KafkaListener(groupId = ApplicationConstant.GROUP_ID_JSON, topics = ApplicationConstant.TOPIC_NAME, containerFactory = ApplicationConstant.KAFKA_LISTENER_CONTAINER_FACTORY)
    public void receivedMessage(CreditTransactionDto creditTransactionDto) throws JsonProcessingException {
        if(this.creditManager.addTransaction(creditTransactionDto)){
            log.info("Credit Transaction is successful");
        }
    }


    @DltHandler
    public void handleDltPayment(
            CreditTransactionDto creditTransactionDto, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Event on dlt topic={}, payload={}", topic, creditTransactionDto);
    }
}
