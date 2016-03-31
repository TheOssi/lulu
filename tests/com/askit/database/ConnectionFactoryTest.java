package com.askit.database;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.askit.database.ConnectionFactory;
import com.askit.exception.DriverNotFoundException;

public class ConnectionFactoryTest {

	@Test
	public void testGetInstanceMethod() {
		try {
			assertThat("Instance is null", ConnectionFactory.getInstance() != null);
			assertThat("Instance is not instance of ConnectionFactory", ConnectionFactory.getInstance() instanceof ConnectionFactory);
			ConnectionFactory.getInstance().getDeleteConnection().setReadOnly(true);
			assertThat("Theres another instance", ConnectionFactory.getInstance().getDeleteConnection().isReadOnly() == true);
			ConnectionFactory.getInstance().getDeleteConnection().setReadOnly(false);
		} catch (final DriverNotFoundException | SQLException e) {
			fail("A exception occured: ");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetConnection() {
		try {
			assertThat("Connection is null", ConnectionFactory.getInstance().getReaderConnection() != null);
			assertThat("Instance is not instance of Connection", ConnectionFactory.getInstance().getReaderConnection() instanceof Connection);
			ConnectionFactory.getInstance().getReaderConnection().close();
			assertThat("Connection is closed", ConnectionFactory.getInstance().getReaderConnection().isClosed() == false);
		} catch (SQLException | DriverNotFoundException e) {
			fail("A exception occured: ");
			e.printStackTrace();
		}
	}
}
