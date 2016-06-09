package com.askit.notification;

/**
 * This enum holds the code for the notification kinds.
 * 
 * @author Max Lenk
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public enum NotificationCodes {

	NOTIFICATION_NEW_QUESTION("NQ"),
	NOTIFICATION_ADDED_TO_GROUP("ATG"),
	NOTIFICATION_ANSWER_SET("AS"),
	NOTIFICATION_QUESTION_END("AE"),
	NOTIFICATION_WON_BET("WB"),
	NOTIFICATION_LOST_BET("LB"),
	NOTIFICATION_QUESTION_SOON_END("QSE"),
	NOTIFICATION_NEW_MESSAGE("NM"),
	NOTIFICATION_INVITE_PUBLIC("IP");

	private String code;

	private NotificationCodes(final String code) {
		this.code = code;
	}

	/**
	 * get the code of a notification kind
	 * 
	 * @return the code of the notification kind
	 */
	public String getCode() {
		return code;
	}
}