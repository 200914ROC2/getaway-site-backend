package com.spacegeecks.delegates;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
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

			Map<String, Object> jsonMap = om.readValue(request.getInputStream(), Map.class);
			if (jsonMap.containsKey("userId")) {
				
				TransactionStatus ts = new TransactionStatus();
				ts = tServ.findTStatusByName("open");
				t.setStatus(ts);
				
				if (jsonMap.containsKey("price") && jsonMap.containsKey("listing_id") && jsonMap.containsKey("title") && jsonMap.containsKey("image") && jsonMap.containsKey("userId")) {
					
					t.setPrice(Double.parseDouble((String) jsonMap.get("price")));
					t.setListingId(((Integer) jsonMap.get("listing_id")));
					t.setTitle((String) jsonMap.get("title"));
					t.setImage((String) jsonMap.get("image"));
					
					t.setUserId((Integer) jsonMap.get("userId"));
					
					t.setId(tServ.addToCart(uSession, t));
					
					response.getWriter().write(om.writeValueAsString(t));
				} else {
					response.sendError(400, "Field listing error.");
				}
			} else {
				response.sendError(401, "Must be logged in to shop.");
			}
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		
	}
	
}

