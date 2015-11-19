package com.betit.kai.tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.betit.queries.QueryManager;
import com.betit.queries.QueryManagerInterface;

public class KaiTests {

	public static void main(final String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		final String p1 = "get";
		final String p2 = "String";

		final QueryManagerInterface bqi = new QueryManager();

		// get Methode mit Name der Klasse und Parameter Typen, damit überladene
		// Methoden erkannt werden
		final Method m = bqi.getClass().getMethod(p1 + p2, new Class[] { String.class });

		// ruft die Methode mit definierten Parameter auf
		final Object o = m.invoke(bqi, new Object[] { new String("Hallo") });

		// Test
		System.out.println((String) o);

	}

}
