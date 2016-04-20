package com.askit.etc;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.askit.database.ConnectionManager;

public class Util {

	/**
	 * This method will help to select the current execution path
	 *
	 * @return the current runntime directory
	 */
	public static File getRunntimeDirectory() {
		File jarFile = null;
		File parentPathOfJar = null;
		try {
			jarFile = new File(ConnectionManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString());
			parentPathOfJar = jarFile.getParentFile();
		} catch (final URISyntaxException e) {
			parentPathOfJar = new File(File.separator + ".");
		}
		return parentPathOfJar;
	}

	/**
	 *
	 * @param exception
	 * @return the exception text
	 */
	public static String getExceptionText(final Exception exception) {
		final StringWriter stringWriter = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(stringWriter, true);
		printWriter.flush();
		exception.printStackTrace(printWriter);
		return stringWriter.getBuffer().toString();
	}

	public static <T> T[] concatenateTwoArrays(final T[] arrayOne, final T[] arrayTwo) {
		final int arrayOneLenght = arrayOne.length;
		final int arrayTwoLenght = arrayTwo.length;
		@SuppressWarnings("unchecked")
		final T[] finalArray = (T[]) Array.newInstance(arrayOne.getClass().getComponentType(), arrayOneLenght + arrayTwoLenght);
		System.arraycopy(arrayOne, 0, finalArray, 0, arrayOneLenght);
		System.arraycopy(arrayTwo, 0, finalArray, arrayOneLenght, arrayTwoLenght);
		return finalArray;
	}

	public static int getSizeOfResultSet(final ResultSet resultSet) throws SQLException {
		final int currentRow = resultSet.getRow();
		resultSet.last();
		final int size = resultSet.getRow();
		resultSet.absolute(currentRow);
		return size;
	}

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
			// TODO
		}
	}

}