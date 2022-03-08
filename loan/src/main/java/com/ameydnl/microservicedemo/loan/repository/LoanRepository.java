package com.ameydnl.microservicedemo.loan.repository;

import com.ameydnl.microservicedemo.loan.model.Loan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Long> {


    List<Loan> findByCustomerIdOrderByStartDtDesc(int customerId);

}