package com.askit.entities;

import java.util.Date;

public class Message {

	public final static String TABLE_NAME = "Messages";
	public final static String MESSAGE_ID = "messageID";
	public final static String GROUP_ID = "groupID";
	public final static String USER_ID = "userID";
	public final static String MESSSAGE = "message";
	public final static String DATE = "date";

	private Long messageID;
	private Long groupID;
	private Long userID;
	private String message;
	private Date date;

	public Message() {
		this(null, null, null, null);
	}

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

	public void setMessageID(final Long messageID) {
		this.messageID = messageID;
	}

	public void setGroupID(final Long groupID) {
		this.groupID = groupID;
	}

	public void setUserID(final Long userID) {
		this.userID = userID;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

}