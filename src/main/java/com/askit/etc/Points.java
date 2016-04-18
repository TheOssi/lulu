package com.askit.etc;

public enum Points {

	POINTS_PUBLIC_QUESTION_CREATE(10),
	POINTS_PUBLIC_QUESTION_ANSWERED_BY_USER(1),
	POINTS_PUBLIC_QUESTION_ANSWERED_BY_HOST(1),
	POINTS_PRIVATE_QUESTION_ANSWERED(1);

	private int points;

	private Points(final int points) {
		this.points = points;
	}

	public int getPoints() {
		return points;
	}
}