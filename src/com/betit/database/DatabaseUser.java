package com.betit.database;

public enum DatabaseUser {

	// TODO passes alter
	READ_USER("betAppReader", "marciMarcMarc"),
	WRITE_USER("betAppWriter", "felliFellFell"),
	DELETE_USER("betAppDeleter", "lenkiLenkLenk");

	private String password;
	private String username;

	private DatabaseUser(final String password, final String username) {
		this.password = password;
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}
}
