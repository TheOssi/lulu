package com.askit.notification;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class RegIDHandlerTest {
	@Test
	public void testGetInstance() {
		assertThat("instance is null", RegIDHandler.getInstance() != null);
		assertThat("wrong instance", RegIDHandler.getInstance() instanceof RegIDHandler);
	}

	@Test
	public void testGetRegIDFromUser() {
		final RegIDHandler handler = RegIDHandler.getInstance();
		handler.setRegID(new Long(1), "abc");
		assertThat("wrong reg id", handler.getRegIDFromUser(new Long(1)).equals("abc"));
	}

	@Test
	public void testUpdateRegIDFromUser() {
		final RegIDHandler handler = RegIDHandler.getInstance();
		handler.setRegID(new Long(1), "abc");
		handler.setRegID(new Long(1), "def");
		assertThat("wrong reg id", handler.getRegIDFromUser(new Long(1)).equals("def"));
	}

	public void testThreadSafety() {
		final RegIDHandler handler = RegIDHandler.getInstance();
		final Thread threadOne = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10000000; i++) {
					handler.setRegID(new Long(1), "abc");
					System.out.println("Thread one: " + handler.getRegIDFromUser(new Long(1)));
				}

			}
		});
		final Thread threadTwo = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10000000; i++) {
					handler.setRegID(new Long(1), "def");
					System.out.println("Thread two: " + handler.getRegIDFromUser(new Long(1)));
				}
			}
		});
		threadOne.start();
		threadTwo.start();
	}
}