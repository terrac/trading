package com.trading.server.datamodel;


import java.util.ArrayList;
import java.util.List;


import javax.persistence.Embedded;
import javax.persistence.Id;

import com.googlecode.objectify.Key;



public class Stock {
	public Stock() {
	}
	public Stock(int i, GUser gu) {
		stockAmount = i;
		owner = gu.getKey();
	}
	@Id
	public Long id;

	public Key<GUser> owner;
	Key<TThread> stockOf;
	public int stockAmount;
	public Key<Stock> getKey(){
		return new Key(Stock.class,id);
	}
}
