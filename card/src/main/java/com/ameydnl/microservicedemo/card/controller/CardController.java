package com.ameydnl.microservicedemo.card.controller;

import java.util.List;

import com.ameydnl.microservicedemo.card.config.CardServiceConfig;
import com.ameydnl.microservicedemo.card.model.Card;
import com.ameydnl.microservicedemo.card.model.Customer;
import com.ameydnl.microservicedemo.card.model.Properties;
import com.ameydnl.microservicedemo.card.repository.CardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class CardController {

    private final CardRepository cardRepository;
    private final CardServiceConfig cardServiceConfig;

    @PostMapping("/my-card-list")
    public List<Card> getCardDetails(@RequestHeader("microservicedemo-correlation-id") String correlationid, @RequestBody Customer customer) {
        List<Card> cards = cardRepository.findByCustomerId(customer.getCustomerId());
        if (cards != null) {
            return cards;
        } else {
            return null;
        }

    }

    @GetMapping("/card/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(cardServiceConfig.getMsg(), cardServiceConfig.getBuildVersion(),
                cardServiceConfig.getMailDetails(), cardServiceConfig.getActiveBranches());
        return ow.writeValueAsString(properties);
    }

}