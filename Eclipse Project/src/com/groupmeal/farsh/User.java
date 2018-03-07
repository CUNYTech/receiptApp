package com.groupmeal.farsh;

public class User {
	private long id;
	private String email;
	private String passHash;
	private String salt;
	private String firstName;
	private String lastName;
	private String phone;
	private long created;
	private byte state;
	private boolean valid;

	User(long inID, String inEmail, String inPassHash, String inSalt, String inFirst, String inLast, String inPhone,
			long inCreated, byte inState) {
		id = inID;
		email = inEmail;
		passHash = inPassHash;
		salt = inSalt;
		firstName = inFirst;
		lastName = inLast;
		phone = inPhone;
		created = inCreated;
		state = inState;
		valid = true;
	}

	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassHash() {
		return passHash;
	}

	public String getSalt() {
		return salt;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhone() {
		return phone;
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
