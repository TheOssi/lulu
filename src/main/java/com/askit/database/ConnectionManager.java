package com.askit.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import com.askit.database.sqlHelper.Constants;

/**
 *
 * This manager provides all connections to the database. For that
 * functionallity a extern libary for pooling the connections is used. <br/>
 * Three types with different permissions are avaible:
 * <ul>
 * <li>Reader connection: This connection have only read permissions to all
 * tables necessary of the app</li>
 *
 * <li>Writer connection: This connection have read and writer permissions to
 * all tables necessary of the app</li>
 *
 * <li>Deleter connection: This connection have only delete permissions to some
 * tables necessary of the app</li>
 * </ul>
 *
 * Also some attributes like timeouts, driver, ip, pool size are set to the
 * connections.
 *
 * The pooling has a huge impact to the performance of the system. The pool
 * provides lazy connections. If somebody call a getConnection method, a
 * connection from the pool return. The caller have the duty to close the
 * connection after using it. Thus return the connection to the pool. If no
 * connection is avaible in the pool, the caller must wait.
 *
 * @author Kai Müller
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class ConnectionManager {
	private static final String VALIDATION_QUERY = "SELECT 1";
	private static final int ABANDONED_TIMEOUT = 60;
	private static final int VALIDATION_INTERVALL = 30000;
	private static final int MAX_IDLE = 20;
	private static final int MIN_IDLE = 5;
	private static final int MAX_PARALLEL_CONNECTIONS = 100;
	private static final int INITIAL_IDLE_CONNECTIONS = 5;
	private static final int MAX_WAIT_TIME = 30000;
	private static final String MARIA_DB_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String IP_OF_DATABASE = "localhost";
	private static final String JDBC_PROTOCOLL = "jdbc:mysql";
	private static final String URL = JDBC_PROTOCOLL + "://" + IP_OF_DATABASE + "/" + Constants.SCHEMA_NAME;
	private static final ConnectionManager INSTANCE = new ConnectionManager();

	private DataSource readerDataSource;
	private DataSource writerDataSource;
	private DataSource deleterDataSource;

	private ConnectionManager() {
		setDeleterDataSource();
		setReaderDataSource();
		setWriterDataSource();
	}

	private void setReaderDataSource() {
		final PoolProperties properties = new PoolProperties();
		setDefaultSettings(properties);
		properties.setUsername(DatabaseUser.READ_USER.getUsername());
		System.out.println(DatabaseUser.READ_USER.getPassword());
		properties.setPassword(DatabaseUser.READ_USER.getPassword());
		properties.setDefaultReadOnly(true);
		readerDataSource = new DataSource();
		readerDataSource.setPoolProperties(properties);
	}

	private void setWriterDataSource() {
		final PoolProperties properties = new PoolProperties();
		setDefaultSettings(properties);
		properties.setUsername(DatabaseUser.WRITE_USER.getUsername());
		properties.setPassword(DatabaseUser.WRITE_USER.getPassword());
		properties.setDefaultReadOnly(false);
		writerDataSource = new DataSource();
		writerDataSource.setPoolProperties(properties);
	}

	private void setDeleterDataSource() {
		final PoolProperties properties = new PoolProperties();
		setDefaultSettings(properties);
		properties.setUsername(DatabaseUser.DELETE_USER.getUsername());
		properties.setPassword(DatabaseUser.DELETE_USER.getPassword());
		properties.setDefaultAutoCommit(true);
		deleterDataSource = new DataSource();
		deleterDataSource.setPoolProperties(properties);
	}

	private void setDefaultSettings(final PoolProperties properties) {
		properties.setDriverClassName(MARIA_DB_DRIVER);
		properties.setUrl(URL);
		properties.setDefaultAutoCommit(true);
		properties.setMinIdle(MIN_IDLE);
		properties.setMaxIdle(MAX_IDLE);
		properties.setMaxActive(MAX_PARALLEL_CONNECTIONS);
		properties.setInitialSize(INITIAL_IDLE_CONNECTIONS);
		properties.setMaxWait(MAX_WAIT_TIME);
		properties.setTestOnReturn(false);
		properties.setTestWhileIdle(true);
		properties.setTestOnBorrow(true);
		properties.setValidationQuery(VALIDATION_QUERY);
		properties.setValidationInterval(VALIDATION_INTERVALL);
		properties.setRemoveAbandonedTimeout(ABANDONED_TIMEOUT);
		properties.setRemoveAbandoned(true);
		properties.setLogAbandoned(true);
	}

	/**
	 * Because only one instance of a manager should exist and handle all
	 * connections to the database, this class is implemented as a singelton. So
	 * this method retuns the only instance of this class.
	 *
	 * @return the only instance of the class ConnectionManager
	 */
	public static synchronized ConnectionManager getInstance() {
		return INSTANCE;
	}

	/**
	 *
	 * @return a connection to the database with the "reader" user
	 * @throws SQLException
	 *             if a database access error occurs
	 */
	public synchronized Connection getReaderConnection() throws SQLException {
		return readerDataSource.getConnection();
	}

	/**
	 *
	 * @return a connection to the database with the "writer" user
	 * @throws SQLException
	 *             if a database access error occurs
	 */
	public synchronized Connection getWriterConnection() throws SQLException {
		return writerDataSource.getConnection();
	}

	/**
	 *
	 * @return a connection to the database with the "delete" user
	 * @throws SQLException
	 *             if a database access error occurs
	 */
	public synchronized Connection getDeleteConnection() throws SQLException {
		return deleterDataSource.getConnection();
	}
}