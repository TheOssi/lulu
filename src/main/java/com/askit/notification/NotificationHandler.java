package com.askit.notification;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * All notifications to send are stored in this class. Implemented as a queue it
 * provides only the necessary methods with better naming.
 * 
 * @author Kai Müller
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class NotificationHandler {

	private static final NotificationHandler INSTANCE = new NotificationHandler();

	private final ConcurrentLinkedQueue<Notification> notifications = new ConcurrentLinkedQueue<Notification>();

	private NotificationHandler() {
	}

	/**
	 * Because this class is implemented as a singelon, this method returns the
	 * only instance if the notification handler
	 * 
	 * @return the only instance of the notification handler
	 */
	public static synchronized NotificationHandler getInstance() {
		return INSTANCE;
	}

	/**
	 * Adds a notification to the queue
	 * 
	 * @param notification
	 *            the notifcation to add
	 */
	public void addNotification(final Notification notification) {
		notifications.offer(notification);
	}

	/**
	 * get and deletes the next notification in the queue
	 * 
	 * @return the next notification
	 */
	public Notification getNextNotificationAndDelete() {
		return notifications.poll();
	}

	/**
	 * Are any notifications in the queue?
	 * 
	 * @return true, if any notification is in queue, false if not
	 */
	public boolean hasNotifications() {
		return notifications.isEmpty();
	}
}