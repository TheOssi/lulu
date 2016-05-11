package com.askit.face;

public class MappedUserHash {
	private final String username;
	private final long time;

	public String getUsername() {
		return username;
	}

	public long getTime() {
		return time;
	}

	public MappedUserHash(final String username, final long time) {
		this.username = username;
		this.time = time;
	}
}