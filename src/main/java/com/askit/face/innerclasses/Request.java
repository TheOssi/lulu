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

	final Pattern regExQuestionPattern = Pattern.compile("/QUESTION/([0-9]*)|/QUESTION");
	final Pattern regExQuestionsPattern = Pattern.compile("/QUESTION");

	final Pattern regExGroupPattern = Pattern.compile("/GROUP/([0-9]*)|/GROUP");
	final Pattern regExGroupsPattern = Pattern.compile("/GROUPS");

	final Pattern regExAnswerPattern = Pattern.compile("/ANSWER/([0-9]*)|/ANSWER");
	final Pattern regExAnswersPattern = Pattern.compile("/ANSWERS");

	final Pattern regExUserPattern = Pattern.compile("/USER/([0-9]*)|/USER");
	final Pattern regExUserScorePattern = Pattern.compile("/USER/SCORE/([0-9]*)");
	final Pattern regExUsersPattern = Pattern.compile("/USERS");

	final Pattern regExSessionPattern = Pattern.compile("/SESSION");

	Matcher matcher;
	Integer id;

	public Request(final String pathInfo, final Map<String, String[]> parameters, final PrintWriter out) {
		this.pathInfo = pathInfo;
		this.parameters = parameters;
		this.out = out;
	}

	public void handleRequest() throws MissingParametersException, WrongHashException, DuplicateHashException, DatabaseLayerException, ServletException {
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
		}else{
			System.out.println("Bla");
			if (!SessionManager.getInstance().isValidSessionHash(shash[0])) {
				throw new WrongHashException("Sessionhash not valid");
			}
			
		}
	}
}
