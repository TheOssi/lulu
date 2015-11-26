package com.askit.face;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.askit.exception.DriverNotFoundException;
import com.askit.exception.WrongHashException;
import com.askit.queries.DatabaseQueryManager;
import com.askit.queries.QueryManager;

/**
 * Servlet implementation class Faceservlet
 */
@WebServlet("/Faceservlet/*")
public class Faceservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private class GETRequest {
		// Accommodate two requests, one for all resources, another for a
		// specific resource

		private final Pattern regExBetPattern = Pattern.compile("/BET/([0-9]*)|/BET");
		private final Pattern regExBetsPattern = Pattern.compile("/BETS");

		private final Pattern regExGroupPattern = Pattern.compile("/GROUP/([0-9]*)|/GROUP");
		private final Pattern regExGroupsPattern = Pattern.compile("/GROUPS");

		private final Pattern regExUserPattern = Pattern.compile("/USER/([0-9]*)|/USER");
		private final Pattern regExUsersPattern = Pattern.compile("/USERS");

		private final Pattern regExSessionPattern = Pattern.compile("/SESSION");

		private Integer id;

		public GETRequest(final String pathInfo, final Map<String, String[]> parameters) throws ServletException, WrongHashException, SQLException,
				DriverNotFoundException {

			Matcher matcher;

			matcher = regExSessionPattern.matcher(pathInfo);
			if (matcher.find()) {
				String hash[];
				hash = parameters.get("HASH");
				SessionManager.getInstance().createSession(hash[0],"blabla");
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

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Faceservlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Params").append(request.getParameter("BET")
		// + "|" + request.getParameter("ID"));
		final PrintWriter out = response.getWriter();
		final QueryManager qm = new DatabaseQueryManager();

		out.println("GET request handling");
		out.println(request.getPathInfo());
		out.println(request.getParameterMap());
		try {
			final GETRequest resourceValues = new GETRequest(request.getPathInfo(), request.getParameterMap());
			out.println("ID: " + resourceValues.getId());
		} catch (final ServletException e) {
			response.setStatus(400);
			response.resetBuffer();
			e.printStackTrace();
			out.println(e.toString());
		} catch (final WrongHashException e) {
			final JSONBuilder jb = new JSONBuilder();
			out.print(jb.createJSON(e));
		} catch (final DriverNotFoundException e) {
			// TODO
		} catch (final SQLException e) {
			// TODO
		}
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doPut(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doDelete(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}
}
