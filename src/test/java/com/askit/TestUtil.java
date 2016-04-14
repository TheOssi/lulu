package com.askit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.askit.database.ConnectionManager;
import com.askit.etc.Constants;
import com.askit.etc.Util;
import com.askit.exception.DriverNotFoundException;
import com.askit.queries.SQLFactory;

public class TestUtil {
	private static final String DIR = Util.getRunntimeDirectory().getParent() + File.separator + "SQL" + File.separator;

	public static void createUser(final String username, final String passwordHash) throws SQLException, DriverNotFoundException {
		final Connection writerConnection = ConnectionManager.getInstance().getWriterConnection();
		final String[] columns = new String[] { "passwordHash", "username", "accessionDate", "language" };
		final String firstPart = SQLFactory.buildInsertStatement(Constants.SCHEMA_NAME, Constants.TABLE_USERS, columns);
		final PreparedStatement statement = writerConnection.prepareStatement(firstPart + "?,?,? );");
		statement.setString(1, passwordHash);
		statement.setString(2, username);
		statement.setDate(3, new Date(System.currentTimeMillis()));
		statement.setString(4, "DE");
		statement.executeQuery();

	}

	public static void deleteAllUsers() {

	}

	public static void createDatabase() throws IOException, SQLException, DriverNotFoundException {
		final String username = JOptionPane.showInputDialog("Username:");
		final String password = JOptionPane.showInputDialog("Password:");
		final Connection connection = ConnectionManager.getInstance().buildOneTimeConnection(username, password);

		final File dir = new File(DIR);
		final File[] sqlFiles = dir.getAbsoluteFile().listFiles();

		for (final File file : sqlFiles) {
			final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			final StringBuilder contentOfFile = new StringBuilder();
			while (bufferedReader.ready()) {
				contentOfFile.append(bufferedReader.readLine());
			}
			bufferedReader.close();
			connection.createStatement().execute(contentOfFile.toString());
		}
	}

	public static void deleteDatabase() throws SQLException, DriverNotFoundException {
		final String username = JOptionPane.showInputDialog("Username:");
		final String password = JOptionPane.showInputDialog("Password:");
		final Connection connection = ConnectionManager.getInstance().buildOneTimeConnection(username, password);
		connection.createStatement().execute("DROP DATABASE " + Constants.SCHEMA_NAME);
	}
}
