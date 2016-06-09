package com.askit.face.innerclasses;

import java.io.PrintWriter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.MissingParametersException;
import com.askit.exception.NotificationException;
import com.askit.exception.WrongHashException;
import com.askit.face.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class Request {
	Integer id;
	Matcher matcher;
	String pathInfo;
	PrintWriter out;
	Map<String, String[]> parameters;
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	final static Pattern regExQuestionPattern = Pattern.compile("/QUESTION/([0-9]+)$|/QUESTION$");
	final static Pattern regExQuestionsPattern = Pattern.compile("/QUESTIONS");

	final static Pattern regExGroupPattern = Pattern.compile("/GROUP/([0-9]+)$|/GROUP$");
	final static Pattern regExGroupsPattern = Pattern.compile("/GROUPS");

	final static Pattern regExAnswerPattern = Pattern.compile("/ANSWER/([0-9]+)$|/ANSWER$");
	final static Pattern regExAnswersPattern = Pattern.compile("/ANSWERS");

	final static Pattern regExUserPattern = Pattern.compile("/USER/([0-9]+)$|/USER$");
	final static Pattern regExUserScorePattern = Pattern.compile("/USER/SCORE/([0-9]+)");
	final static Pattern regExUsersPattern = Pattern.compile("/USERS");

	final static Pattern regExSessionPattern = Pattern.compile("/SESSION");
	final static Pattern regExGCMPattern = Pattern.compile("/GCM");

	final static Pattern regExPicturePattern = Pattern.compile("/PICTURE");

	/**
	 * @param pathInfo
	 * @param parameters
	 * @param out
	 */
	public Request(final String pathInfo, final Map<String, String[]> parameters, final PrintWriter out) {
		this.pathInfo = pathInfo;
		this.parameters = parameters;
		this.out = out;
	}

	/**
	 * Method for general request processing, checks for valid session and
	 * handles request for sessions
	 * 
	 * @throws MissingParametersException
	 * @throws WrongHashException
	 * @throws DuplicateHashException
	 * @throws DatabaseLayerException
	 * @throws ServletException
	 * @throws NotificationException
	 */
	public void handleRequest() throws MissingParametersException, WrongHashException, DuplicateHashException, DatabaseLayerException,
			ServletException, NotificationException {
		String sessionHash = null;
		boolean root = false;

		if (parameters.containsKey(URLConstants.PARAMETERS_SESSIONHASH)) {
			sessionHash = parameters.get(URLConstants.PARAMETERS_SESSIONHASH)[0];
		}
		if (parameters.containsKey(URLConstants.PARAMETERS_ROOT)) {
			root = Boolean.parseBoolean(parameters.get(URLConstants.PARAMETERS_ROOT)[0]);
		}
		if (pathInfo.equals(null) || pathInfo.equals("")) {
			throw new ServletException("Malformed URL");
		}

		matcher = regExSessionPattern.matcher(pathInfo);
		if (matcher.find()) {
			if (parameters.containsKey(URLConstants.PARAMETERS_PASSWORDHASH) && root == false) {
				final String passwordHash = parameters.get(URLConstants.PARAMETERS_PASSWORDHASH)[0];
				// TODO get this from a url parameter
				final String username = "blala";
				final String generatedSessionHash = SessionManager.getInstance().createSession(username, passwordHash);
				out.println(buildOutputJSON(generatedSessionHash));
			} else if (parameters.containsKey(URLConstants.PARAMETERS_PASSWORDHASH) && root == true) {
				out.println(SessionManager.getInstance().getSessionStats());
			} else {
				throw new MissingParametersException("Missing PasswordHash");
			}
			return;
		} else {
			if (sessionHash != null) {
				if (!SessionManager.getInstance().isValidSessionHash(sessionHash)) {
					throw new WrongHashException("Sessionhash not valid");
				}
			} else {
				throw new MissingParametersException("Missing SessionHash");
			}
		}
	}

	private String buildOutputJSON(final String sessionHash) {
		final JsonObject json = new JsonObject();
		json.addProperty("hash", sessionHash);
		return gson.toJson(json);
	}
}