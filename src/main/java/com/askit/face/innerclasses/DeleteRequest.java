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
			DatabaseLayerException, ServletException {
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
			if (parameters.containsKey(Constants.PARAMETERS_USERID)
					&& parameters.containsKey(Constants.PARAMETERS_CONTACTID)) {
				userID = Long.parseLong(parameters.get(Constants.PARAMETERS_USERID)[0]);
				contactID = Long.parseLong(parameters.get(Constants.PARAMETERS_CONTACTID)[0]);
				queryManager.deleteContact(userID, contactID);
			} else if (parameters.containsKey(Constants.PARAMETERS_USERID)
					&& parameters.containsKey(Constants.PARAMETERS_GROUPID)) {
				userID = Long.parseLong(parameters.get(Constants.PARAMETERS_USERID)[0]);
				groupID = Long.parseLong(parameters.get(Constants.PARAMETERS_GROUPID)[0]);
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
			if (parameters.containsKey(Constants.PARAMETERS_GROUPID)) {
				groupID = Long.parseLong(parameters.get(Constants.PARAMETERS_GROUPID)[0]);
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
			if (parameters.containsKey(Constants.PARAMETERS_QUESTIONID)
					&& parameters.containsKey(Constants.PARAMETERS_PUBLIC)) {
				questionID = Long.parseLong(parameters.get(Constants.PARAMETERS_QUESTIONID)[0]);
				isPublic = Boolean.parseBoolean(parameters.get(Constants.PARAMETERS_PUBLIC)[0]);
				if (!isPublic) {
					queryManager.deletePrivateQuestion(questionID);
				}
			} else {
				throw new MissingParametersException("Not enough Parameters specified");
			}

			return;
		}

		throw new ServletException();

	}

}
