package com.spacegeecks.delegates;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.spacegeecks.beans.User;
import com.spacegeecks.data.UserPostgres;
import com.spacegeecks.services.UserService;

public class LoginDelegate implements FrontControllerDelegate {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		if ("POST".equals(request.getMethod())) {
			logIn(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}

	private void logIn(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		
		UserService uServ = new UserService(new UserPostgres());
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> jsonMap = om.readValue(request.getInputStream(), Map.class);
		
		String username = (String) jsonMap.get("username");
		String password = (String) jsonMap.get("password");
		
		User u = uServ.loginUser(username, password);
		
		if (u != null) {
			request.getSession().setAttribute("user", u);
			response.getWriter().write((om.writeValueAsString(u)));
			System.out.println("printing the session ...");
			System.out.println(request.getSession());
		} else {
			response.setHeader("INCORRECT_CREDENTIALS", "Incorrect cr");
			response.sendError(400, "invalid credentials");
		
		}
	}
	
}

