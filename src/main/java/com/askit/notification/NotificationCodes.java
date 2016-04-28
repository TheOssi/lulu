package com.askit.notification;

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
	String code;

	private NotificationCodes(final String code) {
		this.code = code;
	}
	public String getCode(){
		return this.code;
	}
}
