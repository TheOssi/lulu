package com.betit.errorhandling;

public class Error {
	ErrorCode errorCode;
	String message;
	String metadata;

	public Error(ErrorCode msg, String metadata) {
		this.errorCode = msg;
		this.message = msg.toString();
		this.metadata = metadata;
	}

	public ErrorCode getMsg() {
		return errorCode;
	}

	public void setMsg(ErrorCode msg) {
		this.errorCode = msg;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
}
