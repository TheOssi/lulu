package com.askit.entities;

import java.util.Date;

public class User {

	private final Long userID;
	private final String passwordHash;
	private final String phoneNumberHash;
	private final String username;
	private final Date accessionDate;
	private final String profilePictureURI;
	private final String language;
	private final int scoreOfGlobal;

	public User(final Long userID, final String passwordHash, final String phoneNumberHash, final String username, final Date accessionDate,
			final String profilePictureURI, final String language, final int scoreOfGlobal) {
		this.userID = userID;
		this.passwordHash = passwordHash;
		this.phoneNumberHash = phoneNumberHash;
		this.username = username;
		this.accessionDate = accessionDate;
		this.profilePictureURI = profilePictureURI;
		this.language = language;
		this.scoreOfGlobal = scoreOfGlobal;
	}

	public Long getUserID() {
		return userID;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public String getPhoneNumberHash() {
		return phoneNumberHash;
	}

	public String getUsername() {
		return username;
	}

	public Date getAccessionDate() {
		return accessionDate;
	}

	public String getProfilePictureURI() {
		return profilePictureURI;
	}

	public String getLanguage() {
		return language;
	}

	public int getScoreOfGlobal() {
		return scoreOfGlobal;
	}

}