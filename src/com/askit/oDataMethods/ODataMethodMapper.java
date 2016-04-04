package com.askit.oDataMethods;

import java.util.HashMap;
import java.util.Map;

public final class ODataMethodMapper {
	private final Map<String, MethodCaller> map;

	private final ODataMethodManager odataMethods;

	public ODataMethodMapper() {
		map = new HashMap<String, MethodCaller>();
		odataMethods = new ODataMethodManager();
		map.put("GET/USER", new MethodCaller() {
			@Override
			public Object call(final Map<String, String[]> urlParameters) {
				return odataMethods.getUser(urlParameters);
			}
		});
	}

	public Object runMethodWithResult(final String odata, final Map<String, String[]> urlParameters) {
		return map.get(odata).call(urlParameters);
	}

	public void runMethodWithoutResult(final String odata, final Map<String, String[]> urlParameters) {
		map.get(odata).call(urlParameters);
	}
}
