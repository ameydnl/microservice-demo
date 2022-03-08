package com.ameydnl.microservicedemo.loan.controller;

import java.util.List;

import com.ameydnl.microservicedemo.loan.model.Customer;
import com.ameydnl.microservicedemo.loan.model.Loan;
import com.ameydnl.microservicedemo.loan.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoanController {

    private final LoanRepository loanRepository;

    @PostMapping("/loans")
    public List<Loan> getLoansDetails(@RequestBody Customer customer) {
        List<Loan> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
        if (loans != null) {
            return loans;
        } else {
            return null;
        }

    }

}