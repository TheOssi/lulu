package com.askit.entities;

public class Contact {

	private final String userID;
	private final String contactID;

	public Contact(final String userID, final String contactID) {
		this.userID = userID;
		this.contactID = contactID;
	}

	public String getUserID() {
		return userID;
	}

	public String getContactID() {
		return contactID;
	}

}
