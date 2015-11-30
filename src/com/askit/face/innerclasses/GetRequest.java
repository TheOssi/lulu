package com.askit.face.innerclasses;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import com.askit.entities.Group;
import com.askit.entities.User;
import com.askit.exception.DriverNotFoundException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.MissingParametersException;
import com.askit.exception.WrongHashException;
import com.askit.face.JSONBuilder;
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
			throws ServletException, SQLException, DriverNotFoundException, WrongHashException, DuplicateHashException,
			MissingParametersException {

		Matcher matcher;

		matcher = regExSessionPattern.matcher(pathInfo);
		if (matcher.find()) {
			String hash[];
			if (!parameters.isEmpty()) {
				hash = parameters.get("HASH");
				out.println("{hash : " + SessionManager.getInstance().createSession(hash[0], "blala") + " }");
			} else {
				throw new MissingParametersException("Missing Parameters");
			}
			return;
		}

		matcher = regExGroupPattern.matcher(pathInfo);
		if (matcher.find()) {
			id = Integer.parseInt(matcher.group(1));
			String shash[] = parameters.get("SESSIONHASH");
			JSONBuilder jb = new JSONBuilder();

			if (SessionManager.getInstance().isValidSessionHash(shash[0])) {
				out.println(jb.createJSON(new Group(new Long(id), Calendar.getInstance().getTime(), new Long(1337),
						"KaiIstGay", "/bla/blubber/fasel")));
			}
			return;
		}

		matcher = regExUsersPattern.matcher(pathInfo);
		if (matcher.find()) {
			// id = Integer.parseInt(matcher.group(1));
			String shash[] = parameters.get("SESSIONHASH");
			JSONBuilder jb = new JSONBuilder();
			User[] users = new User[2];
			users[0] = new User(0L, "ababab", "lololo", "CleanCodingMaster3000", new Date(900, 01, 30),
					"/pfad/zum/Bild", "Kaiisch", 0);
			users[1] = new User(1337L, "hash", "PhoneHash", "DogeIsLoveDogeIsLife", new Date(2071, 01, 30),
					"/pfad/zum/Bild", "Doge", 999999999);
			if (SessionManager.getInstance().isValidSessionHash(shash[0])) {
				out.println(jb.createJSON(users));
			}
			return;
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
