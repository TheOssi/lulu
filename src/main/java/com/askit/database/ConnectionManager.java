package com.askit.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
//TODO drivernotfoundexception
import com.askit.etc.Constants;

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
		readerDataSource.setDriverClassName(MARIA_DB_DRIVER);
		readerDataSource.setUsername(DatabaseUser.READ_USER.getUsername());
		readerDataSource.setPassword(DatabaseUser.READ_USER.getPassword());
		readerDataSource.setUrl(URL);
		readerDataSource.setDefaultAutoCommit(true);
		readerDataSource.setDefaultReadOnly(true);

		// TODO
		readerDataSource.setMinIdle(20);
		readerDataSource.setMaxIdle(25);
		readerDataSource.setMaxTotal(-1);
		readerDataSource.setMaxOpenPreparedStatements(180);
	}

	private void setWriterDataSource() {
		writerDataSource = new BasicDataSource();
		writerDataSource.setDriverClassName(MARIA_DB_DRIVER);
		writerDataSource.setUsername(DatabaseUser.READ_USER.getUsername());
		writerDataSource.setPassword(DatabaseUser.READ_USER.getPassword());
		writerDataSource.setUrl(URL);
		readerDataSource.setDefaultAutoCommit(true);
		readerDataSource.setDefaultReadOnly(false);

		// TODO
		writerDataSource.setMinIdle(20);
		writerDataSource.setMaxIdle(25);
		writerDataSource.setMaxTotal(-1);
		writerDataSource.setMaxOpenPreparedStatements(180);
	}

	private void setDeleterDataSource() {
		deleterDataSource = new BasicDataSource();
		deleterDataSource.setDriverClassName(MARIA_DB_DRIVER);
		deleterDataSource.setUsername(DatabaseUser.READ_USER.getUsername());
		deleterDataSource.setPassword(DatabaseUser.READ_USER.getPassword());
		deleterDataSource.setUrl(URL);
		readerDataSource.setDefaultAutoCommit(true);
		readerDataSource.setDefaultReadOnly(false);

		// TODO
		deleterDataSource.setMinIdle(20);
		deleterDataSource.setMaxIdle(25);
		deleterDataSource.setMaxTotal(-1);
		deleterDataSource.setMaxOpenPreparedStatements(180);
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