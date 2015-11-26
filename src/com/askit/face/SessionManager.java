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

	private static final SessionManager INSTANCE = new SessionManager();
	private static final long SESSIONTIME = 10 * 60 * 1000; // Min * 60 * 1000

	private static Thread checkSessionsThread = new Thread(INSTANCE);
	private final HashMap<String, MappedUserHash> sessionMap = new HashMap<String, MappedUserHash>();
	private final QueryManager queryManager = new DatabaseQueryManager();;

	private SessionManager() {
		checkSessionsThread.start();
	}

	public static synchronized SessionManager getInstance() {
		return INSTANCE;
	}

	public void deleteAllSessions() {
		if (checkSessionsThread.isAlive()) {
			checkSessionsThread.interrupt();
		}
		sessionMap.clear();
		if (checkSessionsThread.isInterrupted()) {
			checkSessionsThread.start();
		}
	}

	public void createSession(final String username, final String passwordHash) throws SQLException, DriverNotFoundException, WrongHashException,
			DuplicateHashException {
		if (checkHash(username, passwordHash)) {
			final String sessionHash = createSessionHash();
			if (!sessionMap.containsKey(sessionHash)) {
				sessionMap.put(sessionHash, new MappedUserHash(username, Calendar.getInstance().getTimeInMillis() + SESSIONTIME));
			} else {
				throw new DuplicateHashException("Hash already existing");
			}
		} else {
			throw new WrongHashException("Wrong passwordHash");
		}
	}

	public void destroySessionsForUser(final String username) {
		for (final Entry<String, MappedUserHash> entry : sessionMap.entrySet()) {
			final String key = entry.getKey();
			final String value = entry.getValue().getUsername();
			if (value.equals(username)) {
				sessionMap.remove(key);
			}
		}
	}

	public void destroySession(final String sessionHash) {
		sessionMap.remove(sessionHash);
	}

	private boolean checkHash(final String username, final String passwordHash) throws SQLException, DriverNotFoundException {
		return queryManager.checkUser(username, passwordHash);
	}

	private String createSessionHash() {
		return Calendar.getInstance().getTimeInMillis() + "a" + Calendar.getInstance().hashCode(); // TODO
	}

	public boolean isValidSessionHash(final String sessionHash) throws WrongHashException {
		return sessionMap.containsKey(sessionHash);
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