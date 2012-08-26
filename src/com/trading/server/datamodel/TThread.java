package com.trading.server.datamodel;


import java.util.ArrayList;
import java.util.List;


import javax.persistence.Embedded;
import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.trading.server.SDao;



public class TThread {
	public TThread() {
	}
	public TThread(GUser gu,String link,String title,String points,String category) {
		this.link = link;
		this.title= title;
		try {
			this.points = Integer.parseInt(points);
		} catch (NumberFormatException e) {
			this.points = 0;
		}
		this.category = category;
		Market mt = new Market();
		market = SDao.getMarketDao().put(mt);
		mt.init(gu);
	}
	@Id
	public Long id;

	public String link;
	public String title;
	public int points = 0;
	public String category;
	public int views;
	public Key<Market> market;
	
	public Key<TThread> getKey(){
		return new Key(TThread.class,id);
	}
	
}
