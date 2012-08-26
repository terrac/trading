package com.trading.server.datamodel;


import java.util.ArrayList;
import java.util.List;


import javax.persistence.Embedded;
import javax.persistence.Id;

import com.googlecode.objectify.Key;



public class GUser {
	public GUser() {
	}
	public GUser(String userId, String nickname) {
		id = userId;
		displayName = nickname;
	}
	@Id
	public String id;
	public String displayName;
	public int money = 100;
	
	public String getDisplayName() {
		return displayName;
	}
	public Key<GUser> getKey(){
		return new Key(GUser.class,id);
	}
	
}
