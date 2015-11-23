package com.betit.queries;

import java.sql.SQLException;

import com.betit.exception.DriverNotFoundException;

public interface QueryManager {

	public boolean checkPhoneNumberHash(String phoneNumberHash) throws SQLException, DriverNotFoundException;

}
