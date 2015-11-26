package com.askit.face;

public class MappedUserHash {
	private String username;
	private long time;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public MappedUserHash(String username, long time) {
		this.username = username;
		this.time = time;
	}
	
	
}
