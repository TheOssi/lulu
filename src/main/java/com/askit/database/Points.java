package com.askit.database;

public enum Points {

	POINTS_PUBLIC_QUESTION_CREATE(10),
	POINTS_PUBLIC_QUESTION_ANSWERED_BY_USER(1),
	POINTS_PUBLIC_QUESTION_ANSWERED_FOR_HOST(1),
	POINTS_PRIVATE_QUESTION_ANSWERED(1),
	POINTS_PRIVATE_BET_RIGHT_ANSWER(5);

	private int points;

	private Points(final int points) {
		this.points = points;
	}

	public int getPoints() {
		return points;
	}
}