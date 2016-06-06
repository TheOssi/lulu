package com.askit.notification;

import java.util.HashMap;
import java.util.Map;

/**
 * This class handels and provides the registrationIDs for the GCM. The class is
 * implemented as a singelton.
 * 
 * @author Kai Müller
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class RegIDHandler {

	private static final RegIDHandler INSTANCE = new RegIDHandler();
	private final Map<Long, String> regIDs = new HashMap<Long, String>();

	private RegIDHandler() {
	}

	/**
	 * Because the class is implemented as a singelton, this method returns the
	 * only instance of the RegIDHandler
	 * 
	 * @return the instance of the RedIDHandler
	 */
	public static synchronized RegIDHandler getInstance() {
		return INSTANCE;
	}

	/**
	 * @param userID
	 *            the userID for which the regID should be returned
	 * @return the regID of the user; if no regID is avaible null is returned
	 */
	public String getRegIDFromUser(final Long userID) {
		return regIDs.get(userID);
	}

	/**
	 * Sets or update the regID of a user.
	 * 
	 * @param userID
	 *            the userID
	 * @param regID
	 *            the regID of this user
	 */
	public void setRegID(final Long userID, final String regID) {
		if (regIDs.containsKey(userID)) {
			regIDs.replace(userID, regID);
		} else {
			regIDs.put(userID, regID);
		}
	}
}