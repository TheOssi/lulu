package com.askit.face.innerclasses;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;

import com.askit.entities.User;
import com.askit.etc.Constants;
import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.MissingParametersException;
import com.askit.exception.WrongHashException;
import com.askit.face.JSONBuilder;
import com.askit.queries.DatabaseQueryManager;
import com.askit.queries.QueryManager;

public class PostRequest extends Request {
	public PostRequest(final String pathInfo, final Map<String, String[]> parameters, final PrintWriter out){
		super(pathInfo,parameters,out);
		
	}
	public void handleRequest() throws MissingParametersException, WrongHashException, DuplicateHashException, DatabaseLayerException, ServletException{
		super.handleRequest();
		final JSONBuilder jsonBuilder = new JSONBuilder();
		final QueryManager queryManager = new DatabaseQueryManager();
		
		final Long groupID = Long.parseLong(parameters.get(Constants.PARAMETERS_GROUPID)[0]);
		final Long questionID = Long.parseLong(parameters.get(Constants.PARAMETERS_QUESTIONID)[0]);
		final Long userID = Long.parseLong(parameters.get(Constants.PARAMETERS_USERID)[0]);
		final boolean isPublic = Boolean.parseBoolean(parameters.get(Constants.PARAMETERS_PUBLIC)[0]);
		final boolean isExpired = Boolean.parseBoolean(parameters.get(Constants.PARAMETERS_ACTIVE)[0]);
		final int startIndex = Integer.parseInt(parameters.get(Constants.PARAMETERS_STARTINDEX)[0]);
		final int quantity = Integer.parseInt(parameters.get(Constants.PARAMETERS_QUANTITY)[0]);
		final String language = parameters.get(Constants.PARAMETERS_LANGUAGE)[0];
		final String searchPattern = parameters.get(Constants.PARAMETERS_SEARCH)[0];
		final Long answerID = Long.parseLong(parameters.get(Constants.PARAMETERS_ANSWERID)[0]);
		final String userName = parameters.get(Constants.PARAMETERS_USERNAME)[0];
		final String passwordHash = parameters.get(Constants.PARAMETERS_PASSWORDHASH)[0];;
		final String phoneNumberHash = parameters.get(Constants.PARAMETERS_PHONEHASH)[0];
		
		/*POST
		 *   	/USER
		 *   	@params: 
		 *   	USERNAME: String
		 *   	PHONEHASH: String
		 *   	PASSWORDHASH: String
		 *   	LANGUAGE: String
		 */
		matcher = regExUserPattern.matcher(pathInfo);
		if (matcher.find()) {
			id = Integer.parseInt(matcher.group(1));
			if ( userName!= null && phoneNumberHash != null && passwordHash != null && language != null) {
				final Date accessionDate = null;
				final String profilePictureURI = null;
				final int scoreOfGlobal = 0;
				User user = new User(userID,passwordHash,phoneNumberHash,userName,accessionDate,profilePictureURI,language,scoreOfGlobal);
				queryManager.registerUser(user);
			} else {
				throw new MissingParametersException("Missings Parameters");
			}
			return;

		}
		
	//	queryManager.createNewGroup(group);
		//queryManager.createNewQuestionInGroup(question);
		//queryManager.createOneTimeQuestion(question);
	//	queryManager.createPublicQuestion(question);
		
	}
}
