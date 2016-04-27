package com.askit.database;

public class AbstractQuestion {

	private final Long id, time;
	private final String tableName;

	public AbstractQuestion(final Long id, final Long time, final String tableName) {
		this.id = id;
		this.time = time;
		this.tableName = tableName;
	}

	public Long getId() {
		return id;
	}

	public Long getTime() {
		return time;
	}

	public String getTableName() {
		return tableName;
	}
}