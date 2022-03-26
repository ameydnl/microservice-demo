package com.ameydnl.microservicedemo.account.controller;

import com.ameydnl.microservicedemo.account.client.CardFeignClient;
import com.ameydnl.microservicedemo.account.client.LoanFeignClient;
import com.ameydnl.microservicedemo.account.config.AccountServiceConfig;
import com.ameydnl.microservicedemo.account.model.*;
import com.ameydnl.microservicedemo.account.repository.AccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountRepository accountRepository;

    private final AccountServiceConfig accountServiceConfig;

    private final LoanFeignClient loanFeignClient;

    private final CardFeignClient cardFeignClient;

    @PostMapping("/account")
    public Account getAccountDetails(@RequestBody Customer customer) {

        Account account = accountRepository.findByCustomerId(customer.getCustomerId());
        return account;

    }

    @GetMapping("/account/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(accountServiceConfig.getMsg(), accountServiceConfig.getBuildVersion(),
                accountServiceConfig.getMailDetails(), accountServiceConfig.getActiveBranches());
        return ow.writeValueAsString(properties);
    }

    @PostMapping("/my-customer-detail")
    //@CircuitBreaker(name = "detailsForCustomerSupportApp", fallbackMethod = "myCustomerDetailsFallBack")
    @Retry(name = "retryForCustomerDetails", fallbackMethod = "myCustomerDetailsFallBack")
    public CustomerDetail myCustomerDetails(@RequestHeader("microservicedemo-correlation-id") String correlationid, @RequestBody Customer customer) {
        Account account = accountRepository.findByCustomerId(customer.getCustomerId());
        List<Loan> loans = loanFeignClient.getLoanDetail(correlationid, customer);
        List<Card> cards = cardFeignClient.getCardDetail(correlationid, customer);

        CustomerDetail customerDetails = new CustomerDetail();
        customerDetails.setAccounts(account);
        customerDetails.setLoans(loans);
        customerDetails.setCards(cards);

        return customerDetails;
    }

    private CustomerDetail myCustomerDetailsFallBack(@RequestHeader("microservicedemo-correlation-id") String correlationid,Customer customer, Throwable t) {
        Account account = accountRepository.findByCustomerId(customer.getCustomerId());
        List<Loan> loans = loanFeignClient.getLoanDetail(correlationid,customer);

        CustomerDetail customerDetails = new CustomerDetail();
        customerDetails.setAccounts(account);
        customerDetails.setLoans(loans);

        return customerDetails;
    }

}