package com.betit.queries;

public class SQLFactory {

	public static String buildSimpleSelectStatement(final String schema, final String table) {
		return "SELECT * FROM " + schema + "." + table;
	}

	/**
	 * This method build the start of a SELECT Statement with the keyword
	 * "WHERE", but without a condition
	 * 
	 * @param schema
	 *            Schema name
	 * @param table
	 *            Table name
	 * @param colums
	 *            String array with to selecting columns
	 * @return a SELECT Stament without the WHERE conditions
	 */
	public static String buildSelectStatement(final String schema, final String table, final String[] colums) {
		final StringBuilder statement = new StringBuilder();
		statement.append("SELECT ");
		for (final String column : colums) {
			statement.append(column);
			statement.append(",");
		}
		statement.delete(statement.length() - 2, statement.length() - 1);
		statement.append(" FROM ");
		statement.append(schema);
		statement.append(".");
		statement.append(table);
		statement.append(" WHERE ");
		return statement.toString();
	}
}