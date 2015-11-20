package com.betit.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.betit.database.ConnectionFactory;
import com.betit.database.Constants;

public class DatabaseQueryManager implements QueryManager {

	private final static ConnectionFactory CONNECTION_FACTORY = ConnectionFactory.getInstance();

	public void getBQ(final long id) throws SQLException {
		final Connection connection = CONNECTION_FACTORY.getReaderConnection();
		final PreparedStatement statement = connection.prepareStatement(SQLFactory.buildSimpleSelectStatement(Constants.SCHEMA_NAME,
				Constants.TABLE_BQ));
		statement.executeQuery();

	}

	public void getBQMetadata(final long id) throws SQLException {
		final Connection connection = CONNECTION_FACTORY.getReaderConnection();
		final String[] columns = new String[] { "bqID", "titel", "question" };
		final String firstPart = SQLFactory.buildSelectStatement(Constants.SCHEMA_NAME, Constants.TABLE_BQ, columns);
		final PreparedStatement statement = connection.prepareStatement(firstPart + "bqID = ?;");
		statement.setLong(0, id);
		statement.executeQuery();
	}

}
