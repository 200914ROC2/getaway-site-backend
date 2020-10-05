package com.spacegeecks.delegates;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.spacegeecks.beans.Role;
import com.spacegeecks.beans.User;
import com.spacegeecks.data.UserPostgres;
import com.spacegeecks.services.UserService;

public class RegistrationDelegate implements FrontControllerDelegate {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		if ("POST".equals(request.getMethod())) {
			register(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}

	private void register(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		User u = new User();
		UserService uServ = new UserService(new UserPostgres());
		ObjectMapper om = new ObjectMapper();

		Map<String, Object> jsonMap = om.readValue(request.getInputStream(), Map.class);
		
		if (jsonMap.containsKey("username") && jsonMap.containsKey("password") && jsonMap.containsKey("email") && jsonMap.containsKey("firstname") && jsonMap.containsKey("lastname")) {
			u.setUsername((String) jsonMap.get("username"));
			String password = (String) jsonMap.get("password");
			u.setEmail((String) jsonMap.get("email"));
			u.setFirstName((String) jsonMap.get("firstname"));
			u.setLastName((String) jsonMap.get("lastname"));
			Role r = new Role();
			r = uServ.getRoleByName("standard");
			uServ.updatePassword(u, password); // The object mapper sets the pw to plantext; this hashes it.
			u.setRole(r);
		} else {
			response.sendError(400, "All fields required.");
		}
		
		if (isUnique(u.getUsername(), u.getEmail())) {
			int uId = uServ.registerUser(u);
			u.setUserId(uId);
			response.getWriter().write(om.writeValueAsString(u));
		} else {
			response.sendError(400, "Sorry, that username or email is already in use.");
		}
	}
	
	private boolean isUnique(String username, String email) {
		UserService uServ = new UserService(new UserPostgres());
		
		if (uServ.getUserByUsername(username) != null)
			return false;
		else if (uServ.getUserByEmail(email) != null)
			return false;
		else
			return true;
	}
}
