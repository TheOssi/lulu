package com.askit.entities;

import com.thirdparty.entities.Column;
import com.thirdparty.entities.Entity;

@Entity
public class Notification {

	@Column(name = "notificationID")
	private Long notificationID;
	@Column(name = "code")
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
