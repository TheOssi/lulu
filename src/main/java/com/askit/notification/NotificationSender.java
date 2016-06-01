package com.askit.notification;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import com.askit.exception.ExceptionHandler;
import com.askit.exception.NotificationException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NotificationSender implements Runnable {

	private static final int SLEEP_TIME = 5000;
	private static final NotificationSender INSTANCE = new NotificationSender();
	private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";

	private final GsonBuilder gsonBuilder = new GsonBuilder();
	private final Gson gson = gsonBuilder.setPrettyPrinting().create();
	private final ExceptionHandler exceptionHandler = ExceptionHandler.getInstance();
	private final NotificationHandler notificationHandler = NotificationHandler.getInstance();

	private Thread sendNotificationThread;
	private final String authKey;

	private NotificationSender() {
		authKey = getAuthKey();
	}

	public static synchronized NotificationSender getInstace() {
		return INSTANCE;
	}

	private void send(final Notification notification) throws NotificationException {
		try {
			final URL url = new URL(FCM_URL);
			final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization", "key=" + authKey);
			connection.setDoOutput(true);
			final DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
			final String content = gson.toJson(notification);
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

			final String errorText = getErrorText(response.toString());
			if (responseCode == 200 && errorText.equals("NotRegistered")) {
				NotificationHandler.getInstance().addNotification(notification);
			} else if ((responseCode == 200 || responseCode >= 500)
					&& (errorText.equals("Unavailable") || errorText.equals("error:InternalServerError"))) {
				NotificationHandler.getInstance().addNotification(notification);
			} else if (errorText.length() > 0) {
				throw new NotificationException("Internal Error with code " + responseCode + " and response '" + response.toString() + "'");
			}

			// TODO 200 + error: DeviceMessageRate Exceeded

		} catch (final IOException e) {
			exceptionHandler.handleError(e);
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
					exceptionHandler.handleError(e);
				}
			} else {
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (final InterruptedException e) {
					if (!sendNotificationThread.isAlive() || sendNotificationThread.isInterrupted()) {
						sendNotificationThread.start();
					}
				}
			}
		}
	}

	// TODO what if not avaible
	private String getErrorText(final String json) {
		final JsonElement parsedJSON = new JsonParser().parse(json);
		JsonObject jsonObject = parsedJSON.getAsJsonObject();
		final JsonArray jsonArray = jsonObject.getAsJsonArray("results");
		jsonObject = jsonArray.get(0).getAsJsonObject();
		final String result = jsonObject.get("error").getAsString();
		return result;
	}

	private String getAuthKey() {
		String authKey = "";
		try {
			final File propertiesFile = new File("./config/cm.properties");
			if (propertiesFile.exists() == false) {
				throw new IOException("Properties file (" + propertiesFile.getAbsolutePath() + ") does not exist");
			}

			final Properties properties = new Properties();
			final InputStream inputStream = new FileInputStream(propertiesFile);
			properties.load(inputStream);
			closeSilentlyInputStream(inputStream);
			authKey = properties.getProperty("auth_key");
		} catch (final IOException e) {
			exceptionHandler.handleError(e);
		}
		return authKey;
	}

	private static void closeSilentlyInputStream(final InputStream inputStream) {
		try {
			inputStream.close();
		} catch (final IOException e) {
			ExceptionHandler.getInstance().handleError(e);
		}
	}
}