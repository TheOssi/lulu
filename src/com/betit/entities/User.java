package com.betit.entities;

import java.util.Date;

public class User {

	private Long userID;
	private final String phoneNrHash;
	private final String username;
	private final String userHash;
	private final Date accessionDate;
	private String profilePictureURI;
	private final int globaleScore;

	public User(final String phoneNrHash, final String username, final String userHash, final Date accessionDate, final int globaleScore) {
		this.phoneNrHash = phoneNrHash;
		this.username = username;
		this.userHash = userHash;
		this.accessionDate = accessionDate;
		this.globaleScore = globaleScore;
	}

	public Long getUserID() {
		return userID;
	}

	public String getPhoneNrHash() {
		return phoneNrHash;
	}

	public String getUsername() {
		return username;
	}

	public String getUserHash() {
		return userHash;
	}

	public Date getAccessionDate() {
		return accessionDate;
	}

	public String getProfilePictureURI() {
		return profilePictureURI;
	}

	public int getGlobaleScore() {
		return globaleScore;
	}
}