package com.askit.notification;

import java.util.concurrent.ConcurrentLinkedQueue;

public class NotificationHandler {

	private static final NotificationHandler INSTANCE = new NotificationHandler();

	private final ConcurrentLinkedQueue<Notification> notifications = new ConcurrentLinkedQueue<Notification>();

	private NotificationHandler() {
	}

	public static synchronized NotificationHandler getInstace() {
		return INSTANCE;
	}

	public void addNotification(final Notification notification) {
		notifications.offer(notification);
	}

	public Notification getNextNotificationAndDelete() {
		return notifications.poll();
	}

	public boolean hasNotifications() {
		return notifications.isEmpty();
	}

}