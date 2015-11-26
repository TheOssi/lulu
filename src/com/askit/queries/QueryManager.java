package com.askit.queries;

import java.sql.SQLException;

import com.askit.exception.DriverNotFoundException;

public interface QueryManager {

	public boolean checkUser(String username, String phoneNumberHash) throws SQLException, DriverNotFoundException;

}
