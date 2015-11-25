package com.askit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.askit.database.ConnectionFactory;
import com.askit.database.Constants;
import com.askit.exception.DriverNotFoundException;
import com.askit.exception.WrongHashException;
import com.askit.face.SessionManager;
import com.askit.queries.SQLFactory;

public class SessionManagerTest {
	private static String[] hashes = new String[] { "ABCDEFG", "BCDEFGH", "CDEFGHI", "DEFGHIJ" };

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// TODO setting up database
		for (final String hash : hashes) {
			TestUtil.createUser(hash);
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		TestUtil.deleteAllUsers();
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
		try {
			SessionManager.getInstance().createSession(hashes[0]);
		} catch (SQLException | DriverNotFoundException | WrongHashException e) {
			fail("a exception appeared: ");
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateSessionThreadSafety() {
		// TODO sowohl input als auch outout
	}

	@Test
	public void testCreateSessionWithNullHash() {
		try {
			SessionManager.getInstance().createSession(null);
		} catch (SQLException | DriverNotFoundException | WrongHashException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(expected = WrongHashException.class)
	public void testCreateSessionWithFalseHash() throws WrongHashException {
		try {
			SessionManager.getInstance().createSession("THISISNOTAHASH");
		} catch (SQLException | DriverNotFoundException e) {
			fail("a exception appeared: ");
			e.printStackTrace();
		}
	}

	@Test
	public void testTimeout() {
		//nach 10 min neu anmelden
	}

	

}
