package com.askit;

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

import com.askit.database.ConnectionFactory;
import com.askit.database.Constants;
import com.askit.exception.DriverNotFoundException;
import com.askit.queries.DatabaseQueryManager;
import com.askit.queries.QueryManager;
import com.askit.queries.SQLFactory;

public class QueryManagerTest {

	private static QueryManager queryManager;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		queryManager = new DatabaseQueryManager();
		// TODO clearTestDatabase or create database
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		// TODO delete data
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPhoneNumeberHashCheck() throws SQLException, DriverNotFoundException {
		final Connection writerConnection = ConnectionFactory.getInstance().getWriterConnection();
		final String[] columns = new String[] { "phoneNumberHash", "username", "accessionDate" };
		final String firstPart = SQLFactory.buildInsertStatement(Constants.SCHEMA_NAME, Constants.TABLE_USERS, columns);
		final PreparedStatement statement = writerConnection.prepareStatement(firstPart + "?,?,? );");
		statement.setString(1, "ABCDEFG");
		statement.setString(2, "TestUser");
		statement.setDate(3, new Date(System.currentTimeMillis()));
		statement.executeQuery();

		assertThat("The phoneNumerHash wasn't found", queryManager.checkPhoneNumberHash("ABCDEFG") == true);
		assertThat("The phoneNumerhHash was found", queryManager.checkPhoneNumberHash("123456") == false);
	}
}
