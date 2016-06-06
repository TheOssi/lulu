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

import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.WrongHashException;
import com.askit.face.SessionManager;

public class SessionManagerTest {
	private static String[] passwordHashes = new String[] { "ABCDEFG", "BCDEFGH", "CDEFGHI", "DEFGHIJ" };
	private static String[] usernames = new String[] { "User1", "User2", "User3", "User4" };

	@BeforeClass
	public static void setUpBeforeClass() throws IOException, SQLException {
	}

	@AfterClass
	public static void tearDownAfterClass() throws SQLException {

	}

	@Before
	public void setUp() {
		SessionManager.getInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSessionManagerIsNotNull() {
		assertThat("SessionManager is null", SessionManager.getInstance() != null);
	}

	@Test
	public void testCreateSessionSimple() {
		try {
			SessionManager.getInstance().createSession(usernames[0], passwordHashes[0]);
		} catch (WrongHashException | DuplicateHashException | DatabaseLayerException e) {
			e.printStackTrace();
			fail("a exception appeared: " + e.getMessage());
		}
	}

	@Test
	public void testCreateSessionWithNullHash() {
		try {
			SessionManager.getInstance().createSession(null, null);
		} catch (WrongHashException | DuplicateHashException | DatabaseLayerException e) {
			e.printStackTrace();
			fail("a exception appeared: " + e.getMessage());
		}
	}

	@Test(expected = WrongHashException.class)
	public void testCreateSessionWithFalseHash() throws WrongHashException {
		try {
			SessionManager.getInstance().createSession("THISISNOTAUSER", "THISISNOTAHASH");
		} catch (DuplicateHashException | DatabaseLayerException e) {
			e.printStackTrace();
			fail("a exception appeared: " + e.getMessage());
		}
	}

}
