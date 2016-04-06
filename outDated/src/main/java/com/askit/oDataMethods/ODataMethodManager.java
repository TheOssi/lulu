package com.askit.oDataMethods;

import java.util.Map;

import com.askit.annotation.ODataLink;
import com.askit.entities.User;

public class ODataMethodManager {

	@ODataLink("/GET/USER")
	public User getUser(final Map<String, String[]> parameters) {
		return null;
	}

}
