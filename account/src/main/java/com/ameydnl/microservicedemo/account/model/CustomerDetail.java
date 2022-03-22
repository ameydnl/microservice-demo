/**
 * 
 */
package com.ameydnl.microservicedemo.account.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CustomerDetail {
	
	private Account accounts;
	private List<Loan> loans;
	private List<Card> cards;
	
	

}
