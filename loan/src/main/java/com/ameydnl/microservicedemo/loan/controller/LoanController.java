package com.ameydnl.microservicedemo.loan.controller;

import java.util.List;

import com.ameydnl.microservicedemo.loan.config.LoanServiceConfig;
import com.ameydnl.microservicedemo.loan.model.Customer;
import com.ameydnl.microservicedemo.loan.model.Loan;
import com.ameydnl.microservicedemo.loan.model.Properties;
import com.ameydnl.microservicedemo.loan.repository.LoanRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoanController {

    private final LoanRepository loanRepository;
    private final LoanServiceConfig loanServiceConfig;

    @PostMapping("/my-loan-list")
    public List<Loan> getLoansDetails(@RequestBody Customer customer) {
        List<Loan> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
        if (loans != null) {
            return loans;
        } else {
            return null;
        }

    }

    @GetMapping("/loan/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(loanServiceConfig.getMsg(), loanServiceConfig.getBuildVersion(),
                loanServiceConfig.getMailDetails(), loanServiceConfig.getActiveBranches());
        return ow.writeValueAsString(properties);
    }

}