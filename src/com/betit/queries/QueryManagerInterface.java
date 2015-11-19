package com.betit.queries;

import com.betit.entities.BQ;
import com.betit.entities.Group;

public interface QueryManagerInterface {

	public BQ getBQ();

	public Group getGroup();

	public String getString(String s);

}
