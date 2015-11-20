package com.betit.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.betit.database.ConnectionFactory;
import com.betit.database.Constants;
import com.betit.entities.BQ;
import com.betit.entities.Group;
import com.betit.entities.Winner;

public class DatabaseQueryManager implements QueryManager {

	private final static ConnectionFactory CONNECTION_FACTORY = ConnectionFactory.getInstance();

	@Override
	public Group getGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BQ getBQ() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getString(final String s) {
		// TODO Auto-generated method stub
		return s;
	}

	public void getBQ(final long id) throws SQLException {
		final Connection connection = CONNECTION_FACTORY.getReaderConnection();
		final PreparedStatement statement = connection.prepareStatement(SQLFactory.buildSimpleSelectStatement(Constants.SCHEMA_NAME,
				Constants.TABLE_BQ));
		statement.execute();

	}

	public void getBQMetadata(final long id) throws SQLException {
		final Connection connection = CONNECTION_FACTORY.getReaderConnection();
		final String[] columns = new String[] { "bqID", "titel", "question" };
		final String firstPart = SQLFactory.buildSelectStatement(Constants.SCHEMA_NAME, Constants.TABLE_BQ, columns);
		final PreparedStatement statement = connection.prepareStatement(firstPart + "bqID = ?;");
		statement.setLong(0, id);
		statement.execute();
	}

	@Override
	public Winner[] getWinners(final long bqID) throws SQLException {
		final Connection connection = CONNECTION_FACTORY.getReaderConnection();

		return null;
	}
}
