package com.betit.queries;

import java.sql.SQLException;

public interface QueryManager {

	public boolean checkPhoneNumberHash(String phoneNumberHash) throws SQLException;

}
