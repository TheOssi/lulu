package com.askit.notification;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.askit.exception.NotificationException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NotificationSender implements Runnable {

	private static final NotificationSender INSTANCE = new NotificationSender();
	private static final String AUTH_KEY = "";
	private final static String GCM_URL = "https://android.googleapis.com/gcm/send";

	private Thread sendNotificationThread;

	private final GsonBuilder gsonBuilder = new GsonBuilder();
	private final Gson gson = gsonBuilder.setPrettyPrinting().create();
	private final NotificationHandler notificationHandler = NotificationHandler.getInstace();

	private NotificationSender() {
	}

	public static synchronized NotificationSender getInstace() {
		return INSTANCE;
	}

	private void send(final Notification notification) throws NotificationException {
		try {
			final URL url = new URL(GCM_URL);
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization", "key=" + AUTH_KEY);
			connection.setDoOutput(true);
			final DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
			final String content = gson.toJson(notificationHandler.getNextNotificationAndDelete());
			dataOutputStream.writeBytes(content);
			dataOutputStream.flush();
			dataOutputStream.close();
		} catch (final IOException e) {
			e.printStackTrace();
			throw new NotificationException(e);
		}
	}

	public void startThread() {
		if (sendNotificationThread == null || !sendNotificationThread.isAlive()) {
			sendNotificationThread = new Thread(INSTANCE);
			sendNotificationThread.start();
		}
	}

	@Override
	public void run() {
		while (true) {
			if (notificationHandler.hasNotifications()) {
				try {
					send(notificationHandler.getNextNotificationAndDelete());
				} catch (final NotificationException e) {
					e.printStackTrace();
				}
			} else {
				try {
					Thread.sleep(5000);
				} catch (final InterruptedException e) {
					if (!sendNotificationThread.isAlive() || sendNotificationThread.isInterrupted()) {
						sendNotificationThread.start();
					}
				}
			}
		}
	}

}