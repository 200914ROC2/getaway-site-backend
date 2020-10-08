package com.spacegeecks.delegates;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacegeecks.beans.User;
import com.spacegeecks.data.TransactionPostgres;
import com.spacegeecks.data.UserPostgres;
import com.spacegeecks.services.TransactionService;
import com.spacegeecks.services.UserService;


public class CartDelegate implements FrontControllerDelegate {

	private UserService uServ = new UserService(new UserPostgres());
	private TransactionService tServ = new TransactionService(new TransactionPostgres());
	private ObjectMapper om = new ObjectMapper();
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> jsonMap = om.readValue(request.getInputStream(), Map.class);
		
		User uSession = (User) request.getSession().getAttribute("user");
		User u = null;
//		if (uSession == null) {
//			Map<String, Object> jsonMap = om.readValue(request.getInputStream(), Map.class);
//			if (jsonMap.containsKey("userId")) {
//				uSession = uServ.getUserByID((Integer) jsonMap.get("userId"));
//			} else {
//				response.sendError(400,"Please log in to do things.");
//			}
//		}
		
		if ("GET".equals(request.getMethod())) {
			System.out.println("We caught the get method for the cart");
//			if (uSession != null) {
//				response.getWriter().write(om.writeValueAsString(tServ.findCartByUser(uSession)));
			
			 if(u != null || jsonMap.containsKey("userID")){
				response.getWriter().write(om.writeValueAsString(tServ.findCartByUser(uServ.getUserByID((Integer) jsonMap.get("userId")))));
				
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
