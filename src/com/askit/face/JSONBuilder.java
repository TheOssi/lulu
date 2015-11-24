package com.askit.face;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JSONBuilder {
	Gson gson = new Gson();

	public JSONBuilder() {

	}

	// Generate from Single Entity
	public String createJSON(final Object o) {

		final JsonObject jo = new JsonObject();
		final String json = gson.toJson(o);

		jo.addProperty(o.getClass().getSimpleName(), json);
		return gson.toJson(jo);

	}

	// Generate from Collection
	public String createJSON(final Object[] objectArray) {
		final JsonObject jo = new JsonObject();
		String json;
		for (final Object currentObject : objectArray) {
			json = gson.toJson(currentObject);
			jo.addProperty(currentObject.getClass().getSimpleName() + currentObject.hashCode(), json);
		}
		return gson.toJson(jo);
	}
}
