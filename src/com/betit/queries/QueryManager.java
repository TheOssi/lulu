package com.betit.queries;

import java.sql.SQLException;

import com.betit.entities.BQ;
import com.betit.entities.Group;
import com.betit.entities.Winner;

public interface QueryManager {

	public BQ getBQ();

	public Group getGroup();

	public String getString(String s);

	public Winner[] getWinners(long bqID) throws SQLException;

}
