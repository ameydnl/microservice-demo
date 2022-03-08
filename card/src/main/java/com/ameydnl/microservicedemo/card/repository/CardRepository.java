package com.ameydnl.microservicedemo.card.repository;

import java.util.List;

import com.ameydnl.microservicedemo.card.model.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CardRepository extends CrudRepository<Card, Long> {

    List<Card> findByCustomerId(int customerId);

}