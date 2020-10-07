package com.spacegeecks.beans;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Transaction {

	private int id;
	private double price;
	private Timestamp timeStamp;
	private TransactionStatus status; // open, sale, return, canceled
	private int userId;
	private int listingId;
	private String title;
	private String image;
	
	public Transaction() {
		id = 0;
		price = 0.0;
		timeStamp = Timestamp.valueOf(LocalDateTime.now());
		status = new TransactionStatus();
		userId = 0;
		listingId = 0;
		title = "OBJECT";
		image = "IMAGE";
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int transactionId) {
		this.id = transactionId;
	}

	public int getListingId() {
		return listingId;
	}

	public void setListingId(int listingId) {
		this.listingId = listingId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
