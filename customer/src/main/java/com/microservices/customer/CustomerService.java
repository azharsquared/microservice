package com.microservices.customer;

import com.microservices.clients.fraud.FraudClient;
import com.microservices.clients.notification.NotificationClient;
import com.microservices.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    //private final RestTemplate restTemplate;
    private final NotificationClient notificationClient;

    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder().firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email()).build();

        customerRepository.saveAndFlush(customer);
       // restTemplate.getForObject("http://FRAUD/api/v1/fraud-check/{customerID}",FraudCheckResponse.class,customer.getId());
        fraudClient.isFraudster(customer.getId());
        // todo: make it async. i.e add to queue
        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Hi %s, you are registered...",
                                customer.getFirstName())
                ));
    }
}
