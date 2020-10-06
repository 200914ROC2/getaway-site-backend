package com.spacegeecks.beans;

public class UserPhoto {
	private int userPhotoId;
	private String photoCredit;
	private PhotoStatus photoStatus;
	private int userId;
	
	
	public UserPhoto(int userPhotoId, String userPhoto, PhotoStatus photoStatus) {
		super();
		this.userPhotoId = userPhotoId;
		this.photoStatus = photoStatus;
	}
	public UserPhoto() {
		
	}
	
	public int getUserPhotoId() {
		return userPhotoId;
	}
	public void setUserPhotoId(int userPhotoId) {
		this.userPhotoId = userPhotoId;
	}
	public PhotoStatus getPhotoStatus() {
		return photoStatus;
	}
	public void setPhotoStatus(PhotoStatus photoStatus) {
		this.photoStatus = photoStatus;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPhotoCredit() {
		return photoCredit;
	}
	public void setPhotoCredit(String photoCredit) {
		this.photoCredit = photoCredit;
	}
}
