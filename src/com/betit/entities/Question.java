package com.betit.entities;

import java.util.Date;

public class Question {

	private final Long questionID;
	private final String question;
	private final String additionalInformation;
	private final Long hostID;
	private final Long groupID;
	private final Date createDate;
	private final Date endDate;
	private final Boolean allAnswered;
	private final Boolean optionExtension;
	private final Integer definitionOfEnd;
	private final Integer sumOfUsersToAnswer;
	private final Boolean finished;
	private final Long rightAnswerID;

	public Question(final Long questionID, final String question, final String additionalInformation, final Long hostID, final Long groupID,
			final Date createDate, final Date endDate, final Boolean allAnswered, final Boolean optionExtension, final Integer definitionOfEnd,
			final Integer sumOfUsersToAnswer, final Boolean finished, final Long rightAnswerID) {
		this.questionID = questionID;
		this.question = question;
		this.additionalInformation = additionalInformation;
		this.hostID = hostID;
		this.groupID = groupID;
		this.createDate = createDate;
		this.endDate = endDate;
		this.allAnswered = allAnswered;
		this.optionExtension = optionExtension;
		this.definitionOfEnd = definitionOfEnd;
		this.sumOfUsersToAnswer = sumOfUsersToAnswer;
		this.finished = finished;
		this.rightAnswerID = rightAnswerID;
	}

	public Long getBqID() {
		return questionID;
	}

	public String getTitle() {
		return question;
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

	public Long getGroupID() {
		return groupID;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public Boolean getAllAnswered() {
		return allAnswered;
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

	public Long getRightAnswerID() {
		return rightAnswerID;
	}
}