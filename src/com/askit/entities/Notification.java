package com.askit.entities;

public class Notification {

	private final Long notificationID;
	private final String code;
	private final String[] parameter;

	public Notification(final Long notificationID, final String code, final String[] parameter) {
		this.notificationID = notificationID;
		this.code = code;
		this.parameter = parameter;
	}

	public Long getNotificationID() {
		return notificationID;
	}

	public String getCode() {
		return code;
	}

	public String[] getParameter() {
		return parameter;
	}

}
