package com.trading.server.datamodel;

import java.util.List;

import com.trading.server.Daot;
import com.trading.server.Daot.Transactable;
import com.trading.server.SDao;

public class RpcImpl implements Rpc{

	@Override
	public String submit(String link, String title, String points,String category) {
		// TODO Auto-generated method stub
		System.out.println("blah");
		
		try {
			SDao.getTThreadDao().put(new TThread(null,link, title, points, category));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "blah";
	}
	
	@Override
	public TThread[] getAllFromCategory(String category) {
		
		return SDao.getTThreadDao().listByProperty("category", category).toArray(new TThread[0]);
		
	}

	@Override
	public TThread getTThreadFromId(final long id) {
		return SDao.getTThreadDao().get(id);		
	}
	public TThread getTThreadFromId(String id) {
		return getTThreadFromId(Integer.parseInt(id));		
	}
	@Override
	public void incrementTThread(final long id) {
		Daot.repeatInTransaction(new Transactable() {
	        @Override
	        public void run(Daot daot)
	        {
	        	
	                TThread tt = daot.ofy().find(TThread.class, id);
	                tt.views++;
	                daot.ofy().put(tt);
	        }
		});
		
	}

	
	
	@Override
	public String claimCategory(String category) {
		
		return "blah";
	}

	static RpcImpl r = new RpcImpl();
	public static RpcImpl getR(){
		return r;
	}
}
