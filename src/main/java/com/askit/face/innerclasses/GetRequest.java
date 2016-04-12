package com.askit.face.innerclasses;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import org.apache.commons.lang3.tuple.Pair;

import com.askit.entities.Answer;
import com.askit.entities.Group;
import com.askit.entities.PrivateQuestion;
import com.askit.entities.PublicQuestion;
import com.askit.entities.User;
import com.askit.etc.Constants;
import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DriverNotFoundException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.MissingParametersException;
import com.askit.exception.ModellToObjectException;
import com.askit.exception.WrongHashException;
import com.askit.face.JSONBuilder;
import com.askit.face.SessionManager;
import com.askit.queries.DatabaseQueryManager;
import com.askit.queries.QueryManager;

public class GetRequest extends Request {
	private Integer id;

	/*
	 * 
	 */
	public GetRequest(final String pathInfo, final Map<String, String[]> parameters, final PrintWriter out) {
		super(pathInfo, parameters, out);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.askit.face.innerclasses.Request#handleRequest() Processes Get
	 * Request
	 */
	public void handleRequest() throws DatabaseLayerException, MissingParametersException, ServletException,
			WrongHashException, DuplicateHashException {
		final JSONBuilder jsonBuilder = new JSONBuilder();
		final QueryManager queryManager = new DatabaseQueryManager();
		Long groupID = null;
		Long questionID = null;
		Long userID = null;
		boolean isPublic = false;
		boolean isExpired = false;
		int startIndex = 0;
		int quantity = 0;
		String language = null;
		String searchPattern = null;
		Long answerID = null;

		if (parameters.containsKey(Constants.PARAMETERS_GROUPID)) {
			groupID = Long.parseLong(parameters.get(Constants.PARAMETERS_GROUPID)[0]);
		}
		if (parameters.containsKey(Constants.PARAMETERS_QUESTIONID)) {
			questionID = Long.parseLong(parameters.get(Constants.PARAMETERS_QUESTIONID)[0]);
		}
		if (parameters.containsKey(Constants.PARAMETERS_USERID)) {
			userID = Long.parseLong(parameters.get(Constants.PARAMETERS_USERID)[0]);
		}
		if (parameters.containsKey(Constants.PARAMETERS_PUBLIC)) {
			isPublic = Boolean.parseBoolean(parameters.get(Constants.PARAMETERS_PUBLIC)[0]);
		}
		if (parameters.containsKey(Constants.PARAMETERS_ACTIVE)) {
			isExpired = Boolean.parseBoolean(parameters.get(Constants.PARAMETERS_ACTIVE)[0]);
		}
		if (parameters.containsKey(Constants.PARAMETERS_STARTINDEX)) {
			startIndex = Integer.parseInt(parameters.get(Constants.PARAMETERS_STARTINDEX)[0]);
		}
		if (parameters.containsKey(Constants.PARAMETERS_QUANTITY)) {
			quantity = Integer.parseInt(parameters.get(Constants.PARAMETERS_QUANTITY)[0]);
		}
		if (parameters.containsKey(Constants.PARAMETERS_LANGUAGE)) {
			language = parameters.get(Constants.PARAMETERS_LANGUAGE)[0];
		}
		if (parameters.containsKey(Constants.PARAMETERS_SEARCH)) {
			searchPattern = parameters.get(Constants.PARAMETERS_SEARCH)[0];
		}
		if (parameters.containsKey(Constants.PARAMETERS_ANSWERID)) {
			answerID = Long.parseLong(parameters.get(Constants.PARAMETERS_ANSWERID)[0]);
		}

		super.handleRequest();
		// /GROUP
		// SINGLE GROUP
		/*
		 * GET /GROUP/ID
		 * 
		 * @params: NONE Example: /GROUP/1234
		 */
		matcher = regExGroupPattern.matcher(pathInfo);
		if (matcher.find()) {
			id = Integer.parseInt(matcher.group(1));
			if (id != null) {
				this.out.println("{ groupName: " + queryManager.getGroupName(groupID) + ", pictureUrl: "
						+ queryManager.getGroupPictureURI(groupID) + "}");
			} else if (userID != null && searchPattern != null) {
				Group[] groups;
				groups = queryManager.searchForGroup(userID, searchPattern);
				this.out.println(jsonBuilder.createJSON(groups));
			} else {
				throw new MissingParametersException();
			}

			return;
		}
		// /GROUPS
		// ENTITYSET GROUP
		/*
		 * GET /GROUPS
		 * 
		 * @params:
		 * 
		 * Example: /GROUP/1234
		 */
		matcher = regExGroupsPattern.matcher(pathInfo);
		if (matcher.find()) {

			return;
		}

		/*
		 * GET /USER/ID
		 * 
		 * @params: NONE Example: /USER/1234
		 */
		matcher = regExUserPattern.matcher(pathInfo);
		if (matcher.find()) {
			id = Integer.parseInt(matcher.group(1));
			this.out.println(id);
			if (id != null) {
				final String username = queryManager.getUsername(id);
				this.out.println("{username : " + username + "}");
			} else {
				throw new MissingParametersException("Missing ID in Parameters");
			}
			return;

		}

		// /USER/SCORE/ID + GROUPID=ID Pattern returns Global or Group Score
		/*
		 * GET /USER/SCORE/ID
		 * 
		 * @params: GROUPID: Long Example: /USER/SCORE/1234?GROUPID=423
		 */
		matcher = regExUserScorePattern.matcher(pathInfo);
		if (matcher.find()) {
			if (parameters.containsKey(Constants.PARAMETERS_GROUPID)) {
				groupID = Long.parseLong(parameters.get(Constants.PARAMETERS_GROUPID)[0]);
			}
			Long userscore;
			id = Integer.parseInt(matcher.group(1));
			this.out.println("{Score of : " + id + "}");
			if (id != null) {
				if (groupID != null) {
					userscore = queryManager.getUserScoreInGroup(id, groupID);
				} else {
					userscore = queryManager.getUserScoreOfGlobal(id);
				}
				this.out.println("{Score : " + userscore + "}");
			} else {
				throw new MissingParametersException("Missing ID in Parameters");
			}
			return;
		}

		// /USERS + Parameters
		// returns Users
		// Answer Parameter + Question = getUsersofAnswer
		// Public Flag --> true when "TRUE" , FALSE --> when not set
		/*
		 * GET /USER
		 * 
		 * @params: GROUPID: Long SEARCH: String, SearchPattern QUESTIONID: Long
		 * ANSWERID: Long PUBLIC: Boolean Example:
		 * /USER?QUESTIONID=32&ANSWERID=23&PUBLIC="TRUE"
		 */
		matcher = regExUsersPattern.matcher(pathInfo);
		if (matcher.find()) {
			User[] users = null;

			if (groupID != null || !searchPattern.isEmpty() || questionID != null) {
				if (groupID != null && searchPattern.isEmpty() && questionID == null) {
					users = queryManager.getUsersOfGroup(groupID);
				} else if (!searchPattern.isEmpty() && groupID != null) {
					users = queryManager.getUsersByUsername(searchPattern);
				} else if (questionID != null && answerID == null && isPublic) {
					users = queryManager.getUsersOfPublicQuestion(questionID);
				} else if (questionID != null && answerID == null && !isPublic) {
					users = queryManager.getUsersOfPrivateQuestion(questionID);
				} else if (questionID != null && answerID != null && !isPublic) {
					users = queryManager.getUsersOfAnswerPrivateQuestion(answerID);
				} else if (questionID != null && answerID != null && isPublic) {
					users = queryManager.getUsersOfAnswerPublicQuestion(answerID);
				}

				this.out.println(jsonBuilder.createJSON(users));
			} else {
				throw new MissingParametersException("No Parameters specified.");
			}
			return;
		}

		// Question
		// /QUESTION/ID
		// Parameter Public: True/False
		/*
		 * GET /QUESTION/ID
		 * 
		 * @params: PUBLIC: boolean Example: /QUESTION/123
		 */
		matcher = regExQuestionPattern.matcher(pathInfo);
		if (matcher.find()) {
			id = Integer.parseInt(matcher.group(1));
			if (id != null && !isPublic) {
				this.out.println(jsonBuilder.createJSON(queryManager.getPrivateQuestion(id)));

			} else {
				throw new MissingParametersException("Missing ID in Parameters");
			}
			return;
		}
		// Questions
		/*
		 * GET /QUESTIONS
		 * 
		 * @params: USERID QUANTITY LANGUAGE ACTIVE: Boolean START: StartIndex
		 * Example: /QUESTIONS
		 */
		matcher = regExQuestionsPattern.matcher(pathInfo);
		if (matcher.find()) {

			if (isPublic) {
				PublicQuestion[] publicQuestions;
				if (userID == null && quantity != 0 && language != null) {
					publicQuestions = queryManager.getPublicQuestions(startIndex, quantity, language);
				} else if (userID != null && quantity != 0 && !isExpired) {
					publicQuestions = queryManager.getActivePublicQuestionsOfUser(userID, startIndex, quantity);
				} else if (userID != null && quantity != 0 && isExpired) {
					publicQuestions = queryManager.getOldPublicQuestionsOfUser(userID, startIndex, quantity);
				} else if (userID == null && searchPattern != null) {
					publicQuestions = queryManager.searchForPublicQuestion(searchPattern);
				} else {
					throw new MissingParametersException("No or not enough Parameters specified");
				}
				this.out.println(jsonBuilder.createJSON(publicQuestions));
			} else {
				PrivateQuestion[] privateQuestions = null;
				if (questionID == null && groupID != null && startIndex != 0 && quantity != 0) {
					privateQuestions = queryManager.getQuestionsOfGroup(groupID, startIndex, quantity);
				} else if (questionID == null && groupID != null && startIndex != 0 && quantity != 0 && isExpired) {
					privateQuestions = queryManager.getOldPrivateQuestions(groupID, startIndex, quantity);
				} else if (questionID != null && groupID == null && quantity == 0 && userID == null) {
					this.out.println(jsonBuilder.createJSON(queryManager.getPrivateQuestion(questionID)));
				} else if (questionID == null && groupID == null && !isExpired && userID != null && startIndex != 0
						&& quantity != 0) {
					privateQuestions = queryManager.getActivePrivateQuestionsOfUser(userID, startIndex, quantity);
				} else if (questionID == null && groupID == null && isExpired && userID != null && startIndex != 0
						&& quantity != 0) {
					privateQuestions = queryManager.getOldPrivateQuestionsOfUser(userID, startIndex, quantity);
				} else if (groupID != null && searchPattern != null && questionID == null && userID == null) {
					privateQuestions = queryManager.searchForPrivateQuestionInGroup(groupID, searchPattern);
				} else {
					throw new MissingParametersException("No or not enough Parameters specified");
				}
				this.out.println(jsonBuilder.createJSON(privateQuestions));
			}
			return;
		}
		// Answer Not implemented?
		/*
		 * GET /Answer/ID
		 * 
		 * @params: NONE Example: /GROUP/1234
		 */
		matcher = regExAnswerPattern.matcher(pathInfo);
		if (matcher.find()) {
			return;
		}
		// Answers
		/*
		 * GET /ANSWERS
		 * 
		 * @params: QUESTIONID USERID PUBLIC Example: /ANSWERS
		 */
		matcher = regExAnswersPattern.matcher(pathInfo);
		if (matcher.find()) {
			Answer[] answers;
			Pair<Answer, Integer>[] countedAnswers;

			if (questionID != null && userID == null && !isPublic) {
				countedAnswers = queryManager.getAnswersOfPrivateQuestionAndCount(questionID);
			} else if (questionID != null && userID == null && !isPublic) {
				countedAnswers = queryManager.getAnswersOfPublicQuestionAndCount(questionID);
			} else if (questionID != null && userID != null && !isPublic) {
				answers = new Answer[1];
				answers[0] = queryManager.getChoseAnswerInPrivateQuestion(questionID, userID);
			} else {
				throw new MissingParametersException("No or not enough Parameters specified");
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
