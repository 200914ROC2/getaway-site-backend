package com.spacegeecks.beans;

public class Role {

	private int id;
	private String name; // Admin, Employee, or Standard
	
	public Role() {
		id = 0;
		name = "New";
	}

	public int getId() {
		return id;
	}

	public void setId(int roleId) {
		this.id = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
