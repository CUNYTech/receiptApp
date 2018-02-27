package com.groupmeal.farsh;

public class User {
	/**
	 * This is the identifier for this specific user.
	 */
	public long id;
	/**
	 * This is the email they entered when creating the account.
	 */
	public String email;
	/**
	 * This is the first name they entered when creating the account.
	 */
	public String firstName;
	/**
	 * This is the last name they entered when creating the account.
	 */
	public String lastName;
	/**
	 * This is their phone number as a string.
	 */
	public String phone;
	/**
	 * This is the time, in milliseconds, when the user created their
	 * account.
	 */
	public long created;
	/**
	 * This is currently unused but will keep track of some status on the
	 * account. Like disabled, admin, normal, etc.
	 */
	public byte state;
	/**
	 * This will keep track of whether the current instance of the user is
	 * valid.
	 */
	public boolean valid;
	
	/**
	 * Create a new invalid user as a placeholder or filler when this
	 * information isn't available
	 */
	public User() {
		valid = false;
	}

	/**
	 * Create a new user with the given properties. We won't have to keep
	 * track of the password or salt because Firebase does authentication
	 * for us.
	 */
	public User(long inID, String inEmail, String inFirst, String inLast, String inPhone, long inCreated, byte inState){
	    id = inID;
	    email = inEmail;
	    firstName = inFirst;
	    lastName = inLast;
	    phone = inPhone;
	    created = inCreated;
	    state = inState;
	    valid = true;
	}
}
