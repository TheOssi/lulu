package com.askit.face.innerclasses;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import com.askit.entities.Answer;
import com.askit.entities.Group;
import com.askit.entities.PrivateQuestion;
import com.askit.entities.PublicQuestion;
import com.askit.entities.User;
import com.askit.exception.DriverNotFoundException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.MissingParametersException;
import com.askit.exception.ModellToObjectException;
import com.askit.exception.WrongHashException;
import com.askit.face.JSONBuilder;
import com.askit.face.SessionManager;
import com.askit.queries.DatabaseQueryManager;
import com.askit.queries.QueryManager;

public class GetRequest {
	private final Pattern regExBetPattern = Pattern.compile("/QUESTION/([0-9]*)|/QUESTION");
	private final Pattern regExBetsPattern = Pattern.compile("/QUESTION");

	private final Pattern regExGroupPattern = Pattern.compile("/GROUP/([0-9]*)|/GROUP");
	private final Pattern regExGroupsPattern = Pattern.compile("/GROUPS");

	private final Pattern regExUserPattern = Pattern.compile("/USER/([0-9]*)|/USER");
	private final Pattern regExUserScorePattern = Pattern.compile("/USER/SCORE/([0-9]*)");
	private final Pattern regExUsersPattern = Pattern.compile("/USERS");

	private final Pattern regExSessionPattern = Pattern.compile("/SESSION");

	private Integer id;

	@SuppressWarnings("unused")
	public GetRequest(final String pathInfo, final Map<String, String[]> parameters, final PrintWriter out)
			throws ServletException, SQLException, DriverNotFoundException, WrongHashException, DuplicateHashException,
			MissingParametersException {

		Matcher matcher;
		QueryManager qm = new DatabaseQueryManager();
		String shash[] = parameters.get("SESSIONHASH");
		JSONBuilder jb = new JSONBuilder();
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

			matcher = regExGroupPattern.matcher(pathInfo);
			if (matcher.find()) {
				id = Integer.parseInt(matcher.group(1));

				out.println(jb.createJSON(new Group(new Long(id), Calendar.getInstance().getTime(), new Long(1337),
						"KaiIstGay", "/bla/blubber/fasel")));

				return;
			}

			// /USER/ID returns Username
			matcher = regExUserPattern.matcher(pathInfo);
			if (matcher.find()) {
				id = Integer.parseInt(matcher.group(1));
				if (id != null) {
					String username = qm.getUsername(id);
					out.println("{username : " + id + "}");
				} else {
					throw new MissingParametersException("Missing ID in Parameters");
				}
				return;

			}

			// /USER/SCORE/ID + GROUPID=ID Pattern returns Global or Group Score
			matcher = regExUserScorePattern.matcher(pathInfo);
			if (matcher.find()) {
				Long groupID = Long.parseLong(parameters.get("GROUPID")[0]);
				Long userscore;
				id = Integer.parseInt(matcher.group(1));
				if (id != null) {
					if (groupID != null) {
						userscore = qm.getUserScoreInGroup(id, groupID);
					} else {
						userscore = qm.getUserScoreOfGlobal(id);
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
				String groupID = parameters.get("GROUPID")[0];
				String searchPattern = parameters.get("SEARCH")[0];
				Long questionID = Long.parseLong(parameters.get("QUESTIONID")[0]);
				boolean isPublic = Boolean.parseBoolean(parameters.get("PUBLIC")[0]);
				User[] users = null;
				if (!groupID.isEmpty() || !searchPattern.isEmpty() || questionID != null) {
					if (!groupID.isEmpty()) {
						users = qm.getUsersOfGroup(Long.parseLong(groupID));
					} else if (!searchPattern.isEmpty()) {
						qm.getUsersByUsername(searchPattern);
					} else if (questionID != null && isPublic) {
						qm.getUsersOfAnswerPublicQuestion(questionID);
					} else if (questionID != null) {
						qm.getUsersOfPrivateQuestion(questionID);
					}
					
					out.println(jb.createJSON(users));
				} else {
					throw new MissingParametersException("No Parameters specified.");
				}
				return;
			}

			matcher = regExBetsPattern.matcher(pathInfo);
			if (matcher.find()) {
				return;
			}

			throw new ServletException("Invalid URI");
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}
}
