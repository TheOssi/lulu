package com.askit.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.askit.exception.ExceptionHandler;

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
		InputStream inputStream = null;
		try {
			// TODO how to root
			final File propertiesFile = new File("./config/config.properties");
			if (propertiesFile.exists() == false) {
				throw new IOException("Properties file (" + propertiesFile.getAbsolutePath() + ") does not exist");
			}
			final Properties properties = new Properties();
			inputStream = new FileInputStream(propertiesFile);
			properties.load(inputStream);

			setPassword(properties, READ_USER);
			setPassword(properties, WRITE_USER);
			setPassword(properties, DELETE_USER);
		} catch (final IOException e) {
			ExceptionHandler.getInstance().handleError(e);
		} finally {
			closeSilentlyInputStream(inputStream);
		}
	}

	private static void closeSilentlyInputStream(final InputStream inputStream) {
		try {
			inputStream.close();
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