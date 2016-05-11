package com.askit.face;

import java.util.Calendar;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.askit.database.DatabaseQueryManager;
import com.askit.database.QueryManager;
import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.WrongHashException;
/*
 * Handles Sessions acquired by Users
 * Users request Sessionhash which is valid for a certain period of time, after this period they have to get a new one
 * This concept enables a higher security standard
 */
public class SessionManager implements Runnable {

	private static final SessionManager INSTANCE = new SessionManager();
	private static final long SESSIONTIME = 12 * 60 * 1000; // Min * 60 * 1000

	private static Thread checkSessionsThread;
	private final ConcurrentHashMap<String, MappedUserHash> sessionMap = new ConcurrentHashMap<String, MappedUserHash>();
	private final QueryManager queryManager = new DatabaseQueryManager();

	private SessionManager() {
	}
	/*
	 * starts Thread
	 */
	public void start() {
		if (checkSessionsThread == null || !(checkSessionsThread.isAlive())) {
			checkSessionsThread = new Thread(INSTANCE);
			checkSessionsThread.start();
		}
	}
	/*
	 * returns the Instance of the SessionManager
	 */
	public static synchronized SessionManager getInstance() {
		return INSTANCE;
	}
	/*
	 * Deletes all Sessions
	 */
	public void deleteAllSessions() {
		if (checkSessionsThread.isAlive()) {
			checkSessionsThread.interrupt();
		}
		sessionMap.clear();
		if (checkSessionsThread.isInterrupted()) {
			checkSessionsThread.start();
		}
	}
	/*
	 * Creates Session
	 */
	public String createSession(final String username, final String passwordHash) throws WrongHashException, DuplicateHashException,
			DatabaseLayerException {
		if (checkHash(username, passwordHash)) {
			// TODO think about that
			final String sessionHash = createSessionHash(username);
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
	/*
	 * Destroys Session for a certain User
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
	/*
	 * Destroys a single sessionHash
	 */
	public void destroySession(final String sessionHash) {
		sessionMap.remove(sessionHash);
	}
	/*
	 * Checks Passwordhash of User
	 */
	private boolean checkHash(final String username, final String passwordHash) throws DatabaseLayerException {
		queryManager.checkUser(username, passwordHash);
		return true;
		// TODO Hashüberprüfung
	}

	/*
	 * creates unique SessionHash
	 */
	private String createSessionHash(String seed) {
		long hash = Calendar.getInstance().getTimeInMillis();
		if(seed == null || seed == ""){
			seed =  "HalloSaschaKaiIstBloed";
		}else{
			seed = seed.concat("HalloSaschaKaiIstBloed");
		}
		for (int i = 0; i < seed.length(); i++) {
			hash = hash * 31 + seed.charAt(i);
		}
		return Long.toString(Math.abs(hash)) + Calendar.getInstance().getTimeInMillis() * (Math.random()*10);
	}
	/*
	 * verifies if a sessionHash is valid
	 */
	public boolean isValidSessionHash(final String sessionHash) throws WrongHashException {
		return sessionMap.containsKey(sessionHash);
	}
	/*
	 * runing logic(non-Javadoc)
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
					Thread.sleep(5000);
				} catch (final InterruptedException e) {
					checkSessionsThread.start();
				}
			}
		}
	}
}