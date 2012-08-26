package com.trading.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trading.server.datamodel.RpcImpl;
import com.trading.server.datamodel.TThread;

public class ReadServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		TThread[] tt=new RpcImpl().getAllFromCategory(req.getParameter("category"));
		for(TThread t :tt){
			resp.getWriter().append("  <a href=/buy?id="+t.id+">buy</a>");
			resp.getWriter().append("  <a href=\""+t.link+"\">"+t.title+"</a>");
			resp.getWriter().append("  <a href=/sell>sell</a><br>");
		}
	}
}