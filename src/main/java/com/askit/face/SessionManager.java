package com.askit.face;

import java.util.Calendar;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.askit.database.DatabaseQueryManager;
import com.askit.database.QueryManager;
import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.WrongHashException;

public class SessionManager implements Runnable {

	private static final SessionManager INSTANCE = new SessionManager();
	private static final long SESSIONTIME = 12 * 60 * 1000; // Min * 60 * 1000

	private static Thread checkSessionsThread;
	private final ConcurrentHashMap<String, MappedUserHash> sessionMap = new ConcurrentHashMap<String, MappedUserHash>();
	private final QueryManager queryManager = new DatabaseQueryManager();

	private SessionManager() {
	}

	public void start() {
		if (checkSessionsThread == null || !(checkSessionsThread.isAlive())) {
			checkSessionsThread = new Thread(INSTANCE);
			checkSessionsThread.start();
		}
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

	public String createSession(final String username, final String passwordHash) throws WrongHashException, DuplicateHashException,
			DatabaseLayerException {
		if (checkHash(username, passwordHash)) {
			// TODO think about that
			final String sessionHash = createSessionHash();
			if (!sessionMap.containsKey(sessionHash)) {
				sessionMap.put(sessionHash, new MappedUserHash(username, Calendar.getInstance().getTimeInMillis() + SESSIONTIME));
				return sessionHash;
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

	private boolean checkHash(final String username, final String passwordHash) throws DatabaseLayerException {
		queryManager.checkUser(username, passwordHash);
		return true;
		// TODO Hashüberprüfung
	}

	// really unique?; maybe Math-random and dele the String word!!
	private String createSessionHash() {
		long hash = Calendar.getInstance().getTimeInMillis();
		final String word = "HalloSaschaKaiIstBloed";
		for (int i = 0; i < 11; i++) {
			hash = hash * 31 + word.charAt(i);
		}
		return Long.toString(Math.abs(hash));// Calendar.getInstance().getTimeInMillis()
												// + "a" +
												// (Math.random()*10000); //
												// TODO
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