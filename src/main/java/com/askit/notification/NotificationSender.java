package com.askit.notification;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.askit.exception.NotificationException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NotificationSender implements Runnable {

	private static final NotificationSender INSTANCE = new NotificationSender();
	private static final String AUTH_KEY = ""; // TODO get this from S.
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

			final int responseCode = connection.getResponseCode();
			final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String responseString;
			final StringBuilder response = new StringBuilder();

			while ((responseString = in.readLine()) != null) {
				response.append(responseString);
			}
			in.close();

			final String errorText = getErrorText(responseString);
			if (responseCode == 200 && errorText.equals("NotRegistered")) {
				NotificationHandler.getInstace().addNotification(notification);
			} else if ((responseCode == 200 || responseCode >= 500)
					&& (errorText.equals("Unavailable") || errorText.equals("error:InternalServerError"))) {
				NotificationHandler.getInstace().addNotification(notification);
			} else if (errorText.length() > 0) {
				throw new NotificationException("Internal Error with code " + responseCode + "and response '" + responseString + "'");
			}

			// TODO 200 + error: DeviceMessageRate Exceeded

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

	// TODO what if not avaible (waiting for S.)
	public String getErrorText(final String json) {
		final JsonElement parsedJSON = new JsonParser().parse(json);
		JsonObject jsonObject = parsedJSON.getAsJsonObject();
		final JsonArray jsonArray = jsonObject.getAsJsonArray("results");
		jsonObject = jsonArray.get(0).getAsJsonObject();
		final String result = jsonObject.get("error").toString();
		return result;
	}

}