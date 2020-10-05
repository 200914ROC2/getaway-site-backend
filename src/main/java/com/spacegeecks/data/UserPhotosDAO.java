package com.spacegeecks.data;

import java.util.Set;

import com.spacegeecks.beans.PhotoStatus;
import com.spacegeecks.beans.UserPhoto;

public interface UserPhotosDAO {
	
	public Set<UserPhoto> getAllApprovedUserPhotos();
	public Set<UserPhoto> getAllUnApprovedUserPhotos();
	public boolean updateUserPhoto(PhotoStatus photoStatus, int id);
	public int addUserPhoto(UserPhoto userPhoto);
	public PhotoStatus getPhotoStatusByName(String name);

}
