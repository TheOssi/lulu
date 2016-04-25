package com.askit.notification;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Notification implements Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private final String to;
	private final Map<String, String> data = new HashMap<String, String>();

	public Notification(final String receiverID, final String code) {
		to = receiverID;
		data.put("code", code);
	}
}