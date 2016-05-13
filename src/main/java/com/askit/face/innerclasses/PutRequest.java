package com.askit.face.innerclasses;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;

import com.askit.database.DatabaseQueryManager;
import com.askit.database.QueryManager;
import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.MissingParametersException;
import com.askit.exception.NotificationException;
import com.askit.exception.WrongHashException;
import com.askit.notification.Notification;
import com.askit.notification.NotificationCodes;
import com.askit.notification.NotificationSupporter;

public class PutRequest extends Request {

	/*
	 * Constructor Calls super() Constructor, see Request;
	 */
	public PutRequest(String pathInfo, Map<String, String[]> parameters, PrintWriter out) {
		super(pathInfo, parameters, out);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.askit.face.innerclasses.Request#handleRequest() Processes
	 * PutRequest and handles required Actions
	 */
	@Override
	public void handleRequest() throws MissingParametersException, WrongHashException, DuplicateHashException,
			DatabaseLayerException, ServletException, NotificationException {
		super.handleRequest();
		final QueryManager queryManager = new DatabaseQueryManager();

		Long groupID = null;
		Long questionID = null;
		Long userID = null;
		boolean isPublic = false;
		String language = null;
		Long answerID = null;
		String userName = null;
		String passwordHash = null;
		String phoneNumberHash = null;
		Long adminID = null;
		String groupName = null;
		String pictureUrl = null;

		/*
		 * PUT /USER Parameters: USERID: Long USERNAME: String LANGUAGE: String
		 * PASSWORDHASH: String PHONEHASH: String PICTUREURL: String
		 */
		matcher = regExUserPattern.matcher(this.pathInfo);
		if (matcher.find()) {
			if (parameters.containsKey(URLConstants.PARAMETERS_USERID)
					&& parameters.containsKey(URLConstants.PARAMETERS_USERNAME)) {
				userID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_USERID)[0]);
				userName = parameters.get(URLConstants.PARAMETERS_USERNAME)[0];
				queryManager.setUsername(userID, userName);
			} else if (parameters.containsKey(URLConstants.PARAMETERS_USERID)
					&& parameters.containsKey(URLConstants.PARAMETERS_LANGUAGE)) {
				userID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_USERID)[0]);
				language = parameters.get(URLConstants.PARAMETERS_LANGUAGE)[0];
				queryManager.setLanguage(userID, language);
			} else if (parameters.containsKey(URLConstants.PARAMETERS_USERID)
					&& parameters.containsKey(URLConstants.PARAMETERS_PASSWORDHASH)) {
				userID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_USERID)[0]);
				passwordHash = parameters.get(URLConstants.PARAMETERS_PASSWORDHASH)[0];
				queryManager.setPasswordHash(userID, passwordHash);
			} else if (parameters.containsKey(URLConstants.PARAMETERS_USERID)
					&& parameters.containsKey(URLConstants.PARAMETERS_PHONEHASH)) {
				userID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_USERID)[0]);
				phoneNumberHash = parameters.get(URLConstants.PARAMETERS_PHONEHASH)[0];
				queryManager.setPhoneNumberHash(userID, phoneNumberHash);
			} else if (parameters.containsKey(URLConstants.PARAMETERS_USERID)
					&& parameters.containsKey(URLConstants.PARAMETERS_PICTUREURL)) {
				userID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_USERID)[0]);
				pictureUrl = parameters.get(URLConstants.PARAMETERS_PICTUREURL)[0];
				queryManager.setProfilPictureOfUser(userID, pictureUrl);
			} else {
				throw new MissingParametersException();
			}
			return;
		}
		/*
		 * PUT /QUESTION Parameters: QUESTIONID: Long ANSWERID: Long PUBLIC:
		 * Boolean USERID: Long
		 */
		matcher = regExQuestionPattern.matcher(this.pathInfo);
		if (matcher.find()) {
			if (parameters.containsKey(URLConstants.PARAMETERS_QUESTIONID)
					&& parameters.containsKey(URLConstants.PARAMETERS_ANSWERID)
					&& parameters.containsKey(URLConstants.PARAMETERS_PUBLIC)
					&& !parameters.containsKey(URLConstants.PARAMETERS_USERID)) {
				isPublic = Boolean.parseBoolean(parameters.get(URLConstants.PARAMETERS_PUBLIC)[0]);
				questionID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_QUESTIONID)[0]);
				answerID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_ANSWERID)[0]);
				if (!isPublic) {
					queryManager.setSelectedAnswerOfPrivateQuestion(questionID, answerID);
						Notification not = new Notification("", NotificationCodes.NOTIFICATION_ANSWER_SET.getCode(),"questionID", questionID.toString());
						NotificationSupporter.sendNotificationToAllMembersOfAGroup(not, groupID);	
				}

			} else if (parameters.containsKey(URLConstants.PARAMETERS_QUESTIONID)
					&& parameters.containsKey(URLConstants.PARAMETERS_ANSWERID)
					&& parameters.containsKey(URLConstants.PARAMETERS_PUBLIC)
					&& parameters.containsKey(URLConstants.PARAMETERS_USERID)) {
				isPublic = Boolean.parseBoolean(parameters.get(URLConstants.PARAMETERS_PUBLIC)[0]);
				questionID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_QUESTIONID)[0]);
				answerID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_ANSWERID)[0]);
				userID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_USERID)[0]);
				if (!isPublic) {
					queryManager.setChoosedAnswerOfPrivateQuestion(userID, questionID, answerID);
					//Trigger.setPointsForAnsweringAPrivateQuestion(groupID, userID);
				} else {
					queryManager.setChoosedAnswerOfPublicQuestion(userID, questionID, answerID);
					
					//Trigger.setPointsForAnsweringAPublicQuestion(userID, hostID);
				}
			} else {
				throw new MissingParametersException();
			}
			return;
		}
	/*
	 * PUT /GROUP Parameters: USERID: Long GROUPID: Long GROUNAME: String
	 * PICTUREURL: String
	 */
	matcher=regExGroupPattern.matcher(this.pathInfo);if(matcher.find())

	{
		if (parameters.containsKey(URLConstants.PARAMETERS_GROUPID)
				&& parameters.containsKey(URLConstants.PARAMETERS_USERID)) {
			adminID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_USERID)[0]);
			groupID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_GROUPID)[0]);
			queryManager.setGroupAdmin(groupID, adminID);
		} else if (parameters.containsKey(URLConstants.PARAMETERS_GROUPID)
				&& parameters.containsKey(URLConstants.PARAMETERS_GROUPNAME)) {
			groupID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_GROUPID)[0]);
			groupName = parameters.get(URLConstants.PARAMETERS_GROUPNAME)[0];
			queryManager.setGroupName(groupID, groupName);
		} else if (parameters.containsKey(URLConstants.PARAMETERS_GROUPID)
				&& parameters.containsKey(URLConstants.PARAMETERS_GROUPNAME)) {
			groupID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_GROUPID)[0]);
			pictureUrl = parameters.get(URLConstants.PARAMETERS_PICTUREURL)[0];
			queryManager.setGroupPicture(groupID, pictureUrl);
		} else {
			throw new MissingParametersException();
		}
		return;
	} throw new ServletException("Invalid URI");
}}
