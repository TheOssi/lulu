package com.askit.database;

public class SQLStatementFactory {

	private final StringBuilder statement = new StringBuilder();

	private void append(final String s) {
		statement.append(" " + s);
	}

	public SQLStatementFactory select() {
		append("SELECT ");
		return this;
	}

	public SQLStatementFactory from(final String table) {
		append("FROM " + table);
		return this;
	}

	public SQLStatementFactory from(final String schema, final String table) {
		append("FROM " + schema + "." + table);
		return this;
	}

	public SQLStatementFactory where() {
		append("WHERE");
		return this;
	}

	public SQLStatementFactory limit(final int start) {
		statement.append("LIMIT " + String.valueOf(start));
		return this;
	}

	public SQLStatementFactory limit(final int start, final int end) {
		statement.append("LIMIT " + String.valueOf(start) + "," + String.valueOf(end));
		return this;
	}

	public SQLStatementFactory orderBy() {

		return this;
	}

	public SQLStatementFactory column() {
		return this;

	}

	public SQLStatementFactory isEqual(final String s) {
		return this;
	}

	public SQLStatementFactory isNotEqual(final String s) {
		return this;
	}

	public String buildSQL() {
		return statement.toString();
	}
}
