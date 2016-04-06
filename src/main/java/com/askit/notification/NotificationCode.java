package com.askit.notification;

public enum NotificationCode {

	TEST_CODE("test");

	private String code;

	private NotificationCode(final String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
