package com.askit.entities;

import com.thirdparty.entities.Column;
import com.thirdparty.entities.Entity;

@Entity
public class Answer {
	@Column(name = "answerID")
	private Long answerID;

	@Column(name = "questionID")
	private Long questionID;

	@Column(name = "answer")
	private String answer;

	public Answer() {
		this(null, null, null);
	}

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

	public void setAnswerID(final Long answerID) {
		this.answerID = answerID;
	}

	public void setQuestionID(final Long questionID) {
		this.questionID = questionID;
	}

	public void setAnswer(final String answer) {
		this.answer = answer;
	}

}
