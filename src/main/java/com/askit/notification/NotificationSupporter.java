package com.askit.notification;

import com.askit.database.DatabaseQueryManager;
import com.askit.database.QueryManager;
import com.askit.entities.User;
import com.askit.exception.DatabaseLayerException;
import com.askit.exception.NotificationException;

//TODO better class name
//TODO implement all methods

public class NotificationCreator {

	public NotificationCreator() {

	}

	public static void sendNotificationToAllMembersOfAGroup(final Notification not, final Long groupID) throws DatabaseLayerException, NotificationException {
		final QueryManager queryManager = new DatabaseQueryManager();
		final User[] users = queryManager.getUsersOfGroup(groupID);
		final RegIDHandler regIDHandler = RegIDHandler.getInstance();
		final NotificationHandler notificationHandler = NotificationHandler.getInstance();
		for (final User user : users) {
			if (not.getTo() == null) {
				final Long userID = user.getUserID();
				final String regID = regIDHandler.getRegIDFromUser(userID);
				if (regID != null) {
					not.setTo(regID);
				} else {
					throw new NotificationException("No regID for User: " + userID);
				}

			}
			notificationHandler.addNotification(not);
		}
	}
}
