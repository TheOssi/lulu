package com.askit.database;

import javax.xml.bind.PropertyException;

import com.askit.util.PropertiesFileHelper;

/**
 * This enum stores the three database user with username and passwords. Because
 * of securtity reasons the passwords are stored in a config file and must read
 * before the first use of any user.
 * 
 * @author Kai Müller
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public enum DatabaseUser {

	READ_USER("appReader"),
	WRITE_USER("appWriter"),
	DELETE_USER("appDeleter");

	static {
		loadAllPasswordsFromFile();
	}

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

	/**
	 * get the password of a user
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * get the username of a user
	 * 
	 * @return the usernmae
	 */
	public String getUsername() {
		return username;
	}

	private void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * All passwords are stored in a config file. This reads all passwords and
	 * maps them to the corresponding user.
	 */
	private static void loadAllPasswordsFromFile() {
		setPassword(READ_USER);
		setPassword(WRITE_USER);
		setPassword(DELETE_USER);
	}

	private static void setPassword(final DatabaseUser user) {
		try {
			final String password = PropertiesFileHelper.getProperty(user.getUsername());
			user.setPassword(password);
		} catch (final PropertyException e) {
			throw new NullPointerException("Password of user " + user.getUsername() + " not set; not found");
		}
	}
}