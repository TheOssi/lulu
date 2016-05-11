package com.askit.face; 

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * @author Max Lenk
 *
 */
public class JSONBuilder {
	private final GsonBuilder gsonBuilder = new GsonBuilder();
	private final Gson gson = gsonBuilder.setPrettyPrinting().create();

	/**
	 * Generates a JSON for a single object
	 *
	 * @param object
	 *            the object which should be a JSON
	 * @return a JSON object
	 */
	public String createJSON(final Object object) {

		final JsonObject jsonObject = new JsonObject();
		final JsonArray jsonArray = new JsonArray();
		jsonArray.add(gson.toJsonTree(object));
		jsonObject.add(object.getClass().getSimpleName(), jsonArray);
		return gson.toJson(jsonObject);
	}

	/**
	 * Generates a JSON from a collection
	 *
	 * @param objectArray
	 *            a array of object which should be a JSON
	 * @return a JSON object
	 */
	public String createJSON(final Object[] objectArray) {
		final JsonObject jsonObject = new JsonObject();
		final JsonArray jsonArray = new JsonArray();
		for (final Object currentObject : objectArray) {
			jsonArray.add(gson.toJsonTree(currentObject));
		}
		jsonObject.add(objectArray.getClass().getSimpleName().substring(0, objectArray.getClass().getSimpleName().length() - 2), jsonArray);
		return gson.toJson(jsonObject);
	}

	/**
	 * Removes all null's of a JSON
	 *
	 * @param json
	 *            the JSON to edit
	 * @return a JSON without null's
	 */
	@SuppressWarnings("unused")
	// TODO use this method and delete annotoation
	private String removeNulls(final String json) {
		final Type type = new TypeToken<Map<String, Object>>() {
		}.getType();
		final Map<String, Object> data = new Gson().fromJson(json, type);

		for (final Iterator<Map.Entry<String, Object>> iterator = data.entrySet().iterator(); iterator.hasNext();) {
			final Map.Entry<String, Object> entry = iterator.next();
			if (entry.getValue() == null) {
				iterator.remove();
			} else if (entry.getValue().getClass().equals(ArrayList.class)) {
				if (((ArrayList<?>) entry.getValue()).size() == 0) {
					iterator.remove();
				}
			}
		}
		final String jsonWithoutNull = new GsonBuilder().setPrettyPrinting().create().toJson(data);
		return jsonWithoutNull;
	}
}