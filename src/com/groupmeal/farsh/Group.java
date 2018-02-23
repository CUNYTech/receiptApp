package com.groupmeal.farsh;

public class Group {
	/**
	 * This is a comment!
	 */
	private String id;
	/**
	 * This is another comment!
	 */
	private int users;
	/**
	 * This is also a comment!
	 */
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
