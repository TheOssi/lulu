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

public class GetRequestOld {
	private final Pattern regExQuestionPattern = Pattern.compile("/QUESTION/([0-9]*)|/QUESTION");
	private final Pattern regExQuestionsPattern = Pattern.compile("/QUESTION");

	private final Pattern regExGroupPattern = Pattern.compile("/GROUP/([0-9]*)|/GROUP");
	private final Pattern regExGroupsPattern = Pattern.compile("/GROUPS");

	private final Pattern regExAnswerPattern = Pattern.compile("/ANSWER/([0-9]*)|/ANSWER");
	private final Pattern regExAnswersPattern = Pattern.compile("/ANSWERS");

	private final Pattern regExUserPattern = Pattern.compile("/USER/([0-9]*)|/USER");
	private final Pattern regExUserScorePattern = Pattern.compile("/USER/SCORE/([0-9]*)");
	private final Pattern regExUsersPattern = Pattern.compile("/USERS");

	private final Pattern regExSessionPattern = Pattern.compile("/SESSION");

	private Integer id;

	@SuppressWarnings("unused")
	public GetRequestOld(final String pathInfo, final Map<String, String[]> parameters, final PrintWriter out) throws ServletException, SQLException,
			DriverNotFoundException, WrongHashException, DuplicateHashException, MissingParametersException, ModellToObjectException, DatabaseLayerException {

		Matcher matcher;
		final QueryManager queryManager = new DatabaseQueryManager();
		final String shash[] = parameters.get(Constants.PARAMETERS_SESSIONHASH);
		final JSONBuilder jsonBuilder = new JSONBuilder();
		matcher = regExSessionPattern.matcher(pathInfo);
		if (matcher.find()) {
			String hash[];
			if (!parameters.isEmpty()) {
				hash = parameters.get("HASH");
				out.println("{hash : " + SessionManager.getInstance().createSession(hash[0], "blala") + " }");
			} else {
				throw new MissingParametersException("Missing Userhash");
			}
			return;
		}

		if (SessionManager.getInstance().isValidSessionHash(shash[0])) {
			// /GROUP
			// SINGLE GROUP
			matcher = regExGroupPattern.matcher(pathInfo);
			if (matcher.find()) {
				id = Integer.parseInt(matcher.group(1));

				out.println(jsonBuilder
						.createJSON(new Group(new Long(id), Calendar.getInstance().getTime(), new Long(1337), "KaiIstGay", "/bla/blubber/fasel")));

				return;
			}
			// /GROUPS
			// ENTITYSET GROUP
			matcher = regExGroupsPattern.matcher(pathInfo);
			if (matcher.find()) {

				out.println(jsonBuilder
						.createJSON(new Group(new Long(id), Calendar.getInstance().getTime(), new Long(1337), "KaiIstGay", "/bla/blubber/fasel")));

				return;
			}

			// /USER/ID returns Username
			matcher = regExUserPattern.matcher(pathInfo);
			if (matcher.find()) {
				id = Integer.parseInt(matcher.group(1));
				if (id != null) {
					final String username = queryManager.getUsername(id);
					out.println("{username : " + id + "}");
				} else {
					throw new MissingParametersException("Missing ID in Parameters");
				}
				return;

			}

			// /USER/SCORE/ID + GROUPID=ID Pattern returns Global or Group Score
			matcher = regExUserScorePattern.matcher(pathInfo);
			if (matcher.find()) {
				final Long groupID = Long.parseLong(parameters.get(Constants.PARAMETERS_GROUPID)[0]);
				Long userscore;
				id = Integer.parseInt(matcher.group(1));
				if (id != null) {
					if (groupID != null) {
						userscore = queryManager.getUserScoreInGroup(id, groupID);
					} else {
						userscore = queryManager.getUserScoreOfGlobal(id);
					}
					out.println("{Score : " + userscore + "}");
				} else {
					throw new MissingParametersException("Missing ID in Parameters");
				}
				return;
			}

			// /USERS + Parameters
			// returns Users
			// Answer Parameter + Question = getUsersofAnswer
			// Public Flag --> true when "TRUE" , FALSE --> when not set
			matcher = regExUsersPattern.matcher(pathInfo);
			if (matcher.find()) {
				final String groupID = parameters.get(Constants.PARAMETERS_GROUPID)[0];
				final String searchPattern = parameters.get(Constants.PARAMETERS_SEARCH)[0];
				final Long questionID = Long.parseLong(parameters.get(Constants.PARAMETERS_QUESTIONID)[0]);
				final Long answerID = Long.parseLong(parameters.get(Constants.PARAMETERS_ANSWERID)[0]);
				final boolean isPublic = Boolean.parseBoolean(parameters.get(Constants.PARAMETERS_PUBLIC)[0]);
				User[] users = null;

				if (!groupID.isEmpty() || !searchPattern.isEmpty() || questionID != null) {
					if (!groupID.isEmpty() && searchPattern.isEmpty() && questionID == null) {
						users = queryManager.getUsersOfGroup(Long.parseLong(groupID));
					} else if (!searchPattern.isEmpty() && groupID.isEmpty()) {
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

					out.println(jsonBuilder.createJSON(users));
				} else {
					throw new MissingParametersException("No Parameters specified.");
				}
				return;
			}
		}
		// Question
		// /QUESTION/ID 
		// Parameter Public: True/False
		matcher = regExQuestionPattern.matcher(pathInfo);
		if (matcher.find()) {
			id = Integer.parseInt(matcher.group(1));
			final boolean isPublic = Boolean.parseBoolean(parameters.get(Constants.PARAMETERS_PUBLIC)[0]);
			if (id != null && !isPublic) {
				out.println(jsonBuilder.createJSON(queryManager.getPrivateQuestion(id)));

			} else {
				throw new MissingParametersException("Missing ID in Parameters");
			}
			return;
		}
		// Questions
		matcher = regExQuestionsPattern.matcher(pathInfo);
		if (matcher.find()) {
			final Long groupID = Long.parseLong(parameters.get(Constants.PARAMETERS_GROUPID)[0]);
			final Long questionID = Long.parseLong(parameters.get(Constants.PARAMETERS_QUESTIONID)[0]);
			final Long userID = Long.parseLong(parameters.get(Constants.PARAMETERS_USERID)[0]);
			final boolean isPublic = Boolean.parseBoolean(parameters.get(Constants.PARAMETERS_PUBLIC)[0]);
			final boolean isExpired = Boolean.parseBoolean(parameters.get(Constants.PARAMETERS_ACTIVE)[0]);
			final int startIndex = Integer.parseInt(parameters.get(Constants.PARAMETERS_STARTINDEX)[0]);
			final int quantity = Integer.parseInt(parameters.get(Constants.PARAMETERS_QUANTITY)[0]);
			final String language = parameters.get(Constants.PARAMETERS_LANGUAGE)[0];

			if (isPublic) {
				PublicQuestion[] publicQuestions;
				if (userID == null && quantity != 0 && language != null) {
					publicQuestions = queryManager.getPublicQuestions(startIndex, quantity, language);
				} else if (userID != null && quantity != 0 && !isExpired) {
					publicQuestions = queryManager.getActivePublicQuestionsOfUser(userID, startIndex, quantity);
				} else if (userID != null && quantity != 0 && isExpired) {
					publicQuestions = queryManager.getOldPublicQuestionsOfUser(userID, startIndex, quantity);
				} else {
					throw new MissingParametersException("No or not enough Parameters specified");
				}
				out.println(jsonBuilder.createJSON(publicQuestions));
			} else {
				PrivateQuestion[] privateQuestions = null;
				if (questionID == null && groupID != null && startIndex != 0 && quantity != 0) {
					privateQuestions = queryManager.getQuestionsOfGroup(groupID, startIndex, quantity);
				} else if (questionID == null && groupID != null && startIndex != 0 && quantity != 0 && isExpired) {
					privateQuestions = queryManager.getOldPrivateQuestions(groupID, startIndex, quantity);
				} else if (questionID != null && groupID == null && quantity == 0 && userID == null) {
					out.println(jsonBuilder.createJSON(queryManager.getPrivateQuestion(questionID)));
				} else if (questionID == null && groupID == null && !isExpired && userID != null && startIndex != 0 && quantity != 0) {
					privateQuestions = queryManager.getActivePrivateQuestionsOfUser(userID, startIndex, quantity);
				} else if (questionID == null && groupID == null && isExpired && userID != null && startIndex != 0 && quantity != 0) {
					privateQuestions = queryManager.getOldPrivateQuestionsOfUser(userID, startIndex, quantity);
				} else {
					throw new MissingParametersException("No or not enough Parameters specified");
				}
				out.println(jsonBuilder.createJSON(privateQuestions));
			}
			return;
		}
		// Answer
		matcher = regExAnswerPattern.matcher(pathInfo);
		if (matcher.find()) {
			return;
		}
		// Answers
		matcher = regExAnswersPattern.matcher(pathInfo);
		if (matcher.find()) {
			final Long questionID = Long.parseLong(parameters.get(Constants.PARAMETERS_QUESTIONID)[0]);
			final Long userID = Long.parseLong(parameters.get(Constants.PARAMETERS_USERID)[0]);
			final boolean isPublic = Boolean.parseBoolean(parameters.get(Constants.PARAMETERS_PUBLIC)[0]);
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

		throw new ServletException("Invalid URI");
	}
	public void handleRequest(){
		
	}

}
