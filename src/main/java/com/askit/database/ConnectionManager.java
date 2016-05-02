package com.askit.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

import com.askit.database.sqlHelper.Constants;

//TODO drivernotfoundexception
//TODO set min und max idle; puffer size

public class ConnectionManager {
	private static final String MARIA_DB_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String IP_OF_DATABASE = "localhost";
	private static final String JDBC_PROTOCOLL = "jdbc:mysql";
	private static final String URL = JDBC_PROTOCOLL + "://" + IP_OF_DATABASE + "/" + Constants.SCHEMA_NAME;
	private static final ConnectionManager INSTANCE = new ConnectionManager();

	private BasicDataSource readerDataSource;
	private BasicDataSource writerDataSource;
	private BasicDataSource deleterDataSource;

	private ConnectionManager() {
		setDeleterDataSource();
		setReaderDataSource();
		setWriterDataSource();
	}

	private void setReaderDataSource() {
		readerDataSource = new BasicDataSource();
		setDefaultSettings(readerDataSource);
		readerDataSource.setUsername(DatabaseUser.READ_USER.getUsername());
		readerDataSource.setPassword(DatabaseUser.READ_USER.getPassword());
		readerDataSource.setDefaultReadOnly(true);
	}

	private void setWriterDataSource() {
		writerDataSource = new BasicDataSource();
		setDefaultSettings(writerDataSource);
		writerDataSource.setUsername(DatabaseUser.WRITE_USER.getUsername());
		writerDataSource.setPassword(DatabaseUser.WRITE_USER.getPassword());
		writerDataSource.setDefaultReadOnly(false);
	}

	private void setDeleterDataSource() {
		deleterDataSource = new BasicDataSource();
		setDefaultSettings(deleterDataSource);
		deleterDataSource.setUsername(DatabaseUser.DELETE_USER.getUsername());
		deleterDataSource.setPassword(DatabaseUser.DELETE_USER.getPassword());
		deleterDataSource.setDefaultAutoCommit(true);
	}

	private void setDefaultSettings(final BasicDataSource basicDataSource) {
		basicDataSource.setDriverClassName(MARIA_DB_DRIVER);
		basicDataSource.setUrl(URL);
		basicDataSource.setDefaultAutoCommit(true);
		basicDataSource.setMinIdle(20);
		basicDataSource.setMaxIdle(25);
		basicDataSource.setMaxTotal(-1);
		basicDataSource.setInitialSize(0);
		basicDataSource.setMaxOpenPreparedStatements(180);
	}

	public static synchronized ConnectionManager getInstance() {
		return INSTANCE;
	}

	/**
	 *
	 * @return a connection to the database with the "reader" user
	 * @throws SQLException
	 */
	public synchronized Connection getReaderConnection() throws SQLException {
		return readerDataSource.getConnection();
	}

	/**
	 *
	 * @return a connection to the database with the "writer" user
	 * @throws SQLException
	 */
	public synchronized Connection getWriterConnection() throws SQLException {
		return writerDataSource.getConnection();
	}

	/**
	 *
	 * @return a connection to the database with the "delete" user
	 * @throws SQLException
	 */
	public synchronized Connection getDeleteConnection() throws SQLException {
		return deleterDataSource.getConnection();
	}
}