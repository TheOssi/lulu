package com.askit.entities;

import java.util.Date;

public class Message {

	private final Long messageID;
	private final Long groupID;
	private final Long userID;
	private final String message;
	private final Date date;

	public Message(final Long groupID, final Long userID, final String message, final Date date) {
		this(null, groupID, userID, message, date);
	}

	public Message(final Long messageID, final Long groupID, final Long userID, final String message, final Date date) {
		this.messageID = messageID;
		this.groupID = groupID;
		this.userID = userID;
		this.message = message;
		this.date = date;
	}

	public Long getMessageID() {
		return messageID;
	}

	public Long getGroupID() {
		return groupID;
	}

	public Long getUserID() {
		return userID;
	}

	public String getMessage() {
		return message;
	}

	public Date getDate() {
		return date;
	}
}