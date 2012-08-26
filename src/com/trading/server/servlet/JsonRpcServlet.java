package com.trading.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.rpc.server.JsonRpcExecutor;
import org.json.rpc.server.JsonRpcServletTransport;

import com.trading.server.datamodel.Rpc;
import com.trading.server.datamodel.RpcImpl;

public class JsonRpcServlet extends HttpServlet {

    private final JsonRpcExecutor executor;

    public JsonRpcServlet() {
        executor = bind();
    }

    private JsonRpcExecutor bind() {
        JsonRpcExecutor executor = new JsonRpcExecutor();

        Rpc calcImpl = new RpcImpl();
        executor.addHandler("rpc", calcImpl, Rpc.class);

        return executor;
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        executor.execute(new JsonRpcServletTransport(req, resp));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	// TODO Auto-generated method stub
        executor.execute(new JsonRpcServletTransport(req, resp));
        }
}