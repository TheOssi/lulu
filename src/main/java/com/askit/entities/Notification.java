package com.askit.entities;

public class Notification {

	public static final String NOTIFICATION_ID = "notificationID";
	public static final String CODE = "code";

	private Long notificationID;
	private String code;
	private String[] parameter;

	public Notification() {
	}

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

	public void setNotificationID(final Long notificationID) {
		this.notificationID = notificationID;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public void setParameter(final String[] parameter) {
		this.parameter = parameter;
	}

}
