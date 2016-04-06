package com.askit.notification;

import java.util.HashMap;
import java.util.Map;

public class NotificationManager {

	// TODO threadSafe

	private final static NotificationManager INSTANCE = new NotificationManager();

	// private final List<Notification> notifications = new
	// ArrayList<Notification>();
	Map<Long, Notification[]> notificaions = new HashMap<Long, Notification[]>();

	private NotificationManager() {
	}

	public synchronized NotificationManager getInstace() {
		return INSTANCE;
	}

	public Notification[] getNotificationsOfUser(final long userID) {
		return notificaions.get(userID);
	}

	public boolean hasUserNotifications(final long userID) {
		return notificaions.containsKey(userID);
	}

	public void addNotificationToUser(final long userID, final Notification notification) {
		if (hasUserNotifications(userID)) {
			final Notification[] oldNotifications = getNotificationsOfUser(userID);
			final Notification[] newNotifications = new Notification[oldNotifications.length + 1];
			for (int i = 0; i < oldNotifications.length; i++) {
				newNotifications[i] = oldNotifications[i];
			}
			newNotifications[oldNotifications.length] = notification;
			notificaions.replace(userID, newNotifications); // TODO right?
		} else {
			notificaions.put(new Long(userID), new Notification[] { notification });
		}
	}

	public void deleteNotificationsOfUser() {

	}
}
