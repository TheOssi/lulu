package com.askit.entities;

import java.util.Date;

public class PublicQuestion {

	private final Long questionID;
	private final String question;
	private final String additionalInformation;
	private final Long hostID;
	private final String pictureURI;
	private final Long groupID;
	private final Date createDate;
	private final Date endDate;
	private final Boolean optionExtension;
	private final Boolean finished;
	private final String language;

	public PublicQuestion(final Long questionID, final String question, final String additionalInformation, final Long hostID,
			final String pictureURI, final Long groupID, final Date createDate, final Date endDate, final Boolean optionExtension,
			final Boolean finished, final String language) {
		this.questionID = questionID;
		this.question = question;
		this.additionalInformation = additionalInformation;
		this.hostID = hostID;
		this.pictureURI = pictureURI;
		this.groupID = groupID;
		this.createDate = createDate;
		this.endDate = endDate;
		this.optionExtension = optionExtension;
		this.finished = finished;
		this.language = language;
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

	public Boolean getFinished() {
		return finished;
	}

	public String getLanguage() {
		return language;
	}
}