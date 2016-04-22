package com.askit.etc;

import com.askit.notification.Notification;

public enum NotificationCodes {

	TEST_NOT(1, new Notification("ab", "ab", "bvs"));
	int code;
	Notification notification;

	private NotificationCodes(final int code, final Notification notification) {
		this.code = code;
		this.notification = notification;
	}
}
