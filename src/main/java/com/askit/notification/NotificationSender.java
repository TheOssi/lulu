package com.askit.notification;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.PropertyException;

import com.askit.exception.ExceptionHandler;
import com.askit.exception.NotificationException;
import com.askit.util.PropertiesFileHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The notification sender sends the notifications stored in the
 * {@link NotificationHandler}. A thread loops of ther the handler and sends the
 * notification. If no notification is currently avaible, the thread sleeps for
 * some time. If a exception occurs, a exception is thrown and handle by the
 * {@link ExceptionHandler}. The not send notification is add to the
 * notification handler.
 * 
 * @author Kai Müller
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class NotificationSender implements Runnable {

	private static final String JSON_FIELD_ERROR = "error";
	private static final String JSON_FIELD_RESULTS = "results";
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

	/**
	 * The notification sender is implemented as a singelton, so this methods
	 * returns the only instance of the sender.
	 * 
	 * @return the only instance of the sender
	 */
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

	/**
	 * starts the sending thread
	 */
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

	private String getErrorText(final String json) {
		final JsonElement parsedJSON = new JsonParser().parse(json);
		JsonObject jsonObject = parsedJSON.getAsJsonObject();
		String result = "";
		if (jsonObject.has(JSON_FIELD_RESULTS)) {
			final JsonArray jsonArray = jsonObject.getAsJsonArray(JSON_FIELD_RESULTS);
			if (jsonArray.size() > 0) {
				jsonObject = jsonArray.get(0).getAsJsonObject();
				if (jsonObject.has(JSON_FIELD_ERROR)) {
					result = jsonObject.get(JSON_FIELD_ERROR).getAsString();
				}
			}
		}
		return result;
	}

	private String getAuthKey() {
		String authKey = "";
		try {
			authKey = PropertiesFileHelper.getProperty("auth_key");
		} catch (final PropertyException e) {
			exceptionHandler.handleError(e);
		}
		return authKey;
	}
}