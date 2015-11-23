package com.betit.face;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;

public class SessionManager implements Runnable {

	private static SessionManager instance;
	private HashMap<String, Long> sessionMap;

	private SessionManager() {
		final Thread t1 = new Thread(new SessionManager());
		t1.start();
	}

	public static synchronized SessionManager getInstance() {
		if (SessionManager.instance == null) {
			SessionManager.instance = new SessionManager();
		}
		return SessionManager.instance;
	}

	public void createSession(final String hash) {
		if (checkHash(hash)) {
			sessionMap.put(createSessionHash(), Calendar.getInstance().getTimeInMillis() + 600000);
		} else {

		}
	}

	private boolean checkHash(final String hash) {
		// QueryManager queryManager = new DatabaseQueryManager();
		// return queryManager.checkPhoneNumberHash(hash);
		return true;
	}

	private String createSessionHash() {
			
		return Calendar.getInstance().getTimeInMillis() + "a" + Calendar.getInstance().hashCode() ;
	}

	@Override
	public void run() {
		while (true) {
			if (!sessionMap.isEmpty()) {
				for (final Entry<String, Long> entry : sessionMap.entrySet()) {
					final String key = entry.getKey();
					final Long value = entry.getValue();
					if (value <= Calendar.getInstance().getTimeInMillis()) {
						sessionMap.remove(key);
					}

				}

			} else {
				// evtl Standbymodus
			}
		}
	}
}
