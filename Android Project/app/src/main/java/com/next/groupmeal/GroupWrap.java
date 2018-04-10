package com.next.groupmeal;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class GroupWrap {
	@Exclude
	public String groupID;
	public String owner;
	public String groupName;
	public ArrayList<String> users;

	public GroupWrap() {
	}

	public GroupWrap(String groupID, String owner, String groupName) {
		this.groupID = groupID;
		this.owner = owner;
		this.groupName = groupName;
		this.users = new ArrayList<>();
	}
}
