package com.askit.database;

public class SQLFactory {

	public static final String ASCENDING = "ASC";
	public static final String DESCENDING = "DESC";

	// ================================================================================
	// SELECT STATEMENTS
	// ================================================================================

	public static String buildSelectAllStatement(final String schema, final String table) {
		return buildSelectStatement(schema, table, "*", "", "", "");
	}

	public static String buildSelectAllStatementWithWhereCondition(final String schema, final String table, final String whereCondition) {
		return buildSelectStatement(schema, table, "*", "WHERE " + whereCondition, "", "");
	}

	public static String buildSelectAllStatementWithWhereConditionLimitClauselOrderByClausel(final String schema, final String table,
			final String whereCondition, final String orderBy, final String limit) {
		return buildSelectStatement(schema, table, "*", "WHERE " + whereCondition, orderBy, limit);
	}

	public static String buildSelectStatementWithWhereCondition(final String schema, final String table, final String[] columns,
			final String whereCondition) {
		final StringBuilder preStatement = new StringBuilder();
		preStatement.append(columns[0]);
		for (int i = 1; i < columns.length; i++) {
			preStatement.append(",");
			preStatement.append(columns[i]);
		}
		return buildSelectStatement(schema, table, preStatement.toString(), "WHERE " + whereCondition, "", "");
	}

	private static String buildSelectStatement(final String schema, final String table, final String selectColumnsStatement,
			final String whereStatement, final String orderbyStatement, final String limitStatement) {
		final StringBuilder statement = new StringBuilder();
		statement.append("SELECT ");
		statement.append(selectColumnsStatement);
		statement.append(" FROM ");
		statement.append(schema);
		statement.append(".");
		statement.append(table);
		statement.append(" ");
		statement.append(whereStatement);
		statement.append(" ");
		statement.append(orderbyStatement);
		statement.append(" ");
		statement.append(limitStatement);
		return statement.toString().trim() + ";";
	}

	// ================================================================================
	// INSERT STATEMENTS
	// ================================================================================

	public static String buildInsertAllStatement(final String schema, final String table, final int columnNumber) {
		final StringBuilder statement = new StringBuilder();
		statement.append("INSERT INTO ");
		statement.append(schema);
		statement.append(".");
		statement.append(table);
		statement.append(" VALUES ( ?");
		for (int i = 1; i < columnNumber; i++) {
			statement.append(",?");
		}
		statement.append(");");
		return statement.toString();
	}

	public static String buildInsertStatement(final String schema, final String table, final String[] columns) {
		final StringBuilder statement = new StringBuilder();
		statement.append("INSERT INTO ");
		statement.append(schema);
		statement.append(".");
		statement.append(table);
		statement.append("(");
		statement.append(columns[0]);
		for (int i = 1; i < columns.length; i++) {
			statement.append(",");
			statement.append(columns[i]);
		}
		statement.append(")");
		statement.append(" VALUES ( ");
		statement.append("?");
		for (int i = 1; i < columns.length; i++) {
			statement.append(",?");
		}
		statement.append(");");
		return statement.toString();
	}

	// ================================================================================
	// DELETE STATEMENTS
	// ================================================================================

	public static String buildDeleteStatement(final String schema, final String table, final String whereCondition) {
		final StringBuilder statement = new StringBuilder();
		statement.append("DELETE FROM ");
		statement.append(schema);
		statement.append(".");
		statement.append(table);
		statement.append(" WHERE ");
		statement.append(whereCondition);
		statement.append(";");
		return statement.toString();
	}

	// ================================================================================
	// UPDATE STATEMENTS
	// ================================================================================

	public static String buildUpdateStatement(final String schema, final String table, final String setClausel, final String whereCondition) {
		final StringBuilder statement = new StringBuilder();
		statement.append("UPDATE  ");
		statement.append(schema);
		statement.append(".");
		statement.append(table);
		statement.append("SET ");
		statement.append(setClausel);
		statement.append(" WHERE ");
		statement.append(whereCondition);
		statement.append(";");
		return statement.toString();
	}

	// ================================================================================
	// OTHER STATEMENTS
	// ================================================================================

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

	public static String buildOrderByStatement(final String orderByStatement) {
		final StringBuilder statement = new StringBuilder();
		statement.append("ORDER BY ");
		statement.append(orderByStatement);
		return statement.toString();
	}

	public static String buildLimitStatement(final int startIndex, final int quantity) {
		final StringBuilder statement = new StringBuilder();
		statement.append("LIMIT ");
		statement.append(String.valueOf(startIndex));
		statement.append(",");
		statement.append(String.valueOf(quantity));
		return statement.toString();
	}

}