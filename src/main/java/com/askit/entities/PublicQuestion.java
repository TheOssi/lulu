package com.askit.entities;

import java.util.Date;

public class PublicQuestion {

	public final static String QUESTION_ID = "questionID";
	public final static String QUESTION = "question";
	public final static String ADDITIONAL_INFORMATION = "additionalInformation";
	public final static String HOST_ID = "hostID";
	public final static String PICTURE_URI = "pictureURI";
	public final static String CREATED_DATE = "createDate";
	public final static String END_DATE = "endDate";
	public final static String OPTION_EXTENSION = "optionExtension";
	public final static String FINISHED = "finished";
	public final static String LANGUAGE = "language";

	private Long questionID;
	private String question;
	private String additionalInformation;
	private Long hostID;
	private String pictureURI;
	private Date createDate;
	private Date endDate;
	private Boolean optionExtension;
	private Boolean finished;
	private String language;

	public PublicQuestion() {
	}

	public PublicQuestion(final Long questionID, final String question, final String additionalInformation, final Long hostID,
			final String pictureURI, final Date createDate, final Date endDate, final Boolean optionExtension, final Boolean finished,
			final String language) {
		this.questionID = questionID;
		this.question = question;
		this.additionalInformation = additionalInformation;
		this.hostID = hostID;
		this.pictureURI = pictureURI;
		this.createDate = createDate;
		this.endDate = endDate;
		this.optionExtension = optionExtension;
		this.finished = finished;
		this.language = language;
	}

	public PublicQuestion(final String question, final String additionalInformation, final Long hostID, final String pictureURI,
			final Date createDate, final Date endDate, final Boolean optionExtension, final Boolean finished, final String language) {
		this.question = question;
		this.additionalInformation = additionalInformation;
		this.hostID = hostID;
		this.pictureURI = pictureURI;
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

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public void setOptionExtension(final Boolean optionExtension) {
		this.optionExtension = optionExtension;
	}

	public void setFinished(final Boolean finished) {
		this.finished = finished;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "PublicQuestion [questionID=" + questionID + ", question=" + question + ", additionalInformation=" + additionalInformation
				+ ", hostID=" + hostID + ", pictureURI=" + pictureURI + ", createDate=" + createDate + ", endDate=" + endDate + ", optionExtension="
				+ optionExtension + ", finished=" + finished + ", language=" + language + "]";
	}

}