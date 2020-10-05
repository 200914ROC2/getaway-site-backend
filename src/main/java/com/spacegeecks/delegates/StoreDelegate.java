package com.spacegeecks.delegates;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacegeecks.beans.Role;
import com.spacegeecks.beans.Transaction;
import com.spacegeecks.beans.TransactionStatus;
import com.spacegeecks.beans.User;
import com.spacegeecks.data.TransactionPostgres;
import com.spacegeecks.services.TransactionService;


public class StoreDelegate implements FrontControllerDelegate {

	private TransactionService tServ = new TransactionService(new TransactionPostgres());
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User uSession = (User) request.getSession().getAttribute("user");
		Transaction t = new Transaction();
		ObjectMapper om = new ObjectMapper();
		
		if ("POST".equals(request.getMethod())) {
			if (uSession != null) {
				Map<String, Object> jsonMap = om.readValue(request.getInputStream(), Map.class);
				
				if (jsonMap.containsKey("price") && jsonMap.containsKey("listingId") && jsonMap.containsKey("title") && jsonMap.containsKey("image")) {
					
					t.setPrice((double) jsonMap.get("price"));
					t.setListingId((String) jsonMap.get("listingId"));
					t.setTitle((String) jsonMap.get("title"));
					t.setImage((String) jsonMap.get("image"));
					
					t.setUserId(uSession.getUserId());
					
					TransactionStatus ts = new TransactionStatus();
					ts = tServ.findTStatusByName("open");
					
					t.setId(tServ.addToCart(uSession, t));
					
					response.getWriter().write(om.writeValueAsString(t));
				} else {
					response.sendError(400, "Field listing error.");
				}
			} else {
				response.sendError(400, "Must be logged in to shop.");
			}
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		
	}
	
}