package com.groupmeal.farsh;

/**
 * This class will keep track of a group that the
 * user can join to begin a meal with their
 * friends. Upon creating a group one of two
 * things will happen:
 *  - The user will be given a QR code that they
 *    will allow their friends to scan to join
 *    this group. This can be good because it
 *    ensures that the users joining this group
 *    will be near the original user.
 *    
 *  - Or the user will be given a key that they
 *    will enter into the app to join the group.
 *    Instead of a QR code and scanning, it's
 *    only a string of characters they have to
 *    manually enter into the application. This
 *    will be MUCH easier that the QR code but
 *    we can't always be sure that the group can
 *    even be valid.
 */
public class Group {
	/**
	 * This will keep the track of the special ID
	 * of this group that will allow other users
	 * to join this group.
	 */
	private String id;
	/**
	 * This will keep track of how many users are
	 * actually in this group. This might be
	 * removed as it probably isn't important.
	 */
	private int users;
	/**
	 * This will keep track of the exact
	 * milliseconds this group was created.
	 * 
	 * We can possibly have the groups have a
	 * timeout, meaning. After 5 hours, the group
	 * automatically destroys itself to prevent
	 * malicious use, or unnecessary groups as
	 * well saving data on our servers.
	 */
	private long created;
	/**
	 * This is the current state of the group.
	 * This can indicate multiple states of the
	 * groups. This might keep track of whether
	 * the group is locked or not, visible or
	 * not, and multiple different things to
	 * possibly keep track of. 
	 */
	private byte state;
	/**
	 * This keeps track of whether this group was
	 * properly loaded from the database. If it's
	 * not, we'll be able to notify the user that
	 * there was a problem with the loading, etc.
	 */
	private boolean valid;

	/**
	 * This will create a new group with the
	 * given id, amount of users, creation time,
	 * and the state of the group.
	 */
	Group(String inID, int inUser, long inCreated, byte inState) {
		id = inID;
		users = inUser;
		created = inCreated;
		state = inState;
		valid = true;

	}

	/**
	 * @see #id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @see #users
	 */
	public int getUsers() {
		return users;
	}

	/**
	 * @see #created
	 */
	public long getCreated() {
		return created;
	}

	/**
	 * @see #state
	 */
	public byte getState() {
		return state;
	}

	/**
	 * @see #valid
	 */
	public boolean isValid() {
		return valid;
	}
}
