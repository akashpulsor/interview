package com.example.Interview.producer.controller;


import com.example.Interview.config.ApplicationConstant;
import com.example.Interview.dto.CreditTransactionDto;
import com.example.Interview.producer.service.CreditProducerService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produce")
public class Producer {

    private final CreditProducerService creditProducerService;
    public Producer(CreditProducerService creditProducerService) {
        this.creditProducerService = creditProducerService;
    }

    @PostMapping("/credit")
    public String sendMessage(@RequestBody CreditTransactionDto creditTransactionDto) {
        this.creditProducerService.addCreditTransaction(creditTransactionDto);
        return "json message sent succuessfully";
    }
}
