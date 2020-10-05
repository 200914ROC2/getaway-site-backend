package com.spacegeecks.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacegeecks.beans.User;
import com.spacegeecks.data.TransactionPostgres;
import com.spacegeecks.services.TransactionService;


public class CartDelegate implements FrontControllerDelegate {

	private TransactionService tServ = new TransactionService(new TransactionPostgres());
	private ObjectMapper om = new ObjectMapper();
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User uSession = (User) request.getSession().getAttribute("user");
		
		if ("GET".equals(request.getMethod())) {
			if (uSession != null) {
				response.getWriter().write(om.writeValueAsString(tServ.findCartByUser(uSession)));
			} else {
				response.sendError(400,"Must be logged in to view cart.");
			}
		} else if ("POST".equals(request.getMethod())) {
			if (uSession != null) {
				if (tServ.purchaseCart(uSession))
					response.getWriter().write("You have purchased all items in your cart.");
				else
					response.getWriter().write("There was an error with your cart.");
			}
			else {
				response.sendError(400, "Must be logged in to purchase cart.");
			}
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		
	}
	
}
