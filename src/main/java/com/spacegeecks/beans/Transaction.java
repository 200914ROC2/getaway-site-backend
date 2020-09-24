package com.spacegeecks.beans;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Transaction {

	private int id;
	private Double amount;
	private Timestamp timeStamp;
	private TransactionStatus status; // open, sale, return, canceled
	private int userId;
	private int merchandiseId;
	
	public Transaction() {
		id = 0;
		amount = 0.0;
		timeStamp = Timestamp.valueOf(LocalDateTime.now());
		status = new TransactionStatus();
		userId = 0;
		merchandiseId = 0;
	}
	
	
	public Double getAmount() {
		return amount;
	}


	public void setAmount(Double amount) {
		this.amount = amount;
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


	public int getMerchandiseId() {
		return merchandiseId;
	}


	public void setMerchandiseId(int merchandiseId) {
		this.merchandiseId = merchandiseId;
	}

}
