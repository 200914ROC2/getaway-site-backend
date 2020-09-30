package com.spacegeecks.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacegeecks.delegates.FrontControllerDelegate;


public class FrontController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestHandler rh = new RequestHandler();
       
	private void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		FrontControllerDelegate fcd = rh.handle(request, response);
		
		System.out.println("Process has been called.");
		
		if (fcd != null)
			fcd.process(request, response);
		else
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		process(request, response);
		
		System.out.println("The request is " + request.getMethod());
		System.out.println("This is doGet.");
	}

	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		process(request, response);
		
		System.out.println("This is doOptions.");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		process(request, response);
		
		System.out.println("This is doPost.");
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		process(request, response);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		process(request, response);
	}

}
