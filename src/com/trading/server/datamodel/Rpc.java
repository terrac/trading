package com.trading.server.datamodel;




public interface Rpc {
	public String submit(String link, String title, String points,String category);

	String claimCategory(String category);

	TThread[] getAllFromCategory(String category);

	TThread getTThreadFromId(long id);

	void incrementTThread(long id);
}