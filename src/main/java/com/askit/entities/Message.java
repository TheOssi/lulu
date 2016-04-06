package com.askit.entities;

import java.util.Date;

import com.thirdparty.entities.Column;
import com.thirdparty.entities.Entity;

@Entity
public class Message {

	public final static String MESSAGE_ID = "messageID";
	public final static String GROUP_ID = "groupID";
	public final static String USER_ID = "userID";
	public final static String MESSSAGE = "message";
	public final static String DATE = "date";

	@Column(name = "messageID")
	private Long messageID;
	@Column(name = "groupID")
	private Long groupID;
	@Column(name = "userID")
	private Long userID;
	@Column(name = "message")
	private String message;
	@Column(name = "date")
	private Date date;

	public Message() {
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