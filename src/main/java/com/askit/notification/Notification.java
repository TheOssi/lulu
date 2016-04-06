package com.askit.notification;

//TODO remove from SQL

public class Notification {

	private final Long userID;
	private final String code;
	private final String[] parameters;

	public Notification(final Long userID, final String code, final String[] parameters) {
		this.userID = userID;
		this.code = code;
		this.parameters = parameters;
	}

	public Long getUserID() {
		return userID;
	}

	public String getCode() {
		return code;
	}

	public String[] getParameters() {
		return parameters;
	}

}
