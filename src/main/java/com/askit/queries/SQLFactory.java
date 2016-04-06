package com.askit.queries;

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
	 * This method builds a select statement with a given where condition
	 *
	 * @param schema
	 *            Schema name
	 * @param table
	 *            Table name
	 * @param columns
	 *            String array with to selecting columns
	 * @param whereCondition
	 *            the where condition
	 * @return a SELECT Stament without the WHERE conditions
	 */
	public static String buildSelectStatement(final String schema, final String table, final String[] columns, final String whereCondition) {
		final StringBuilder statement = new StringBuilder();
		statement.append("SELECT ");
		for (final String column : columns) {
			statement.append(column);
			statement.append(",");
		}
		statement.delete(statement.length() - 1, statement.length() + 1);
		statement.append(" FROM ");
		statement.append(schema);
		statement.append(".");
		statement.append(table);
		statement.append(" WHERE ");
		statement.append(whereCondition);
		statement.append(";");
		return statement.toString();
	}

	/**
	 * This method build a String of a INSERT Statement for all columns as a
	 * PreparedStatement
	 *
	 *
	 * @param schema
	 *            Schema name
	 * @param table
	 *            Table name
	 * @return a INSERT Stament as a PreparedStatement
	 */
	public static String buildSimpleInsertStatement(final String schema, final String table, final int columnNumber) {
		final StringBuilder statement = new StringBuilder();
		statement.append("INSERT INTO ");
		statement.append(schema);
		statement.append(".");
		statement.append(table);
		statement.append(" VALUES ( ");
		for (int i = 0; i < columnNumber; i++) {
			statement.append("?,");
		}
		statement.delete(statement.length() - 1, statement.length() + 1);
		statement.append(");");
		return statement.toString();
	}

	/**
	 * This method build a INSERT Statement for specified columns as a
	 * PreparedStatement
	 *
	 * @param schema
	 *            Schema name
	 * @param table
	 *            Table name
	 * @param columns
	 *            the columns in witch the values shoud be inserted
	 * @return a INSERT Stament as a PreparedStatement
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
		statement.delete(statement.length() - 1, statement.length() + 1);
		statement.append(")");
		statement.append(" VALUES ( ");
		for (final String column : columns) {
			statement.append("?,");
		}
		statement.delete(statement.length() - 1, statement.length() + 1);
		statement.append(");");
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

	public static String buildStatementForAreaSelect(final String begin, final String gorderByStatement, final int startIndex, final int quantity) {
		final StringBuilder statement = new StringBuilder();
		statement.append(begin);
		statement.append(" ORDER BY ");
		statement.append(gorderByStatement);
		statement.append(" LIMIT ");
		statement.append(String.valueOf(startIndex));
		statement.append(",");
		statement.append(String.valueOf(quantity));
		statement.append(";");
		return statement.toString();
	}

	public static String buildBeginOSimpleInstertStatement(final String schema, final String table) {
		final StringBuilder statement = new StringBuilder();
		statement.append("INSERT INTO ");
		statement.append(schema);
		statement.append(".");
		statement.append(table);
		statement.append(" VALUES ( ");
		return statement.toString();
	}

	public static String buildBeginOfInsertStatement(final String schema, final String table, final String[] columns) {
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
		statement.delete(statement.length() - 1, statement.length() + 1);
		statement.append(")");
		statement.append(" VALUES ( ");
		return statement.toString();
	}

	public static String buildBeginOfUpdateStatement(final String schema, final String table) {
		final StringBuilder statement = new StringBuilder();
		statement.append("UPDATE  ");
		statement.append(schema);
		statement.append(".");
		statement.append(table);
		statement.append("SET ");
		return statement.toString();
	}
}