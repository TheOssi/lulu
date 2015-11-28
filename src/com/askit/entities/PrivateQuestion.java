package com.askit.entities;

import java.util.Date;

public class PrivateQuestion {

	private final Long questionID;
	private final String question;
	private final String additionalInformation;
	private final Long hostID;
	private final String pictureURI;
	private final Long groupID;
	private final Date createDate;
	private final Date endDate;
	private final Boolean optionExtension;
	private final Integer definitionOfEnd;
	private final Integer sumOfUsersToAnswer;
	private final Boolean finished;
	private final Long selectedAnswerID;
	private final String language;
	private final Boolean isBet;

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
}