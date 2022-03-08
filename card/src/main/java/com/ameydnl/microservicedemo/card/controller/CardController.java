package com.ameydnl.microservicedemo.card.controller;

import java.util.List;

import com.ameydnl.microservicedemo.card.model.Card;
import com.ameydnl.microservicedemo.card.model.Customer;
import com.ameydnl.microservicedemo.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class CardController {

    private final CardRepository cardRepository;

    @PostMapping("/cards")
    public List<Card> getCardDetails(@RequestBody Customer customer) {
        List<Card> cards = cardRepository.findByCustomerId(customer.getCustomerId());
        if (cards != null) {
            return cards;
        } else {
            return null;
        }

    }

}