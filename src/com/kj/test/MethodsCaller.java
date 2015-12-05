package com.kj.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodsCaller {

	public static void main(final String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		final String one = (String) loadMethod("get", "question");
		final String two = (String) loadMethod("get", "user");
		System.out.println(one);
		System.out.println(two);
	}

	public static Object loadMethod(final String request, final String entity) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		final Method[] methods = MethodsToCall.class.getDeclaredMethods();
		for (final Method method : methods) {
			if (method.isAnnotationPresent(Query.class)) {
				final Query query = method.getAnnotation(Query.class);
				if (query.request().equalsIgnoreCase(request) && query.entity().equalsIgnoreCase(entity)) {
					// first cone - first surf (Wenn zwei Annotations gleich
					// sind, wird die erste Methode genommen)
					// new Object[] {} -> Parameter, die zu üergeben sind
					// könnte man über ein Object lösen mit Attributen id1, id2,
					// newString etc, die dann einfach mit set gesetzt werden
					return method.invoke(new MethodsToCall(), new Object[] {});
				}
			}
		}
		return null;
	}

}
