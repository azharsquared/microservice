package com.microservices.fraud;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class FraudCheckService {

    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;

    public boolean isFraudulentCustomer(Integer customerID){
        fraudCheckHistoryRepository.save(FraudCheckHistory.builder()
                .customerId(customerID).isFraudster(false).createdAt(LocalDateTime.now()).build());
        log.info("fraud api hit");
        return false;
    }
}
