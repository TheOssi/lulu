package com.askit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.askit.database.Constants;
import com.askit.queries.SQLFactory;

public class SQLFactoryTest {

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
	public void test() {
		final String statement = SQLFactory.buildStatementForAreaSelect(Constants.SCHEMA_NAME, Constants.TABLE_PUBLIC_QUESTIONS, "createDate DESC",
				"createDate ASC", 100, 10);
	}

}
