package com.trading.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;
import com.trading.server.LoginService;
import com.trading.server.PersonLoginInfo;
import com.trading.server.SDao;
import com.trading.server.datamodel.GUser;
import com.trading.server.datamodel.Market;
import com.trading.server.datamodel.RpcImpl;
import com.trading.server.datamodel.Stock;
import com.trading.server.datamodel.TThread;

public class BuyServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PersonLoginInfo pli=LoginService.login(req.getRequestURI(), resp.getWriter());
		if(pli == null){
			return;
		}
		TThread tt=RpcImpl.getR().getTThreadFromId(req.getParameter("id"));
		Market market = SDao.getMarketDao().get(tt.market);
		market.buy(pli.person);
		resp.getWriter().write("your money: "+pli.person.money);
		resp.getWriter().write("<br>Market<br>");
		for(Stock s :market.stocklist){
			GUser owner = SDao.getGUserDao().get(s.owner);
			resp.getWriter().write("Stock:"+owner.displayName+" "+s.stockAmount+"<br>");			
		}
		//get the market off of the tthread
		
		//call buy on the market.
		
		//buy adds 1 to the price and takes one of the associated stocks that 
		//are held at random (selling sells directly to the thread creator and fails if it doesn't have enough money
		
	}
}