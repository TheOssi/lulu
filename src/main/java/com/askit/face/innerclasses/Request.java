package com.askit.face.innerclasses;

import java.io.PrintWriter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import com.askit.etc.Constants;
import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.MissingParametersException;
import com.askit.exception.WrongHashException;
import com.askit.face.SessionManager;

public class Request {
	String pathInfo;
	Map<String, String[]> parameters;
	PrintWriter out;

	// TODO maybe seperate pattern/matcher class
	final static Pattern regExQuestionPattern = Pattern.compile("/QUESTION/([0-9]*)$|/QUESTION$");
	final static Pattern regExQuestionsPattern = Pattern.compile("/QUESTION");

	final static Pattern regExGroupPattern = Pattern.compile("/GROUP/([0-9]*)$|/GROUP$");
	final static Pattern regExGroupsPattern = Pattern.compile("/GROUPS");

	final static Pattern regExAnswerPattern = Pattern.compile("/ANSWER/([0-9]*)$|/ANSWER$");
	final static Pattern regExAnswersPattern = Pattern.compile("/ANSWERS");

	final static Pattern regExUserPattern = Pattern.compile("/USER/([0-9]*)$|/USER$");
	final static Pattern regExUserScorePattern = Pattern.compile("/USER/SCORE/([0-9]*)");
	final static Pattern regExUsersPattern = Pattern.compile("/USERS");

	// TODO why not static?
	final Pattern regExSessionPattern = Pattern.compile("/SESSION");

	Matcher matcher;
	Integer id;

	public Request(final String pathInfo, final Map<String, String[]> parameters, final PrintWriter out) {
		this.pathInfo = pathInfo;
		this.parameters = parameters;
		this.out = out;
	}

	/*
	 * Method for general request processing, checks for valid session and
	 * handles request for sessionhashes
	 */
	public void handleRequest() throws MissingParametersException, WrongHashException, DuplicateHashException, DatabaseLayerException,
			ServletException {
		final String sessionHash = parameters.get(Constants.PARAMETERS_SESSIONHASH)[0];
		matcher = regExSessionPattern.matcher(pathInfo);
		// TODO maybe no if in a if?
		if (matcher.find()) {
			if (!parameters.isEmpty()) {
				// TODO HASH in constant
				final String passwordHash = parameters.get("HASH")[0];
				// TODO blala?
				// TODO using gson
				out.println("{hash : " + SessionManager.getInstance().createSession(passwordHash, "blala") + "}");
			} else {
				throw new MissingParametersException("Missing PasswordHash");
			}
			return; // TODO necessary?
		} else {
			if (sessionHash != null) {
				if (!SessionManager.getInstance().isValidSessionHash(sessionHash)) {
					throw new WrongHashException("Sessionhash not valid");
				}
			} else {
				throw new MissingParametersException("No SessionHash");
			}
		}
	}
}