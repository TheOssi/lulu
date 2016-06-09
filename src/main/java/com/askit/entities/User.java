package com.askit.entities;

import java.util.Date;

public class User {

	public final static String TABLE_NAME = "Users";
	public final static String USER_ID = "userID";
	public final static String PASSWORD_HASH = "passwordHash";
	public final static String EMAIL = "email";
	public final static String PHONENUMBER_HASH = "phoneNumberHash";
	public final static String USERNAME = "username";
	public final static String ACCESSION_DATE = "accessionDate";
	public final static String PROFILEPICTURE_URI = "profilePictureURI";
	public final static String LANGUAGE = "language";
	public final static String SCORE_OF_GLOBAL = "scoreOfGlobal";

	private Long userID;
	private String passwordHash;
	private String phoneNumberHash;
	private String username;
	private Date accessionDate;
	private String profilePictureURI;
	private String language;
	private int scoreOfGlobal;
	private String email;

	public User() {
	}

	public User(final Long userID, final String passwordHash, final String email, final String phoneNumberHash, final String username,
			final Date accessionDate, final String profilePictureURI, final String language, final int scoreOfGlobal) {
		this.userID = userID;
		this.passwordHash = passwordHash;
		this.phoneNumberHash = phoneNumberHash;
		this.username = username;
		this.accessionDate = accessionDate;
		this.profilePictureURI = profilePictureURI;
		this.language = language;
		this.scoreOfGlobal = scoreOfGlobal;
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public int getScoreOfGlobal() {
		return scoreOfGlobal;
	}

	public void setUserID(final Long userID) {
		this.userID = userID;
	}

	public void setPasswordHash(final String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public void setPhoneNumberHash(final String phoneNumberHash) {
		this.phoneNumberHash = phoneNumberHash;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public void setAccessionDate(final Date accessionDate) {
		this.accessionDate = accessionDate;
	}

	public void setProfilePictureURI(final String profilePictureURI) {
		this.profilePictureURI = profilePictureURI;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	public void setScoreOfGlobal(final int scoreOfGlobal) {
		this.scoreOfGlobal = scoreOfGlobal;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

}