package com.askit.face.innerclasses;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import com.askit.etc.Constants;
import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DriverNotFoundException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.MissingParametersException;
import com.askit.exception.WrongHashException;
import com.askit.face.JSONBuilder;
import com.askit.face.SessionManager;
import com.askit.queries.DatabaseQueryManager;
import com.askit.queries.QueryManager;

public class Request {
	String pathInfo;
	Map<String, String[]> parameters;
	PrintWriter out;

	final static Pattern regExQuestionPattern = Pattern.compile("/QUESTION/([0-9]*)|/QUESTION$");
	final static Pattern regExQuestionsPattern = Pattern.compile("/QUESTION");

	final static Pattern regExGroupPattern = Pattern.compile("/GROUP/([0-9]*)|/GROUP$");
	final static Pattern regExGroupsPattern = Pattern.compile("/GROUPS");

	final static Pattern regExAnswerPattern = Pattern.compile("/ANSWER/([0-9]*)|/ANSWER$");
	final static Pattern regExAnswersPattern = Pattern.compile("/ANSWERS");

	final static Pattern regExUserPattern = Pattern.compile("/USER/([0-9]*)|/USER$");
	final static Pattern regExUserScorePattern = Pattern.compile("/USER/SCORE/([0-9]*)");
	final static Pattern regExUsersPattern = Pattern.compile("/USERS");

	final Pattern regExSessionPattern = Pattern.compile("/SESSION");

	Matcher matcher;
	Integer id;

	public Request(final String pathInfo, final Map<String, String[]> parameters, final PrintWriter out) {
		this.pathInfo = pathInfo;
		this.parameters = parameters;
		this.out = out;
	}

	/*
	 * Method for general request processing Checks for valid session and
	 * handles request f0r sessionhashes
	 */
	public void handleRequest() throws MissingParametersException, WrongHashException, DuplicateHashException,
			DatabaseLayerException, ServletException {
		final String shash[] = this.parameters.get(Constants.PARAMETERS_SESSIONHASH);
		matcher = regExSessionPattern.matcher(pathInfo);
		if (matcher.find()) {
			String hash[];
			if (!parameters.isEmpty()) {
				hash = parameters.get("HASH");
				out.println("{hash : " + SessionManager.getInstance().createSession(hash[0], "blala") + "}");
			} else {
				throw new MissingParametersException("Missing Userhash");
			}
			return;
		} else {
			if (shash != null) {
				if (!SessionManager.getInstance().isValidSessionHash(shash[0])) {
					throw new WrongHashException("Sessionhash not valid");
				}
			} else {
				throw new MissingParametersException("Invalid URI");
			}

		}
	}
}
