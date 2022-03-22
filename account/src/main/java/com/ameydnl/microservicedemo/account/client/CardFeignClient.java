package com.ameydnl.microservicedemo.account.client;

import com.ameydnl.microservicedemo.account.model.Card;
import com.ameydnl.microservicedemo.account.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("card")
public interface CardFeignClient {

	@RequestMapping(method = RequestMethod.POST, value = "my-card-list", consumes = "application/json")
	List<Card> getCardDetail(@RequestBody Customer customer);
}
