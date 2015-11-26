package com.askit.face;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;

import com.askit.exception.DriverNotFoundException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.WrongHashException;
import com.askit.queries.DatabaseQueryManager;
import com.askit.queries.QueryManager;

public class SessionManager implements Runnable {

	private static SessionManager instance = new SessionManager();
	private HashMap<String, MappedUserHash> sessionMap;
	private final QueryManager queryManager;
	private static Thread checkSessionsThread;

	private static final long SESSIONTIME = 10 * 60 * 1000; // Min * 60 * 1000

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

	public void createSession(final String hash, final String username) throws SQLException, DriverNotFoundException, WrongHashException, DuplicateHashException {
		if (checkHash(hash, username)) {
			final String sessionHash = createSessionHash();

			if (!sessionMap.containsKey(sessionHash)) {
				sessionMap.put(sessionHash, new MappedUserHash(username ,Calendar.getInstance().getTimeInMillis() + SESSIONTIME));
			}
			else{
				throw new DuplicateHashException("Hash already existing");
			}
		} else {
			throw new WrongHashException("Wrong Hash");
		}
	}
	
	public void destroySessionsForUser(String username){
		for (final Entry<String, MappedUserHash> entry : sessionMap.entrySet()) {
			final String key = entry.getKey();
			final String value = entry.getValue().getUsername();
			if (value.equals(username) ) {
				sessionMap.remove(key);
			}
		}
	}
	public void destroySession(String sessionHash){
		sessionMap.remove(sessionHash);
	}

	private boolean checkHash(final String hash, final String username) throws SQLException, DriverNotFoundException {
		return queryManager.checkUser(username, hash);
	}

	private String createSessionHash() {
		return Calendar.getInstance().getTimeInMillis() + "a" + Calendar.getInstance().hashCode(); // TODO
	}

	public boolean isValidSessionHash(final String hash) throws WrongHashException {
		if (sessionMap.containsKey(hash)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void run() {
		while (true) {
			if (!sessionMap.isEmpty()) {
				for (final Entry<String, MappedUserHash> entry : sessionMap.entrySet()) {
					final String key = entry.getKey();
					final Long value = entry.getValue().getTime();
					if (value <= Calendar.getInstance().getTimeInMillis()) {
						sessionMap.remove(key);
					}
				}
			} else {
				try {
					Thread.sleep(5000);
				} catch (final InterruptedException e) {
					checkSessionsThread.start();
				}
			}
		}
	}
}