package com.askit.database;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class DatabaseUserTest {

	@Test
	public void test() {
		DatabaseUser.loadAllPasswordsFromFile();
		for (final DatabaseUser databaseUser : DatabaseUser.values()) {
			assertThat("Password of " + databaseUser.getUsername() + " is null", databaseUser.getPassword() != null);
			assertThat("Password of " + databaseUser.getUsername() + " has length 0", databaseUser.getPassword().length() != 0);
		}
	}
}