package com.askit.face;

public class MappedUserHash {
	private String username;
	private long time;
	public String getUsername() {
		return username;
	}
	public long getTime() {
		return time;
	}
	public MappedUserHash(String username, long time) {
		this.username = username;
		this.time = time;
	}
	
	
}
