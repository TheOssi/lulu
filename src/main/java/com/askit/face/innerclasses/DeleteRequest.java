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

public class DeleteRequest extends Request {

	public DeleteRequest(String pathInfo, Map<String, String[]> parameters, PrintWriter out) {
		super(pathInfo, parameters, out);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.askit.face.innerclasses.Request#handleRequest() Processes Delete
	 * Request
	 */
	@Override
	public void handleRequest() throws MissingParametersException, WrongHashException, DuplicateHashException,
			DatabaseLayerException, ServletException, NotificationException {
		super.handleRequest();
		final QueryManager queryManager = new DatabaseQueryManager();
		Long groupID = null;
		Long contactID = null;
		Long questionID = null;
		Long userID = null;
		boolean isPublic = false;

		/*
		 * DELETE /USER PARAMETERS: USERID: Long, CONTACTID: Long Deletes
		 * Contact from User OR Deletes User from Group
		 */
		matcher = regExUserPattern.matcher(pathInfo);
		if (matcher.find()) {
			if (parameters.containsKey(URLConstants.PARAMETERS_USERID)
					&& parameters.containsKey(URLConstants.PARAMETERS_CONTACTID)) {
				userID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_USERID)[0]);
				contactID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_CONTACTID)[0]);
				queryManager.deleteContact(userID, contactID);
			} else if (parameters.containsKey(URLConstants.PARAMETERS_USERID)
					&& parameters.containsKey(URLConstants.PARAMETERS_GROUPID)) {
				userID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_USERID)[0]);
				groupID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_GROUPID)[0]);
				queryManager.deleteUserFromGroup(groupID, userID);
			} else {
				throw new MissingParametersException("Not enough Parameters specified");
			}

			return;
		}

		/*
		 * DELETE /GROUP PARAMETERS: GROUPID: Long Deletes Group
		 */
		matcher = regExGroupPattern.matcher(pathInfo);
		if (matcher.find()) {
			if (parameters.containsKey(URLConstants.PARAMETERS_GROUPID)) {
				groupID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_GROUPID)[0]);
				queryManager.deleteGroup(groupID);
			} else {
				throw new MissingParametersException("Not enough Parameters specified");
			}

			return;
		}
		/*
		 * DELETE /QUESTION PARAMETERS: QUESTIONID: Long PUBLIC: Boolean Deletes
		 * Deletes Private Question
		 */
		matcher = regExGroupPattern.matcher(pathInfo);
		if (matcher.find()) {
			if (parameters.containsKey(URLConstants.PARAMETERS_QUESTIONID)
					&& parameters.containsKey(URLConstants.PARAMETERS_PUBLIC)) {
				questionID = Long.parseLong(parameters.get(URLConstants.PARAMETERS_QUESTIONID)[0]);
				isPublic = Boolean.parseBoolean(parameters.get(URLConstants.PARAMETERS_PUBLIC)[0]);
				if (!isPublic) {
					queryManager.deletePrivateQuestion(questionID);
				}
			} else {
				throw new MissingParametersException("Not enough Parameters specified");
			}

			return;
		}

		matcher = regExSessionPattern.matcher(pathInfo);
		if (matcher.find()) {
		} else {
			throw new ServletException("Invalid URI");
		}

	}

}
