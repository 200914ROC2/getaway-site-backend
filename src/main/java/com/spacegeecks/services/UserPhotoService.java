package com.spacegeecks.services;

import java.util.Set;

import com.spacegeecks.beans.UserPhoto;
import com.spacegeecks.data.UserPhotosDAO;

public class UserPhotoService {
	private UserPhotosDAO userPhotoDao;

	public UserPhotoService(UserPhotosDAO userPhotoDao) {
		super();
		this.userPhotoDao = userPhotoDao;
	}
	
	public Set<UserPhoto> getAllApprovedUserPhotos() {
		return userPhotoDao.getAllApprovedUserPhotos();
	}
	
	
}
