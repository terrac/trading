package com.trading.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;




@RemoteServiceRelativePath("submit")
public interface Submit extends RemoteService {



	void submitLink(String text);

}
