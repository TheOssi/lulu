package com.askit.entities;

import java.util.Date;

public class PrivateQuestion {

	public final static String TABLE_NAME = "PrivateQuestions";
	public final static String QUESTION_ID = "questionID";
	public final static String QUESTION = "question";
	public final static String ADDITIONAL_INFORMATION = "additionalInformation";
	public final static String HOST_ID = "hostID";
	public final static String PICTURE_URI = "pictureURI";
	public final static String GROUP_ID = "groupID";
	public final static String CREATED_DATE = "createDate";
	public final static String END_DATE = "endDate";
	public final static String OPTION_EXTENSION = "optionExtension";
	public final static String DEFINITION_OF_END = "definitionOfEnd";
	public final static String SUM_OF_USERS_TO_ANSWER = "sumOfUsersToAnswer";
	public final static String FINISHED = "finished";
	public final static String SELECTED_ANSWER_ID = "selectedAnswerID";
	public final static String LANGUAGE = "language";
	public final static String IS_BET = "isBet";

	private Long questionID;
	private String question;
	private String additionalInformation;
	private Long hostID;
	private String pictureURI;
	private Long groupID;
	private Date createDate;
	private Date endDate;
	private Boolean optionExtension;
	private Integer definitionOfEnd;
	private Integer sumOfUsersToAnswer;
	private Boolean finished;
	private Long selectedAnswerID;
	private String language;
	private Boolean isBet;

	public PrivateQuestion() {
	}

	public PrivateQuestion(final Long questionID, final String question, final String additionalInformation, final Long hostID,
			final String pictureURI, final Long groupID, final Date createDate, final Date endDate, final Boolean optionExtension,
			final Integer definitionOfEnd, final Integer sumOfUsersToAnswer, final Boolean finished, final Long selectedAnswerID,
			final String language, final Boolean isBet) {
		this.questionID = questionID;
		this.question = question;
		this.additionalInformation = additionalInformation;
		this.hostID = hostID;
		this.pictureURI = pictureURI;
		this.groupID = groupID;
		this.createDate = createDate;
		this.endDate = endDate;
		this.optionExtension = optionExtension;
		this.definitionOfEnd = definitionOfEnd;
		this.sumOfUsersToAnswer = sumOfUsersToAnswer;
		this.finished = finished;
		this.selectedAnswerID = selectedAnswerID;
		this.language = language;
		this.isBet = isBet;
	}

	public PrivateQuestion(final String question, final String additionalInformation, final Long hostID, final String pictureURI, final Long groupID,
			final Date endDate, final Boolean optionExtension, final Integer definitionOfEnd, final Integer sumOfUsersToAnswer,
			final Boolean finished, final Long selectedAnswerID, final String language, final Boolean isBet) {
		this.question = question;
		this.additionalInformation = additionalInformation;
		this.hostID = hostID;
		this.pictureURI = pictureURI;
		this.groupID = groupID;
		this.endDate = endDate;
		this.optionExtension = optionExtension;
		this.definitionOfEnd = definitionOfEnd;
		this.sumOfUsersToAnswer = sumOfUsersToAnswer;
		this.finished = finished;
		this.selectedAnswerID = selectedAnswerID;
		this.language = language;
		this.isBet = isBet;
	}

	public Long getQuestionID() {
		return questionID;
	}

	public String getQuestion() {
		return question;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public Long getHostID() {
		return hostID;
	}

	public String getPictureURI() {
		return pictureURI;
	}

	public Long getGroupID() {
		return groupID;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public Boolean getOptionExtension() {
		return optionExtension;
	}

	public Integer getDefinitionOfEnd() {
		return definitionOfEnd;
	}

	public Integer getSumOfUsersToAnswer() {
		return sumOfUsersToAnswer;
	}

	public Boolean getFinished() {
		return finished;
	}

	public Long getSelectedAnswerID() {
		return selectedAnswerID;
	}

	public String getLanguage() {
		return language;
	}

	public Boolean getIsBet() {
		return isBet;
	}

	public void setQuestionID(final Long questionID) {
		this.questionID = questionID;
	}

	public void setQuestion(final String question) {
		this.question = question;
	}

	public void setAdditionalInformation(final String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public void setHostID(final Long hostID) {
		this.hostID = hostID;
	}

	public void setPictureURI(final String pictureURI) {
		this.pictureURI = pictureURI;
	}

	public void setGroupID(final Long groupID) {
		this.groupID = groupID;
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public void setOptionExtension(final Boolean optionExtension) {
		this.optionExtension = optionExtension;
	}

	public void setDefinitionOfEnd(final Integer definitionOfEnd) {
		this.definitionOfEnd = definitionOfEnd;
	}

	public void setSumOfUsersToAnswer(final Integer sumOfUsersToAnswer) {
		this.sumOfUsersToAnswer = sumOfUsersToAnswer;
	}

	public void setFinished(final Boolean finished) {
		this.finished = finished;
	}

	public void setSelectedAnswerID(final Long selectedAnswerID) {
		this.selectedAnswerID = selectedAnswerID;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	public void setIsBet(final Boolean isBet) {
		this.isBet = isBet;
	}

}