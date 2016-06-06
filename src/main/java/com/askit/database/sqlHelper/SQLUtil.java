package com.askit.database.sqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.askit.exception.ExceptionHandler;

/**
 * All sql util methods are stored static in this class.
 * 
 * @author Kai Müller
 * @since 1.0.0.0
 * @version 1.0.0.0
 *
 */
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
	 * This close a SQL prepared statement, a result set and a connection
	 * silently, so no exception is thrown. If a exceptions occurs it's handle
	 * by the exceptionhandler. The sql things are checked for null and if not,
	 * they tried to closed.
	 * 
	 * @param preparedStatement
	 *            the prepared statement and within the connection to close
	 * @param resultSet
	 *            the resultset to close
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
			ExceptionHandler.getInstance().handleError(exception);
		}
	}
}