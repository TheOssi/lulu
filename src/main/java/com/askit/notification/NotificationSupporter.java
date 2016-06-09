package com.askit.notification;

import com.askit.database.DatabaseQueryManager;
import com.askit.database.QueryManager;
import com.askit.entities.User;
import com.askit.exception.DatabaseLayerException;
import com.askit.exception.NotificationException;

public class NotificationSupporter {

	private static final QueryManager QUERY_MANAGER = new DatabaseQueryManager();

	public static void sendNotificationToAllMembersOfAGroup(final Notification notification, final Long groupID) throws DatabaseLayerException,
			NotificationException {
		final User[] users = QUERY_MANAGER.getUsersOfGroup(groupID);
		sendNotificationToUsers(notification, users);
	}

	public static void sendNotificationToAllMembersOfPrivateQuestion(final Notification notification, final Long questionID)
			throws DatabaseLayerException, NotificationException {
		final User[] users = QUERY_MANAGER.getUsersOfPrivateQuestion(questionID);
		sendNotificationToUsers(notification, users);
	}

	public static void sendNotificationToAllMembersOfPublicQuestion(final Notification notification, final Long questionID)
			throws DatabaseLayerException, NotificationException {
		final User[] users = QUERY_MANAGER.getUsersOfPublicQuestion(questionID);
		sendNotificationToUsers(notification, users);
	}

	private static void sendNotificationToUsers(final Notification notification, final User[] users) throws NotificationException {
		for (final User user : users) {
			if (notification.getTo() == null) {
				final Long userID = user.getUserID();
				final String regID = RegIDHandler.getInstance().getRegIDFromUser(userID);
				if (regID != null) {
					notification.setTo(regID);
				} else {
					throw new NotificationException("No regID for User: " + userID);
				}
			}
			NotificationHandler.getInstance().addNotification(notification);
		}
	}
}