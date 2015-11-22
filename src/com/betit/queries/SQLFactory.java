package com.betit.queries;

public class SQLFactory {

	public static String buildSimpleSelectStatement(final String schema, final String table) {
		final StringBuilder statement = new StringBuilder();
		statement.append("SELECT * FROM ");
		statement.append(schema);
		statement.append(".");
		statement.append(table);
		statement.append(";");
		return statement.toString();
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

	/**
	 * This method build the start of a INSERT Statement for all columns with
	 * the keyword "VALUES (", but without the values
	 * 
	 * @param schema
	 *            Schema name
	 * @param table
	 *            Table name
	 * @return a INSERT Stament without the values
	 */
	public static String buildSimpleInsertStatement(final String schema, final String table) {
		final StringBuilder statement = new StringBuilder();
		statement.append("INSERT INTO ");
		statement.append(schema);
		statement.append(".");
		statement.append(table);
		statement.append(" VALUES ( ");
		return statement.toString();
	}

	/**
	 * This method build the start of a INSERT Statement for specified columns
	 * with the keyword "VALUES (", but without the values
	 * 
	 * @param schema
	 *            Schema name
	 * @param table
	 *            Table name
	 * @param columns
	 *            the columns in witch the values shoud be inserted
	 * @return a INSERT Stament without the values
	 */
	public static String buildInsertStatement(final String schema, final String table, final String[] columns) {
		final StringBuilder statement = new StringBuilder();
		statement.append("INSERT INTO ");
		statement.append(schema);
		statement.append(".");
		statement.append(table);
		statement.append("(");
		for (final String column : columns) {
			statement.append(column);
			statement.append(",");
		}
		statement.delete(statement.length() - 2, statement.length() - 1);
		statement.append(")");
		statement.append(" VALUES ( ");
		return statement.toString();
	}

	/**
	 * This method build the start of a DELETE Statement with the keyword
	 * "WHERE", but without a condition
	 * 
	 * @param schema
	 *            Schema name
	 * @param table
	 *            Table name
	 * @return a DELETE Stament without the WHERE conditions
	 */
	public static String buildDeleteStatement(final String schema, final String table) {
		final StringBuilder statement = new StringBuilder();
		statement.append("DELETE FROM ");
		statement.append(schema);
		statement.append(".");
		statement.append(table);
		statement.append(" WHERE ");
		return statement.toString();
	}
}