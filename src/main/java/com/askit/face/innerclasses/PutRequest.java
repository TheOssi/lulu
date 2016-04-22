package com.askit.face.innerclasses;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;

import com.askit.database.DatabaseQueryManager;
import com.askit.database.QueryManager;
import com.askit.etc.Constants;
import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.MissingParametersException;
import com.askit.exception.WrongHashException;

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
			DatabaseLayerException, ServletException {
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
			if (parameters.containsKey(Constants.PARAMETERS_USERID)
					&& parameters.containsKey(Constants.PARAMETERS_USERNAME)) {
				userID = Long.parseLong(parameters.get(Constants.PARAMETERS_USERID)[0]);
				userName = parameters.get(Constants.PARAMETERS_USERNAME)[0];
				queryManager.setUsername(userID, userName);
			} else if (parameters.containsKey(Constants.PARAMETERS_USERID)
					&& parameters.containsKey(Constants.PARAMETERS_LANGUAGE)) {
				userID = Long.parseLong(parameters.get(Constants.PARAMETERS_USERID)[0]);
				language = parameters.get(Constants.PARAMETERS_LANGUAGE)[0];
				queryManager.setLanguage(userID, language);
			} else if (parameters.containsKey(Constants.PARAMETERS_USERID)
					&& parameters.containsKey(Constants.PARAMETERS_PASSWORDHASH)) {
				userID = Long.parseLong(parameters.get(Constants.PARAMETERS_USERID)[0]);
				passwordHash = parameters.get(Constants.PARAMETERS_PASSWORDHASH)[0];
				queryManager.setPasswordHash(userID, passwordHash);
			} else if (parameters.containsKey(Constants.PARAMETERS_USERID)
					&& parameters.containsKey(Constants.PARAMETERS_PHONEHASH)) {
				userID = Long.parseLong(parameters.get(Constants.PARAMETERS_USERID)[0]);
				phoneNumberHash = parameters.get(Constants.PARAMETERS_PHONEHASH)[0];
				queryManager.setPhoneNumberHash(userID, phoneNumberHash);
			} else if (parameters.containsKey(Constants.PARAMETERS_USERID)
					&& parameters.containsKey(Constants.PARAMETERS_PICTUREURL)) {
				userID = Long.parseLong(parameters.get(Constants.PARAMETERS_USERID)[0]);
				pictureUrl = parameters.get(Constants.PARAMETERS_PICTUREURL)[0];
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
			if (parameters.containsKey(Constants.PARAMETERS_QUESTIONID)
					&& parameters.containsKey(Constants.PARAMETERS_ANSWERID)
					&& parameters.containsKey(Constants.PARAMETERS_PUBLIC)
					&& !parameters.containsKey(Constants.PARAMETERS_USERID)) {
				isPublic = Boolean.parseBoolean(parameters.get(Constants.PARAMETERS_PUBLIC)[0]);
				questionID = Long.parseLong(parameters.get(Constants.PARAMETERS_QUESTIONID)[0]);
				answerID = Long.parseLong(parameters.get(Constants.PARAMETERS_ANSWERID)[0]);
				if (!isPublic) {
					queryManager.setSelectedAnswerOfPrivateQuestion(questionID, answerID);
				}

			} else if (parameters.containsKey(Constants.PARAMETERS_QUESTIONID)
					&& parameters.containsKey(Constants.PARAMETERS_ANSWERID)
					&& parameters.containsKey(Constants.PARAMETERS_PUBLIC)
					&& parameters.containsKey(Constants.PARAMETERS_USERID)) {
				isPublic = Boolean.parseBoolean(parameters.get(Constants.PARAMETERS_PUBLIC)[0]);
				questionID = Long.parseLong(parameters.get(Constants.PARAMETERS_QUESTIONID)[0]);
				answerID = Long.parseLong(parameters.get(Constants.PARAMETERS_ANSWERID)[0]);
				userID = Long.parseLong(parameters.get(Constants.PARAMETERS_USERID)[0]);
				if (!isPublic) {
					queryManager.setChoosedAnswerOfPrivateQuestion(userID, questionID, answerID);
				} else {
					queryManager.setChoosedAnswerOfPublicQuestion(userID, questionID, answerID);
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
		matcher = regExGroupPattern.matcher(this.pathInfo);
		if (matcher.find()) {
			if (parameters.containsKey(Constants.PARAMETERS_GROUPID)
					&& parameters.containsKey(Constants.PARAMETERS_USERID)) {
				adminID = Long.parseLong(parameters.get(Constants.PARAMETERS_USERID)[0]);
				groupID = Long.parseLong(parameters.get(Constants.PARAMETERS_GROUPID)[0]);
				queryManager.setGroupAdmin(groupID, adminID);
			} else if (parameters.containsKey(Constants.PARAMETERS_GROUPID)
					&& parameters.containsKey(Constants.PARAMETERS_GROUPNAME)) {
				groupID = Long.parseLong(parameters.get(Constants.PARAMETERS_GROUPID)[0]);
				groupName = parameters.get(Constants.PARAMETERS_GROUPNAME)[0];
				queryManager.setGroupName(groupID, groupName);
			} else if (parameters.containsKey(Constants.PARAMETERS_GROUPID)
					&& parameters.containsKey(Constants.PARAMETERS_GROUPNAME)) {
				groupID = Long.parseLong(parameters.get(Constants.PARAMETERS_GROUPID)[0]);
				pictureUrl = parameters.get(Constants.PARAMETERS_PICTUREURL)[0];
				queryManager.setGroupPicture(groupID, pictureUrl);
			} else {
				throw new MissingParametersException();
			}
			return;
		}
		throw new ServletException("Invalid URI");
	}
}
