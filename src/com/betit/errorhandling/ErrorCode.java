package com.betit.errorhandling;

public enum ErrorCode {
	HASH_ERROR (1337, "Hash failed");
	
	private final int code;
	private final String message;
	
	ErrorCode(int code, String message){
		this.code = code;
		this.message = message;
	}
	public String toString(){
		return "Code " + code + ": " + message;
	}
}
