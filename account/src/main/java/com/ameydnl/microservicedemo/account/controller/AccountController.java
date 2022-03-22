package com.ameydnl.microservicedemo.account.controller;

import com.ameydnl.microservicedemo.account.client.CardFeignClient;
import com.ameydnl.microservicedemo.account.client.LoanFeignClient;
import com.ameydnl.microservicedemo.account.config.AccountServiceConfig;
import com.ameydnl.microservicedemo.account.model.*;
import com.ameydnl.microservicedemo.account.repository.AccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        if (account != null) {
            return account;
        } else {
            return null;
        }

    }

    @GetMapping("/account/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(accountServiceConfig.getMsg(), accountServiceConfig.getBuildVersion(),
                accountServiceConfig.getMailDetails(), accountServiceConfig.getActiveBranches());
        return ow.writeValueAsString(properties);
    }

    @PostMapping("/my-customer-detail")
    public CustomerDetail myCustomerDetails(@RequestBody Customer customer) {
        Account account = accountRepository.findByCustomerId(customer.getCustomerId());
        List<Loan> loans = loanFeignClient.getLoanDetail(customer);
        List<Card> cards = cardFeignClient.getCardDetail(customer);

        CustomerDetail customerDetails = new CustomerDetail();
        customerDetails.setAccounts(account);
        customerDetails.setLoans(loans);
        customerDetails.setCards(cards);

        return customerDetails;

    }

}