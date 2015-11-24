package com.betit;

import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.betit.database.ConnectionFactory;
import com.betit.database.Constants;
import com.betit.exception.DriverNotFoundException;
import com.betit.face.SessionManager;
import com.betit.queries.SQLFactory;

public class CheckSessionManager {
	private static String[] hashes = new String[] { "ABCDEFG", "BCDEFGH", "CDEFGHI", "DEFGHIJ" };

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		for (final String hash : hashes) {
			registerUserInDatabase(hash);
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// TODO delete Users
	}

	@Before
	public void setUp() throws Exception {
		SessionManager.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		SessionManager.destroy();
	}

	@Test
	public void testSessionManagerIsNotNull() {
		assertThat("SessionManager is null", SessionManager.getInstance() != null);
	}

	@Test
	public void testCreateSessionSimple() {
		SessionManager.getInstance().createSession(hashes[0]);
		// TODO test an sich
	}

	@Test
	public void testCreateSessionThreadSafety() {

	}

	@Test
	public void testCreateSessionWithNullHash() {
		SessionManager.getInstance().createSession(null);
	}

	@Test
	public void testCreateSessionWithFalseHash() {
		SessionManager.getInstance().createSession("THISISNOTAHASH");
	}

	@Test
	public void testTimeout() {

	}

	private static void registerUserInDatabase(final String hash) throws SQLException, DriverNotFoundException {
		final Connection writerConnection = ConnectionFactory.getInstance().getWriterConnection();
		final String[] columns = new String[] { "phoneNumberHash", "username", "accessionDate" };
		final String firstPart = SQLFactory.buildInsertStatement(Constants.TEST_SCHEMA_NAME, Constants.TABLE_USERS, columns);
		final PreparedStatement statement = writerConnection.prepareStatement(firstPart + "?,?,? );");
		statement.setString(1, hash);
		statement.setString(2, "TestUser");
		statement.setDate(3, new Date(System.currentTimeMillis()));
		statement.executeQuery();
	}

}
