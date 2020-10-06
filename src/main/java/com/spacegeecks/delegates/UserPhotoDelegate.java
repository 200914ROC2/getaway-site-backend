package com.spacegeecks.delegates;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacegeecks.beans.User;
import com.spacegeecks.beans.UserPhoto;
import com.spacegeecks.data.UserPhotosPostgres;
import com.spacegeecks.services.UserPhotoService;

public class UserPhotoDelegate implements FrontControllerDelegate {
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ObjectMapper om = new ObjectMapper();
		UserPhotosPostgres userPhotoDao = new UserPhotosPostgres();
		UserPhotoService us = new UserPhotoService(userPhotoDao);
		
		if("GET".equals(request.getMethod())) {
			HashSet<UserPhoto> userPhotos = (HashSet<UserPhoto>) us.getAllApprovedUserPhotos();
			if(userPhotos != null) {
				response.getWriter().write(om.writeValueAsString(userPhotos));
				response.setStatus(200);
			} else {
				response.sendError(500);
			}
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
			
			
		
	}

}
