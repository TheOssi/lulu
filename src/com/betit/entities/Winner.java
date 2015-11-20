package com.betit.entities;

public class Winner {

	private final Long bqID;
	private final Long userID;

	public Winner(final Long bqID, final Long userID) {
		this.bqID = bqID;
		this.userID = userID;
	}

	public Long getBqID() {
		return bqID;
	}

	public Long getUserID() {
		return userID;
	}
}
