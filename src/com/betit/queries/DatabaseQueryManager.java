package com.betit.queries;

import java.sql.Connection;
import java.sql.SQLException;

import com.betit.database.ConnectionFactory;
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

	@Override
	public Winner[] getWinners(final long bqID) throws SQLException {
		final Connection connection = CONNECTION_FACTORY.getReaderConnection();

		return null;
	}
}
