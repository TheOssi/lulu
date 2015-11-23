package com.betit.tests;

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
import com.betit.queries.SQLFactory;

public class CheckSQL {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPhoneNumeberHashCheck() throws SQLException {
		final Connection connection = ConnectionFactory.getInstance().getWriterConnection();
		final String[] columns = new String[] { "phoneNumberHash", "username", "accessionDate" };
		final String firstPart = SQLFactory.buildInsertStatement(Constants.SCHEMA_NAME, Constants.TABLE_USERS, columns);
		final PreparedStatement statement = connection.prepareStatement(firstPart + "?,?,?);");
		statement.setString(1, "ABCDEFG");
		statement.setString(2, "TestUser");
		statement.setDate(3, new Date(System.currentTimeMillis()));
	}
}
