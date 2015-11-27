package com.askit.face.innerclasses;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import com.askit.exception.DriverNotFoundException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.MissingParametersException;
import com.askit.exception.WrongHashException;
import com.askit.face.SessionManager;
import com.askit.queries.DatabaseQueryManager;

public class GetRequest {
	private final Pattern regExBetPattern = Pattern.compile("/QUESTION/([0-9]*)|/QUESTION");
	private final Pattern regExBetsPattern = Pattern.compile("/QUESTION");

	private final Pattern regExGroupPattern = Pattern.compile("/GROUP/([0-9]*)|/GROUP");
	private final Pattern regExGroupsPattern = Pattern.compile("/GROUPS");

	private final Pattern regExUserPattern = Pattern.compile("/USER/([0-9]*)|/USER");
	private final Pattern regExUsersPattern = Pattern.compile("/USERS");

	private final Pattern regExSessionPattern = Pattern.compile("/SESSION");

	private Integer id;

	public GetRequest(final String pathInfo, final Map<String, String[]> parameters, final PrintWriter out)
			throws ServletException, SQLException, DriverNotFoundException, WrongHashException, DuplicateHashException, MissingParametersException {

		Matcher matcher;

		matcher = regExSessionPattern.matcher(pathInfo);
		if (matcher.find()) {
			String hash[];
			if (!parameters.isEmpty()) {
				hash = parameters.get("HASH");
				SessionManager snm = SessionManager.getInstance();
				out.println(snm.createSession(hash[0], "blala"));
			} else {
				throw new MissingParametersException("Missing Parameters");
			}
			return;
		}

		matcher = regExBetPattern.matcher(pathInfo);
		if (matcher.find()) {
			id = Integer.parseInt(matcher.group(1));
			final DatabaseQueryManager qm = new DatabaseQueryManager();

		}

		matcher = regExBetsPattern.matcher(pathInfo);
		if (matcher.find()) {
			return;
		}

		throw new ServletException("Invalid URI");
	}

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}
}
