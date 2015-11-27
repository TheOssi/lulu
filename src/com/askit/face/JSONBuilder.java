package com.askit.face;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class JSONBuilder {
	GsonBuilder gb = new GsonBuilder();
	Gson gson = 	gb.setPrettyPrinting().create();
	
	public JSONBuilder() {

	}

	// Generate from Single Entity
	public String createJSON(final Object o) {

		final JsonObject jo = new JsonObject(); 
	
		jo.add(o.getClass().getSimpleName(), gson.toJsonTree(o));
		return gson.toJson(jo);
	}

	// Generate from Collection
	public String createJSON(final Object[] objectArray) {
		final JsonObject jo = new JsonObject();
		final JsonObject innerJo = new JsonObject();
		for (Object currentObject : objectArray) {
			innerJo.add(currentObject.getClass().getSimpleName() + currentObject.hashCode(), gson.toJsonTree(currentObject));
			jo.add(currentObject.getClass().getSimpleName() + "Set", innerJo);
		}
		return gson.toJson(jo);
	}
	
	private String removeNulls(String json){
		Type type = new TypeToken<Map<String, Object>>() {}.getType();
		Map<String, Object> data = new Gson().fromJson(json, type);

		for (Iterator<Map.Entry<String, Object>> it = data.entrySet().iterator(); it.hasNext();) {
		    Map.Entry<String, Object> entry = it.next();
		    if (entry.getValue() == null) {
		        it.remove();
		    } else if (entry.getValue().getClass().equals(ArrayList.class)) {
		        if (((ArrayList<?>) entry.getValue()).size() == 0) {
		            it.remove();
		        }
		    }
		}

		json = new GsonBuilder().setPrettyPrinting().create().toJson(data);
		return json;
	}
}
