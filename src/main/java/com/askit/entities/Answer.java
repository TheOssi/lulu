package com.askit.entities;

public class Answer {

	public static final String TABLE_NAME_PRIVATE = "AnswersPrivateQuestions";
	public static final String TABLE_NAME_PUBLIC = "AnswersPublicQuestions";

	public final static String ANSWER_ID = "answerID";
	public final static String QUESTION_ID = "questionID";
	public final static String ANSWER = "answer";

	private Long answerID;
	private Long questionID;
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
