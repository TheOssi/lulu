package com.askit.face;

/**
 * This class maps a username to the expire time of his session.
 * 
 * @author Max Lenk
 * @since 1.0.0.0
 * @version 1.0.0.0
 *
 */
public class MappedUserHash {
	private final String username;
	private final long time;

	/**
	 * Creates a new MappedUserHash
	 * 
	 * @param username
	 *            the username of the user
	 * @param time
	 *            the expire time of his session
	 */
	public MappedUserHash(final String username, final long time) {
		this.username = username;
		this.time = time;
	}

	/**
	 * This nethod returns the username of the mapped expire time
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * This method returns the expireTime of the mapped username
	 * 
	 * @return the expireTime
	 */
	public long getTime() {
		return time;
	}
}