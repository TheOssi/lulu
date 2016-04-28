package com.askit.notification;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Notification implements Serializable {

	private static final long serialVersionUID = 1L;

	private String to;
	private final Map<String, String> data = new HashMap<String, String>();

	public Notification(final String receiverID, final String code, final String param1, final String param2) {
		to = receiverID;
		data.put("code", code);
		data.put("param1:", param1);
		data.put("param2", param2);
	}

	public void setTo(String recieverID) {
		this.to = recieverID;
	}
	public String getTo(){
		return this.to;
	}
}