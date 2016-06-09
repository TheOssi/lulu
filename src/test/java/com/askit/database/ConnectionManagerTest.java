package com.askit.database;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

public class ConnectionManagerTest {

	@Test
	public void testGetInstanceMethod() {
		try {
			assertThat("Instance is null", ConnectionManager.getInstance() != null);
			assertThat("Instance is not instance of ConnectionFactory", ConnectionManager.getInstance() instanceof ConnectionManager);
			ConnectionManager.getInstance().getDeleteConnection().setReadOnly(true);
			assertThat("Theres another instance", ConnectionManager.getInstance().getDeleteConnection().isReadOnly() == true);
			ConnectionManager.getInstance().getDeleteConnection().setReadOnly(false);
		} catch (final SQLException e) {
			fail("A exception occured: ");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetConnection() {
		try {
			assertThat("Connection is null", ConnectionManager.getInstance().getReaderConnection() != null);
			assertThat("Instance is not instance of Connection", ConnectionManager.getInstance().getReaderConnection() instanceof Connection);
			ConnectionManager.getInstance().getReaderConnection().close();
			assertThat("Connection is closed", ConnectionManager.getInstance().getReaderConnection().isClosed() == false);
		} catch (final SQLException e) {
			fail("A exception occured: ");
			e.printStackTrace();
		}
	}
}
