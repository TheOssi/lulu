package com.askit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.askit.exception.DriverNotFoundException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.WrongHashException;
import com.askit.face.SessionManager;

public class SessionManagerTest {
	private static String[] passwordHashes = new String[] { "ABCDEFG", "BCDEFGH", "CDEFGHI", "DEFGHIJ" };
	private static String[] usernames = new String[] { "User1", "User2", "User3", "User4" };

	@BeforeClass
	public static void setUpBeforeClass() throws IOException, SQLException, DriverNotFoundException {
		for (int i = 0; i < usernames.length; i++) {
			TestUtil.createDatabase();
			TestUtil.createUser(usernames[i], passwordHashes[i]);
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws SQLException, DriverNotFoundException {
		TestUtil.deleteDatabase();
	}

	@Before
	public void setUp() {
		SessionManager.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		SessionManager.getInstance().deleteAllSessions();
	}

	@Test
	public void testSessionManagerIsNotNull() {
		assertThat("SessionManager is null", SessionManager.getInstance() != null);
	}

	@Test
	public void testCreateSessionSimple() {
		try {
			SessionManager.getInstance().createSession(usernames[0], passwordHashes[0]);
		} catch (SQLException | DriverNotFoundException | WrongHashException | DuplicateHashException e) {
			e.printStackTrace();
			fail("a exception appeared: " + e.getMessage());
		}
	}

	@Test
	public void testCreateSessionThreadSafety() {
		// TODO sowohl input als auch outout
	}

	@Test
	public void testCreateSessionWithNullHash() {
		try {
			SessionManager.getInstance().createSession(null, null);
		} catch (SQLException | DriverNotFoundException | WrongHashException | DuplicateHashException e) {
			e.printStackTrace();
			fail("a exception appeared: " + e.getMessage());
		}
	}

	@Test(expected = WrongHashException.class)
	public void testCreateSessionWithFalseHash() throws WrongHashException {
		try {
			SessionManager.getInstance().createSession("THISISNOTAUSER", "THISISNOTAHASH");
		} catch (SQLException | DriverNotFoundException | DuplicateHashException e) {
			e.printStackTrace();
			fail("a exception appeared: " + e.getMessage());
		}
	}

	@Test
	public void testTimeout() {
		// nach 10 min neu anmelden
	}

}
