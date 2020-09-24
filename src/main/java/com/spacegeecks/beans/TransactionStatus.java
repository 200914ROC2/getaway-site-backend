package com.spacegeecks.beans;

public class TransactionStatus {
	
	private int id;
	private String name; // open, sale, return, canceled
	
	public TransactionStatus() {
		id = 0;
		name = "open";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
