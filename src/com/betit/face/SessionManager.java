package com.betit.face;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;

import com.betit.exception.DriverNotFoundException;
import com.betit.exception.WrongHashException;
import com.betit.queries.DatabaseQueryManager;
import com.betit.queries.QueryManager;

public class SessionManager implements Runnable {

	private static SessionManager instance;
	private HashMap<String, Long> sessionMap;
	private final QueryManager queryManager;
	private static Thread checkSessionsThread;

	private static final long sessionTime = 600000;

	private SessionManager() {
		checkSessionsThread = new Thread(instance);
		queryManager = new DatabaseQueryManager();
		checkSessionsThread.start();
	}

	public static synchronized SessionManager getInstance() {
		if (SessionManager.instance == null) {
			SessionManager.instance = new SessionManager();
		}
		return SessionManager.instance;
	}

	public static void destroy() {
		if (checkSessionsThread != null) {
			checkSessionsThread.interrupt();
		}
		if (instance != null) {
			instance = null;
		}
	}

	public void createSession(final String hash) throws SQLException, DriverNotFoundException, WrongHashException {
		if (checkHash(hash)) {
			String sessionHash = createSessionHash();

			if (!(sessionMap.containsKey(sessionHash))) {
				sessionMap.put(sessionHash, Calendar.getInstance().getTimeInMillis() + sessionTime);
			}
		} else {
			throw new WrongHashException("Wrong Hash");
		}
	}

	private boolean checkHash(final String hash) throws SQLException, DriverNotFoundException {
		return queryManager.checkPhoneNumberHash(hash);
	}

	private String createSessionHash() {
		return Calendar.getInstance().getTimeInMillis() + "a" + Calendar.getInstance().hashCode(); //TODO
	}
	
	public boolean isValidSessionHash(String hash) throws WrongHashException{
		if(sessionMap.containsKey(hash)){
			return true;
		}
		else{
			return false;
			//evtl. Exception wird im Face geworfen
		}
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
				} catch (final InterruptedException e) {
					//reicht das für einen sicheren Neustart?
					SessionManager.destroy();
					SessionManager.getInstance();
				}
			}
		}
	}
}