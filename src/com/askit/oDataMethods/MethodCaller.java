package com.askit.oDataMethods;

import java.util.Map;

public interface MethodCaller {

	public Object call(Map<String, String[]> urlParameters);

}
