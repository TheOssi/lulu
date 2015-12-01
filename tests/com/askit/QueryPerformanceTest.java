package com.askit;

import java.sql.SQLException;

import com.askit.entities.PublicQuestion;
import com.askit.exception.DriverNotFoundException;
import com.askit.queries.DatabaseQueryManager;

public class QueryPerformanceTest {

	public static void main(final String[] args) {
		t1();
	}

	private static void t1() {
		try {
			final long time = System.currentTimeMillis();
			final PublicQuestion[] questions = new DatabaseQueryManager().getPublicQuestions(20, 10);
			System.out.println(System.currentTimeMillis() - time);
			for (final PublicQuestion publicQuestion : questions) {

				System.out.println(publicQuestion);
			}
		} catch (SQLException | DriverNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
