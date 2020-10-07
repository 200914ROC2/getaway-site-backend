package com.spacegeecks.beans;

public class PhotoStatus {
	private int photoStatusId;
	private String photoStatus;
	
	public PhotoStatus(int photoStatusId, String photoStatus) {
		super();
		this.photoStatusId = photoStatusId;
		this.photoStatus = photoStatus;
	}
	
	public int getPhotoStatusId() {
		return photoStatusId;
	}
	public void setPhotoStatusId(int photoStatusId) {
		this.photoStatusId = photoStatusId;
	}
	public String getPhotoStatus() {
		return photoStatus;
	}
	public void setPhotoStatus(String photoStatus) {
		this.photoStatus = photoStatus;
	}
	
}
