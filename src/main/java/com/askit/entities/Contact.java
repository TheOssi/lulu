package com.askit.entities;

import com.thirdparty.entities.Column;
import com.thirdparty.entities.Entity;

@Entity
public class Contact {

	public final static String USER_ID = "userID";
	public final static String CONTACT_ID = "contactID";

	@Column(name = "userID")
	private String userID;
	@Column(name = "contactID")
	private String contactID;

	public Contact() {
	}

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

	public void setUserID(final String userID) {
		this.userID = userID;
	}

	public void setContactID(final String contactID) {
		this.contactID = contactID;
	}

}