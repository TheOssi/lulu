package com.betit.tests;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.betit.face.SessionManager;

public class CheckSessionManager {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// TODO insert Users
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// TODO delete Users
	}

	@Before
	public void setUp() throws Exception {
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

	}

	@Test
	public void testCreateSessionThreadSafety() {

	}

	@Test
	public void testCreateSessionWithFalseHash() {

	}

	@Test
	public void testTimeout() {

	}

}
