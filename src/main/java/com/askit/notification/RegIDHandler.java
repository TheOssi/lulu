package com.askit.notification;

import java.util.HashMap;
import java.util.Map;

public class RegIDHandler {

	private static final RegIDHandler INSTANCE = new RegIDHandler();

	private final Map<Long, String> regIDs = new HashMap<Long, String>();

	private RegIDHandler() {
	}

	public static synchronized RegIDHandler getInstance() {
		return INSTANCE;
	}

	public String getRegIDFromUser(final Long userID) {
		return regIDs.get(userID);
	}

	public void setRegID(final Long userID, final String regID) {
		if (regIDs.containsKey(userID)) {
			regIDs.replace(userID, regID);
		} else {
			regIDs.put(userID, regID);
		}
	}
}