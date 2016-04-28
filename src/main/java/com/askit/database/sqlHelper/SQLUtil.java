package com.askit.database.sqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtil {

	/**
	 * This method measures the size of a resultset. It cares that the current
	 * row pointer is after the call on the same row.
	 * 
	 * @param resultSet
	 *            the resultSet
	 * @return the size of the ResultSet
	 * @throws SQLException
	 */
	public static int getSizeOfResultSet(final ResultSet resultSet) throws SQLException {
		final int currentRow = resultSet.getRow();
		resultSet.last();
		final int size = resultSet.getRow();
		resultSet.absolute(currentRow);
		return size;
	}

	/**
	 * @param preparedStatement
	 * @param resultSet
	 */
	public static void closeSilentlySQL(final PreparedStatement preparedStatement, final ResultSet resultSet) {
		Connection connection = null;
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				connection = preparedStatement.getConnection();
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (final SQLException exception) {
			exception.printStackTrace();
		}
	}
}