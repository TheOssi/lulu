package com.askit.face;

import java.util.Calendar;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.askit.database.DatabaseQueryManager;
import com.askit.database.QueryManager;
import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.WrongHashException;

/**
 * Handles sessions acquired by users request. A sessionhash is valid for a
 * certain period of time, after this period they have to get a new one. This
 * concept enables a higher security standard
 *
 * @author Max Lenk
 * @version 1.0.0.0
 * @since 1.0.0.0
 *
 */
public class SessionManager implements Runnable {

	private static final int SLEEP_TIME = 5000;
	private static final SessionManager INSTANCE = new SessionManager();
	private static final long SESSIONTIME = 12 * 60 * 1000; // Min * 60 * 1000

	private static Thread checkSessionsThread;
	private final ConcurrentHashMap<String, MappedUserHash> sessionMap = new ConcurrentHashMap<String, MappedUserHash>();
	private final QueryManager queryManager = new DatabaseQueryManager();

	private SessionManager() {
	}

	/**
	 * This method starts the Thread SessionManager, because it's implemented as
	 * a singelton Thread. The thread checking the sessions for elapsed ones.
	 */
	public void start() {
		if (checkSessionsThread == null || !checkSessionsThread.isAlive()) {
			checkSessionsThread = new Thread(INSTANCE);
			checkSessionsThread.start();
		}
	}

	/**
	 * returns the Instance of the SessionManager
	 *
	 * @return
	 */
	public static synchronized SessionManager getInstance() {
		return INSTANCE;
	}

	/**
	 * Creates a session for a user
	 *
	 * @param username
	 *            the username of the login
	 * @param passwordHash
	 *            the passwordHash for the login
	 * @return Sessionhash: String
	 * @throws WrongHashException
	 *             if the passwordHash is not valid in the combination with the
	 *             given username
	 * @throws DuplicateHashException
	 *             if the generated sessionHash is currently used by another
	 *             session
	 * @throws DatabaseLayerException
	 *             if something in the datasbase layer went wrong
	 */
	public String createSession(final String username, final String passwordHash)
			throws WrongHashException, DuplicateHashException, DatabaseLayerException {
		if (checkHash(username, passwordHash)) {
			final String sessionHash = createSessionHash(username);
			if (!sessionMap.containsKey(sessionHash)) {
				sessionMap.put(sessionHash,
						new MappedUserHash(username, Calendar.getInstance().getTimeInMillis() + SESSIONTIME));
				return sessionHash;
			} else {
				throw new DuplicateHashException("Hash already existing");
			}
		} else {
			throw new WrongHashException("Wrong passwordHash");
		}
	}

	/**
	 * Destroys als session for a certain User
	 *
	 * @param username
	 *            the username of the user
	 */
	public void destroySessionsForUser(final String username) {
		for (final Entry<String, MappedUserHash> entry : sessionMap.entrySet()) {
			final String key = entry.getKey();
			final String value = entry.getValue().getUsername();
			if (value.equals(username)) {
				sessionMap.remove(key);
			}
		}
	}

	/**
	 * Destroys a single session of a user
	 *
	 * @param sessionHash
	 *            the sessionHash of the session to destroy
	 */
	public void destroySession(final String sessionHash) {
		sessionMap.remove(sessionHash);
	}

	/**
	 * Checks the passwordhash of a user
	 *
	 * @param username
	 *            the username of the user
	 * @param passwordHash
	 *            the hash of the password of the user
	 * @return true, if the passwordHash is valid in the combination with the
	 *         username; false, if not
	 * @throws DatabaseLayerException
	 *             thrown, if something went wrong in the database layer
	 */
	private boolean checkHash(final String username, final String passwordHash) throws DatabaseLayerException {
		// TODO add return to the line below and remove the "return true"
		queryManager.checkUser(username, passwordHash);
		return true;
	}

	/**
	 * creates unique SessionHash
	 *
	 * @param seed
	 *            the seed for the session hash
	 * @return a unique session hash
	 */
	private String createSessionHash(String seed) {
		long hash = Calendar.getInstance().getTimeInMillis();
		if (seed == null || seed == "") {
			seed = "TestMaxSaschaFabiKai";
		} else {
			seed = seed.concat("TestMaxSaschaFabiKai");
		}
		for (int i = 0; i < seed.length(); i++) {
			hash = hash * 31 + seed.charAt(i) * Double.doubleToLongBits(Math.random() * 100);
		}
		return Long.toString(Math.abs((hash) * Calendar.getInstance().getTimeInMillis()), Character.MAX_RADIX);
	}

	/**
	 * verifies if a sessionHash is valid
	 *
	 * @param sessionHash
	 *            the session hash to control
	 * @return true, if valid; false, if not
	 * @throws WrongHashException
	 */
	public boolean isValidSessionHash(final String sessionHash) {
		return sessionMap.containsKey(sessionHash);
	}

	/*
	 * runing logic(non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
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
					Thread.sleep(SLEEP_TIME);
				} catch (final InterruptedException e) {
					checkSessionsThread.start();
				}
			}
		}
	}
}