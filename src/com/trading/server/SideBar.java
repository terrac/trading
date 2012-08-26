package com.trading.server;

public class SideBar {

	public static String getServletRep(String string, String logoutUrl) {
		return "<a href="+logoutUrl+">"+string+"</a>";
	}

}
