package com.askit;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.askit.database.ConnectionFactory;
import com.askit.database.Constants;
import com.askit.exception.DriverNotFoundException;
import com.askit.queries.SQLFactory;

public class TestUtil {
	public static void createUser(String passwordHash) {

	}
	public static void deleteAllUsers(){
		
	}
//	private static void registerUserInDatabase(final String hash) throws SQLException, DriverNotFoundException {
//		final Connection writerConnection = ConnectionFactory.getInstance().getWriterConnection();
//		final String[] columns = new String[] { "phoneNumberHash", "username", "accessionDate" };
//		final String firstPart = SQLFactory.buildInsertStatement(Constants.SCHEMA_NAME, Constants.TABLE_USERS, columns);
//		final PreparedStatement statement = writerConnection.prepareStatement(firstPart + "?,?,? );");
//		statement.setString(1, hash);
//		statement.setString(2, "TestUser");
//		statement.setDate(3, new Date(System.currentTimeMillis()));
//		statement.executeQuery();
//	}
}
