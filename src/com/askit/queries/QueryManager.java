package com.askit.queries;

import java.sql.SQLException;

import com.askit.exception.DriverNotFoundException;

public interface QueryManager {

	public boolean checkPhoneNumberHash(String phoneNumberHash) throws SQLException, DriverNotFoundException;

}
