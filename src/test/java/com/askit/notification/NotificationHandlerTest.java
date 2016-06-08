package com.askit.notification;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class NotificationHandlerTest {

	@Test
	public void testGetInstance() {
		assertThat("instance is null", NotificationHandler.getInstance() != null);
		assertThat("wrong instance", NotificationHandler.getInstance() instanceof NotificationHandler);
	}

	@Test
	public void testNotification() {
		final Notification notification1 = new Notification("1", "", "", "");
		final Notification notification2 = new Notification("2", "", "", "");
		final NotificationHandler handler = NotificationHandler.getInstance();

		handler.addNotification(notification1);
		assertThat("is empty", handler.hasNotifications() == false);
		handler.addNotification(notification2);
		assertThat("is empty", handler.hasNotifications() == false);
		assertThat("wrong notification", handler.getNextNotificationAndDelete().getTo().equals("1"));
		assertThat("is empty", handler.hasNotifications() == false);
		assertThat("wrong notification", handler.getNextNotificationAndDelete().getTo().equals("2"));
		assertThat("is not empty", handler.hasNotifications() == true);
	}

	@Test
	public void testThreadSafety() {
		fail("not implemented yet");
	}
}