package com.askit.face.innerclasses;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;

import com.askit.database.DatabaseQueryManager;
import com.askit.database.QueryManager;
import com.askit.entities.Answer;
import com.askit.entities.Group;
import com.askit.entities.PrivateQuestion;
import com.askit.entities.PublicQuestion;
import com.askit.entities.User;
import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.MissingParametersException;
import com.askit.exception.NotificationException;
import com.askit.exception.WrongHashException;
import com.askit.face.FileSupporter;
import com.askit.notification.Notification;
import com.askit.notification.NotificationCodes;
import com.askit.notification.NotificationHandler;
import com.askit.notification.RegIDHandler;

/**
 * @author D062367
 *
 */

public class PostRequest extends Request {
	final String body;

	/**
	 * @param pathInfo
	 * @param parameters
	 * @param out
	 * @param body
	 */
	public PostRequest(final String pathInfo, final Map<String, String[]> parameters, final PrintWriter out, final String body) {
		super(pathInfo, parameters, out);
		this.body = body;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.askit.face.innerclasses.Request#handleRequest() handles
	 * PostRequest
	 */

	@Override
	public void handleRequest() throws MissingParametersException, WrongHashException, DuplicateHashException, DatabaseLayerException,
			ServletException, NotificationException {
		super.handleRequest();

		final QueryManager queryManager = new DatabaseQueryManager();

		Long groupID = null;
		Long questionID = null;
		Long userID = null;
		boolean isPublic = false;
		boolean isExpired = false;
		String language = null;
		Long answerID = null;
		String userName = null;
		String passwordHash = null;
		String phoneNumberHash = null;
		Long adminID = null;
		String groupName = null;
		String pictureUrl = null;
		String question = null;
		String additionalInformation = null;
		Long hostID = null;
		boolean optionExtension = false;
		Long eDate = null;
		boolean isBet = false;
		int definitionOfEnd = 0;
		int sumOfUsersToAnswer = 0;
		Long selectedAnswerID = null;
		boolean isOneTime = false;
		String answerText = null;
		Long contactID = null;
		String regID = null;

		if (parameters.containsKey(URLConstants.PARAMETERS_GROUPID)) {
			groupID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_GROUPID)[0]);
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_QUESTIONID)) {
			questionID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_QUESTIONID)[0]);
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_USERID)) {
			userID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_USERID)[0]);
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_PUBLIC)) {
			isPublic = Boolean.parseBoolean(parameters.get(URLConstants.PARAMETERS_PUBLIC)[0]);
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_ACTIVE)) {
			isExpired = Boolean.parseBoolean(parameters.get(URLConstants.PARAMETERS_ACTIVE)[0]);
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_LANGUAGE)) {
			language = parameters.get(URLConstants.PARAMETERS_LANGUAGE)[0];
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_ANSWERID)) {
			answerID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_ANSWERID)[0]);
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_USERNAME)) {
			userName = parameters.get(URLConstants.PARAMETERS_USERNAME)[0];
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_PASSWORDHASH)) {
			passwordHash = parameters.get(URLConstants.PARAMETERS_PASSWORDHASH)[0];
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_PHONEHASH)) {
			phoneNumberHash = parameters.get(URLConstants.PARAMETERS_PHONEHASH)[0];
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_ADMINID)) {
			adminID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_ADMINID)[0]);
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_GROUPNAME)) {
			groupName = parameters.get(URLConstants.PARAMETERS_GROUPNAME)[0];
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_PICTUREURL)) {
			pictureUrl = parameters.get(URLConstants.PARAMETERS_PICTUREURL)[0];
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_QUESTION)) {
			question = parameters.get(URLConstants.PARAMETERS_QUESTION)[0];
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_INFORMATION)) {
			additionalInformation = parameters.get(URLConstants.PARAMETERS_INFORMATION)[0];
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_HOSTID)) {
			hostID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_HOSTID)[0]);
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_EXTENSION)) {
			optionExtension = Boolean.parseBoolean(parameters.get(URLConstants.PARAMETERS_EXTENSION)[0]);
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_ENDDATE)) {
			eDate = Long.parseLong(parameters.get(URLConstants.PARAMETERS_ENDDATE)[0]);
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_BET)) {
			isBet = Boolean.parseBoolean(parameters.get(URLConstants.PARAMETERS_BET)[0]);
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_DEFINITIONEND)) {
			definitionOfEnd = Integer.parseInt(parameters.get(URLConstants.PARAMETERS_DEFINITIONEND)[0]);
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_SUMANSWERS)) {
			sumOfUsersToAnswer = Integer.parseInt(parameters.get(URLConstants.PARAMETERS_SUMANSWERS)[0]);
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_SELECTEDANSWER)) {
			selectedAnswerID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_SELECTEDANSWER)[0]);
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_ONETIME)) {
			isOneTime = Boolean.parseBoolean(parameters.get(URLConstants.PARAMETERS_ONETIME)[0]);
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_ANSWER)) {
			answerText = parameters.get(URLConstants.PARAMETERS_ANSWER)[0];
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_CONTACTID)) {
			contactID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_CONTACTID)[0]);
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_REGID)) {
			regID = parameters.get(URLConstants.PARAMETERS_REGID)[0];
		}
		/*
		 * POST /USER
		 * 
		 * @params: USERNAME: String PHONEHASH: String PASSWORDHASH: String
		 * LANGUAGE: String
		 */

		/*
		 * POST /USER
		 * 
		 * @params: USERID: Long CONTACTID: Long
		 */

		matcher = regExUserPattern.matcher(pathInfo);
		if (matcher.find()) {
			final NotificationHandler notificationHandler = NotificationHandler.getInstance();
			final RegIDHandler regHandler = RegIDHandler.getInstance();
			if (userName != null && phoneNumberHash != null && passwordHash != null && language != null) {
				final Date accessionDate = null;
				final String profilePictureURI = null;
				final int scoreOfGlobal = 0;
				final User user = new User(userID, passwordHash, phoneNumberHash, userName, accessionDate, profilePictureURI, language, scoreOfGlobal);
				queryManager.createUser(user);
				out.println("{message: " + "Sucessfully create User}");
			} else if (userID != null && contactID != null) {
				queryManager.addContact(userID, contactID);
				out.println("{message: " + "Sucessfully added Contact}");
			} else if (userID != null && groupID != null) {
				queryManager.addUserToGroup(groupID, userID);
				out.println("{message: " + "Sucessfully added User to Group}");
				regID = regHandler.getRegIDFromUser(userID);
				final Notification not = new Notification(regID, NotificationCodes.NOTIFICATION_ADDED_TO_GROUP.getCode(), "groupID",
						groupID.toString());
				notificationHandler.addNotification(not);
			} else if (userID != null && questionID != null && isOneTime) {
				queryManager.addUserToOneTimeQuestion(userID, questionID);
				out.println("{message: " + "Sucessfully added User to Question}");
			} else if (userID != null && questionID != null && isPublic) {
				queryManager.addUserToPublicQuestion(questionID, userID);
				regID = regHandler.getRegIDFromUser(userID);
				final Notification not = new Notification(regID, NotificationCodes.NOTIFICATION_INVITE_PUBLIC.getCode(), "questionID",
						questionID.toString());
				notificationHandler.addNotification(not);
				out.println("{message: " + "Sucessfully added User to Question}");
			} else {
				throw new MissingParametersException("Missings Parameters");
			}
			return;

		}

		/*
		 * POST /GROUP
		 * 
		 * @params: GROUPNAME: String ADMINID: Long PICTUREURL: String
		 */
		matcher = regExGroupPattern.matcher(pathInfo);
		if (matcher.find()) {
			if (adminID != null && groupName != null && pictureUrl != null) {
				final Date createDate = Calendar.getInstance().getTime();
				final Group group = new Group(createDate, adminID, groupName, pictureUrl);
				queryManager.createNewGroup(group);
				out.println("{message: " + "Sucessfully created new Group}");
			} else {
				throw new MissingParametersException("Missings Parameters");
			}
			return;
		}

		/*
		 * POST /QUESTION
		 * 
		 * @params: PUBLIC: Boolean, defines if Public or Private Question
		 * QUESTION: String INFORMATION: String, additional Information HOSTID:
		 * Long ENDDATE: Long EXTENSION: Boolean ACTIVE: Boolean LANGUAGE:
		 * String PICTUREURL: String DEFINITIONEND: Integer SUMANSWERS: Integer
		 * SELECTEDANSWER: Long ISBET: Boolean
		 */
		matcher = regExQuestionPattern.matcher(pathInfo);
		if (matcher.find()) {
			final NotificationHandler notificationHandler = NotificationHandler.getInstance();
			final RegIDHandler regHandler = RegIDHandler.getInstance();
			final Date createDate = Calendar.getInstance().getTime();
			if (isPublic && question != null && additionalInformation != null && hostID != null && pictureUrl != null && eDate != null
					&& language != null) {
				final Date endDate = new Date(createDate.getTime() + eDate);
				final PublicQuestion publicQuestion = new PublicQuestion(question, additionalInformation, hostID, pictureUrl, createDate, endDate,
						optionExtension, isExpired, language);
				queryManager.createPublicQuestion(publicQuestion);
				regID = regHandler.getRegIDFromUser(hostID);
				final Notification not = new Notification(regID, NotificationCodes.NOTIFICATION_NEW_QUESTION.getCode(), "hostID", hostID.toString());
				notificationHandler.addNotification(not);
			} else if (!isPublic && groupID != null) {
				final Date endDate = new Date(createDate.getTime() + eDate);
				final PrivateQuestion privateQuestion = new PrivateQuestion(question, additionalInformation, hostID, pictureUrl, groupID, endDate,
						optionExtension, definitionOfEnd, sumOfUsersToAnswer, isExpired, selectedAnswerID, language, isBet);
				queryManager.createNewPrivateQuestionInGroup(privateQuestion);

				regID = regHandler.getRegIDFromUser(userID);
				final Notification not = new Notification(regID, NotificationCodes.NOTIFICATION_NEW_QUESTION.getCode(), "hostID", hostID.toString());
				notificationHandler.addNotification(not);
			} else if (!isPublic && isOneTime) {
				final Date endDate = new Date(createDate.getTime() + eDate);
				final PrivateQuestion privateQuestion = new PrivateQuestion(question, additionalInformation, hostID, pictureUrl, groupID, endDate,
						optionExtension, definitionOfEnd, sumOfUsersToAnswer, isExpired, selectedAnswerID, language, isBet);
				queryManager.createOneTimeQuestion(privateQuestion);
			}

			else {
				throw new MissingParametersException("Missings Parameters");
			}
			return;
		}

		// Answer
		/*
		 * POST /ANSWER
		 * 
		 * @params: PUBLIC: Boolean, defines if related to Public or Private
		 * Question QUESTIONID: Long ANSWER: String, answer as Text ANSWERID:
		 * Long, when related to another answer
		 */
		matcher = regExAnswerPattern.matcher(pathInfo);
		if (matcher.find()) {
			if (questionID != null && answerText != null && !isPublic) {
				final Answer answer = new Answer(questionID, answerText);
				queryManager.addAnswerToPrivateQuestion(answer);
			} else if (questionID != null && answerText != null && isPublic) {
				final Answer answer = new Answer(questionID, answerText);
				queryManager.addAnswerToPublicQuestion(answer);
			} else if (questionID != null && answerText != null && !isPublic && answerID != null) {
				final Answer answer = new Answer(answerID, questionID, answerText);
				queryManager.addAnswerToPrivateQuestion(answer);
			} else if (questionID != null && answerText != null && isPublic && answerID != null) {
				final Answer answer = new Answer(answerID, questionID, answerText);
				queryManager.addAnswerToPublicQuestion(answer);
			}

			return;
		}
		matcher = regExGCMPattern.matcher(pathInfo);
		if (matcher.find()) {
			if (regID != null && userID != null) {
				RegIDHandler.getInstance().setRegID(userID, regID);
			} else {
				throw new MissingParametersException();
			}
			return;
		}
		matcher = regExPicturePattern.matcher(pathInfo);
		if (matcher.find()) {
			if (body != null && body != "") {
				if (groupID != null) {
					FileSupporter.createFileWithContent(body, FileSupporter.PICTURE_ROOT + "/group", groupID.toString());
				} else if (userID != null) {
					FileSupporter.createFileWithContent(body, FileSupporter.PICTURE_ROOT + "/user", userID.toString());
				} else if (questionID != null && isPublic) {
					FileSupporter.createFileWithContent(body, FileSupporter.PICTURE_ROOT + "/publicQuestion", questionID.toString());
				} else if (questionID != null && !isPublic) {
					FileSupporter.createFileWithContent(body, FileSupporter.PICTURE_ROOT + "/privateQuestion", questionID.toString());
				} else {
					throw new MissingParametersException("Missing ID for Entity");
				}
			} else {
				throw new MissingParametersException("Empty or no Body");
			}
			return;
		}
		throw new ServletException();
	}
}
