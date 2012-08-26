package com.trading.server;

import com.trading.server.datamodel.GUser;
import com.trading.server.datamodel.Market;
import com.trading.server.datamodel.Stock;
import com.trading.server.datamodel.TThread;

public class SDao {

	public static Dao<GUser> getGUserDao(){
		return new Dao<GUser>(GUser.class);
	}
	public static Dao<TThread> getTThreadDao(){
		return new Dao<TThread>(TThread.class);
	}
	
	public static Dao<Market> getMarketDao(){
		return new Dao<Market>(Market.class);
	}
	
	public static Dao<Stock> getStockDao(){
		return new Dao<Stock>(Stock.class);
	}
	
}
