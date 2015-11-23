package com.betit.face;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;

import com.betit.queries.DatabaseQueryManager;
import com.betit.queries.QueryManager;

public class SessionManager implements Runnable {

	private static SessionManager instance;
	private HashMap<String, Long> sessionMap;
	private QueryManager queryManager ;
	private SessionManager() {
		final Thread t1 = new Thread(new SessionManager());
		queryManager = new DatabaseQueryManager();
		t1.start();
	}

	public static synchronized SessionManager getInstance() {
		if (SessionManager.instance == null) {
			SessionManager.instance = new SessionManager();
		}
		return SessionManager.instance;
	}

	public void createSession(final String hash) {
		try {
			if (checkHash(hash)) {
				sessionMap.put(createSessionHash(), Calendar.getInstance().getTimeInMillis() + 600000);
			} else {
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean checkHash(final String hash) throws SQLException {
		 
		return queryManager.checkPhoneNumberHash(hash);

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
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
		}
	}
}
