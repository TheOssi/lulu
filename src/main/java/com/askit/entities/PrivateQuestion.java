package com.askit.entities;

import java.util.Date;

import com.thirdparty.entities.Column;
import com.thirdparty.entities.Entity;

@Entity
public class PrivateQuestion {

	@Column(name = "questionID")
	private Long questionID;
	@Column(name = "question")
	private String question;
	@Column(name = "additionalInformation")
	private String additionalInformation;
	@Column(name = "hostID")
	private Long hostID;
	@Column(name = "pictureURI")
	private String pictureURI;
	@Column(name = "groupID")
	private Long groupID;
	@Column(name = "createDate")
	private Date createDate;
	@Column(name = "endDate")
	private Date endDate;
	@Column(name = "optionExtension")
	private Boolean optionExtension;
	@Column(name = "definitionOfEnd")
	private Integer definitionOfEnd;
	@Column(name = "sumOfUsersToAnswer")
	private Integer sumOfUsersToAnswer;
	@Column(name = "finished")
	private Boolean finished;
	@Column(name = "selectedAnswerID")
	private Long selectedAnswerID;
	@Column(name = "language")
	private String language;
	@Column(name = "isBet")
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