package com.askit.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.askit.database.ConnectionFactory;
import com.askit.database.Constants;
import com.askit.exception.DriverNotFoundException;

public class DatabaseQueryManager implements QueryManager {

	@Override
	public boolean checkPhoneNumberHash(final String phoneNumberHash) throws SQLException, DriverNotFoundException {
		final Connection connection = ConnectionFactory.getInstance().getReaderConnection();
		final String[] columns = new String[] { "phoneNumberHash" };
		final String firstPart = SQLFactory.buildSelectStatement(Constants.SCHEMA_NAME, Constants.TABLE_USERS, columns);
		final PreparedStatement statement = connection.prepareStatement(firstPart + "phoneNumberHash = ?;");
		statement.setString(1, phoneNumberHash);
		final ResultSet result = statement.executeQuery();
		return result.next();
	}
}