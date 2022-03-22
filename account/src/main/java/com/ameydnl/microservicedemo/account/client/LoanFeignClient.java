package com.ameydnl.microservicedemo.account.client;

import com.ameydnl.microservicedemo.account.model.Customer;
import com.ameydnl.microservicedemo.account.model.Loan;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("loan")
public interface LoanFeignClient {

	@RequestMapping(method = RequestMethod.POST, value = "my-loan-list", consumes = "application/json")
	List<Loan> getLoanDetail(@RequestBody Customer customer);
}
