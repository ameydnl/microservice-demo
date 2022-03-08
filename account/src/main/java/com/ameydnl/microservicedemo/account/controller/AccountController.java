package com.ameydnl.microservicedemo.account.controller;

import com.ameydnl.microservicedemo.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ameydnl.microservicedemo.account.model.Account;
import com.ameydnl.microservicedemo.account.model.Customer;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountRepository accountRepository;

    @PostMapping("/account")
    public Account getAccountDetails(@RequestBody Customer customer) {

        Account account = accountRepository.findByCustomerId(customer.getCustomerId());
        if (account != null) {
            return account;
        } else {
            return null;
        }

    }

}