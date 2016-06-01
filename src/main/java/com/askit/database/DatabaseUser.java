package com.askit.database;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import com.askit.exception.ExceptionHandler;
import com.askit.face.FileSupporter;

public enum DatabaseUser {

	READ_USER("appReader"),
	WRITE_USER("appWriter"),
	DELETE_USER("appDeleter");

	private String password;
	private String username;

	private DatabaseUser(final String username, final String password) {
		this.username = username;
		this.password = password;
	}

	private DatabaseUser(final String username) {
		this.username = username;
		password = "";
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	private void setPassword(final String password) {
		this.password = password;
	}

	public static void loadAllPasswordsFromFile() {
		try {
			final String propertiesFileContent = FileSupporter.getFileContent("./config", "/config.properties");
			final Properties properties = new Properties();
			properties.load(new StringReader(propertiesFileContent));

			setPassword(properties, READ_USER);
			setPassword(properties, WRITE_USER);
			setPassword(properties, DELETE_USER);
		} catch (final IOException e) {
			ExceptionHandler.getInstance().handleError(e);
		}
	}

	private static void setPassword(final Properties properties, final DatabaseUser user) {
		final String password = properties.getProperty(user.getUsername());
		if (password == null || password.trim().length() == 0) {
			throw new NullPointerException("Password of user " + user.getUsername() + " not set");
		}
		user.setPassword(password);
	}
}