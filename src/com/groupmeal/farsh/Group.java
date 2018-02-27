package com.groupmeal.farsh;

public class Group {
	/**
	 * This will keep a key to the group for when users want to connect to
	 * their friend's groups
	 */
	public long id;
	/**
	 * This keeps track of how many users are currently in the group.
	 */
	public int users;
	/**
	 * This is the time, in milliseconds, the group was created.
	 */
	public long created;
	/**
	 * This is currently unused but will keep track of some status on the
	 * account. Like disabled, admin, normal, etc.
	 */
	public byte state;
	/**
	 * This will keep track of whether the current instance of the group
	 * is valid.
	 */
	public boolean valid;
	
	/**
	 * Create a new blank group. This group is invalid by default.
	 */
	public Group() {
		valid = false;
	}
	
	/**
	 * Create a new group with an id, user id, time created, and state.
	 * 
	 * @param inID The id of the group, as a hexadecimal long.
	 * @param inUser
	 * @param inCreated
	 * @param inState
	 */
	public Group(long id, int users, long created, byte state) {
		this.id = id;
		this.users = users;
		this.created = created;
		this.state = state;
		valid = true;
	}
}
