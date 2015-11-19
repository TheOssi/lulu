package com.betit.entities;

public class Answer {

	private final Long answerID;
	private final Long bqID;
	private final String answer;

	public Answer(final long bqID, final String answer) {
		this(null, bqID, answer);
	}

	public Answer(final Long answerID, final Long bqID, final String answer) {
		super();
		this.answerID = answerID;
		this.bqID = bqID;
		this.answer = answer;
	}

	public Long getAnswerID() {
		return answerID;
	}

	public Long getBqID() {
		return bqID;
	}

	public String getAnswer() {
		return answer;
	}

}
