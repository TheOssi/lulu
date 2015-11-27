package com.askit.entities;

public class Answer {

	private final Long answerID;
	private final Long questionID;
	private final String answer;

	public Answer(final long questionID, final String answer) {
		this(null, questionID, answer);
	}

	public Answer(final Long answerID, final Long questionID, final String answer) {
		this.answerID = answerID;
		this.questionID = questionID;
		this.answer = answer;
	}

	public Long getAnswerID() {
		return answerID;
	}

	public Long getQuestionID() {
		return questionID;
	}

	public String getAnswer() {
		return answer;
	}

}
