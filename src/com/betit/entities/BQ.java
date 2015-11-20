package com.betit.entities;

import java.util.Date;

public class BQ {

	private final Long bqID;
	private final String title;
	private final String question;
	private final String additionalInformation;
	private final Long hostID;
	private final Long groupID;
	private final String pictureURI;
	private final Date createDate;
	private final Date endDate;
	private final Boolean allAnswered;
	private final Boolean optionExtension;
	private final Integer definitionOfEnd;
	private final Integer sumOfUsersToAnswer;
	private final Boolean finished;
	private final Long rightAnswerID;
	private final Boolean doubed;
	private final Boolean isBet;

	public BQ(final Long bqID, final String title, final String question, final String additionalInformation, final Long hostID, final Long groupID,
			final String pictureURI, final Date createDate, final Date endDate, final Boolean allAnswered, final Boolean optionExtension,
			final Integer definitionOfEnd, final Integer sumOfUsersToAnswer, final Boolean finished, final Long rightAnswerID, final Boolean doubed,
			final Boolean isBet) {
		this.bqID = bqID;
		this.title = title;
		this.question = question;
		this.additionalInformation = additionalInformation;
		this.hostID = hostID;
		this.groupID = groupID;
		this.pictureURI = pictureURI;
		this.createDate = createDate;
		this.endDate = endDate;
		this.allAnswered = allAnswered;
		this.optionExtension = optionExtension;
		this.definitionOfEnd = definitionOfEnd;
		this.sumOfUsersToAnswer = sumOfUsersToAnswer;
		this.finished = finished;
		this.rightAnswerID = rightAnswerID;
		this.doubed = doubed;
		this.isBet = isBet;
	}

	public Long getBqID() {
		return bqID;
	}

	public String getTitle() {
		return title;
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

	public String getPictureURI() {
		return pictureURI;
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

	public Boolean getDoubed() {
		return doubed;
	}

	public Boolean getIsBet() {
		return isBet;
	}

}
