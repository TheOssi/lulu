package com.betit.entities;

import java.util.Date;

public class User {

	private Long userID;
	private final String passwordHash;
	private final String username;
	private final String language;
	private final Date accessionDate;
	private String profilePictureURI;
	private final int globaleScore;

	public User(final String passwordHash, final String username, final Date accessionDate, final int globaleScore, final String language) {
		this.passwordHash = passwordHash;
		this.username = username;
		this.accessionDate = accessionDate;
		this.globaleScore = globaleScore;
		this.language = language;
	}

	public User(final String phoneNrHash, final String username, final int globaleScore, final String language) {
		this(phoneNrHash, username, null, globaleScore, language);
	}

	public Long getUserID() {
		return userID;
	}

	public String getPhoneNrHash() {
		return passwordHash;
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

	public int getGlobaleScore() {
		return globaleScore;
	}

	public String getLanguage() {
		return language;
	}
}