package com.trading.client.rpc;


import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SubmitAsync {


	void submitLink(String text,AsyncCallback o);

	//There could be a bandwidth concern with this eventually
	//you could add an additional method that is an array of boolean values which tells whether or not to send back a specific thing





}
