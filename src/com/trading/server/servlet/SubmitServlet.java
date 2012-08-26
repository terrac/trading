package com.trading.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.rpc.server.JsonRpcExecutor;
import org.json.rpc.server.JsonRpcServletTransport;

import com.trading.server.LoginService;
import com.trading.server.PersonLoginInfo;
import com.trading.server.SDao;
import com.trading.server.datamodel.Rpc;
import com.trading.server.datamodel.RpcImpl;
import com.trading.server.datamodel.TThread;

public class SubmitServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	PersonLoginInfo pli=LoginService.login(req.getRequestURI(), resp.getWriter());
		if(pli == null){
			return;
		}
    	if(req.getParameter("submit") != null){
    		String category = req.getParameter("category");
			SDao.getTThreadDao().put(new TThread(pli.person,
    		req.getParameter("link"), req.getParameter("title"), req.getParameter("points"), category));
        	resp.getWriter().write("submitted go to <a href=/read?category="+category+">your post</a>");
        	
    	}
    	resp.getWriter().write("<html>\r\n" + 
    			"  <head>\r\n" + 
    			"  </head>\r\n" + 
    			"\r\n" + 
    			"  <body>\r\n" + 
    			"  <form method=POST action=/submit>\r\n" + 
    			"  link<input type=text name=link><br>\r\n" + 
    			"  title<input type=text name=title><br>\r\n" + 
    			"  points<input type=text name=points><br>\r\n" + 
    			"  category<input type=text name=category><br>\r\n" + 
    			"  <input type=submit name=submit value=Submit>\r\n" + 
    			"  </form>\r\n" + 
    			"  </body>\r\n" + 
    			"</html>");
    }
}