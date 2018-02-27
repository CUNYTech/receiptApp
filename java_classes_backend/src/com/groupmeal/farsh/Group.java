package com.groupmeal.farsh;

public class Group {
	private String id;
	private int users;
	private long created;
	private byte state;
	private boolean valid;
	
	Group(String inID, int inUser, long inCreated, byte inState){
		id = inID;
		users = inUser;
		created = inCreated;
		state = inState;
		valid = true;
		
	}
	public String getId() {
		return id;
	}
	
	public int getUsers() {
		return users;
	}
	
	public long getCreated() {
		return created;
	}
	
	public byte getState() {
		return state;
	}
	
	public boolean isValid() {
		return valid;
	}
}
