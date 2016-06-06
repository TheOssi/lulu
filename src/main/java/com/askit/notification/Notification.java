package com.askit.notification;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Kai Müller, Max Lenk
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class Notification implements Serializable {

	private static final String JSON_FIELD_PARAM2 = "param2";
	private static final String JSON_FIELD_PARAM1 = "param1";
	private static final String JSON_FIELD_CODE = "code";
	private static final long serialVersionUID = 1L;

	private String to;
	private final Map<String, String> data = new HashMap<String, String>();

	public Notification(final String receiverID, final String code, final String param1, final String param2) {
		to = receiverID;
		data.put(JSON_FIELD_CODE, code);
		data.put(JSON_FIELD_PARAM1, param1);
		data.put(JSON_FIELD_PARAM2, param2);
	}

	public void setTo(final String recieverID) {
		to = recieverID;
	}

	public String getTo() {
		return to;
	}
}