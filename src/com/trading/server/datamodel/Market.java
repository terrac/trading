package com.trading.server.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Embedded;
import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.trading.server.SDao;

public class Market {
	public Market() {
	}

	@Id
	public Long id;

	// owners of the tthread's stock
	@Embedded
	public List<Stock> stocklist = new ArrayList();
	int lastTrade = 5;

	public void buy(GUser gu) {
		Stock tobuy;

		int rnum = new Random().nextInt(stocklist.size());

		tobuy = stocklist.get(rnum);

		// cannot buy from oneself
		if (tobuy.owner.equals(gu.getKey())) {
			for (Stock a : stocklist) {
				if (!a.owner.equals(gu.getKey())) {
					buyStock(gu, a);
					break;
				}
			}
			return;
		}
		buyStock(gu, tobuy);

	}

	private void buyStock(GUser gu, Stock a) {
		a.stockAmount = a.stockAmount - lastTrade;
		if (a.stockAmount == 0) {
			stocklist.remove(a);
		}
		boolean f = false;
		for (Stock b : stocklist) {
			if (b.owner ==gu.getKey()) {
				f = true;
				b.stockAmount += 5;
			}
		}
		if(!f){
			stocklist.add(new Stock(lastTrade, gu));			
		}
		gu.money -= lastTrade;
		SDao.getGUserDao().put(gu);
		lastTrade++;
		SDao.getMarketDao().put(this);
	}

	public static <T> T getRandomFromList(List<T> events) {
		if (events.size() == 0) {
			return null;
		}
		return events.get(new Random().nextInt(events.size()));
	}

	public void init(GUser gu) {
		stocklist.add(new Stock(80, gu));
		SDao.getMarketDao().put(this);
	}
}
