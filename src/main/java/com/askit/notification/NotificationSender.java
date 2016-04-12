package com.askit.notification;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ConcurrentLinkedQueue;

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
	private final ConcurrentLinkedQueue<Notification> notifications = new ConcurrentLinkedQueue<Notification>();

	private NotificationSender() {
	}

	public static synchronized NotificationSender getInstace() {
		return INSTANCE;
	}

	public void sendNotification(final Notification notification) {
		notifications.offer(notification);
	}

	private Notification getNextNotificationAndDelete() {
		return notifications.poll();
	}

	private boolean hasNotifications() {
		return notifications.isEmpty();
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
			final String content = gson.toJson(getNextNotificationAndDelete());
			dataOutputStream.writeBytes(content);
			dataOutputStream.flush();
			dataOutputStream.close();

			// TODO output
			final int responseCode = connection.getResponseCode();
			System.out.println("Response Code : " + responseCode);

			final BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;

			while ((inputLine = responseReader.readLine()) != null) {
				System.out.println(inputLine);
			}
			responseReader.close();

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
			if (hasNotifications()) {
				try {
					send(getNextNotificationAndDelete());
				} catch (final NotificationException e) {
					e.printStackTrace(); // TODO
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
