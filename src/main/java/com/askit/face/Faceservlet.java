package com.askit.face;

//TODO NumberParsException handling

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.askit.database.DatabaseUser;
import com.askit.database.QuestionEndTimeChecker;
import com.askit.database.QuestionSoonEndTimeChecker;
import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.ExceptionHandler;
import com.askit.exception.MissingParametersException;
import com.askit.exception.NotificationException;
import com.askit.exception.WrongHashException;
import com.askit.face.innerclasses.DeleteRequest;
import com.askit.face.innerclasses.GetRequest;
import com.askit.face.innerclasses.PostRequest;
import com.askit.face.innerclasses.PutRequest;
import com.askit.face.innerclasses.Request;
import com.askit.notification.NotificationHandler;
import com.askit.notification.RegIDHandler;

/**
 * Servlet implementation class Faceservlet
 * 
 * @author Max Lenk
 * @version 1.0.0
 * @since 1.0.0
 *
 * 
 */
@WebServlet("/Face/*")
public class Faceservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig) Init:
	 * Runs all the basic methods at the beginning.
	 */
	@Override
	public void init(final ServletConfig config) {
		try {
			super.init(config);
		} catch (final ServletException e) {
			ExceptionHandler.getInstance().handleError(e);
		}
		DatabaseUser.loadAllPasswordsFromFile();
		ExceptionHandler.getInstance();
		SessionManager.getInstance().start();
		// NotificationSender.getInstace().startThread();
		QuestionEndTimeChecker.getInstance().startThread();
		QuestionSoonEndTimeChecker.getInstance().startThread();
		RegIDHandler.getInstance();
		NotificationHandler.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		final PrintWriter out = getPrintWriter(response);
		final GetRequest resourceValues = new GetRequest(request.getPathInfo(), request.getParameterMap(), out);
		handleRequest(resourceValues, response, out);
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		final PrintWriter out = getPrintWriter(response);
		final String body = getBody(request);
		final PostRequest post = new PostRequest(request.getPathInfo(), request.getParameterMap(), out, body);
		handleRequest(post, response, out);
		out.close();
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPut(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		final PrintWriter out = getPrintWriter(response);
		final PutRequest put = new PutRequest(request.getPathInfo(), request.getParameterMap(), out);
		handleRequest(put, response, out);
		out.close();
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doDelete(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		final PrintWriter out = getPrintWriter(response);
		final DeleteRequest delete = new DeleteRequest(request.getPathInfo(), request.getParameterMap(), out);
		handleRequest(delete, response, out);
		out.close();
	}

	/**
	 * handles various Exceptions and prints stacktrace to log
	 * 
	 * @param exception
	 * @param response
	 * @param out
	 */
	private void handleException(final Exception exception, final HttpServletResponse response, final PrintWriter out) {
		final JSONBuilder jsonBuilder = new JSONBuilder();
		int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		// TODO nicht nach auﬂen geben
		out.print(jsonBuilder.createJSON(exception));
		ExceptionHandler.getInstance().handleError(exception);

		if (exception instanceof DuplicateHashException || exception instanceof WrongHashException) {
			status = HttpServletResponse.SC_UNAUTHORIZED;
		} else if (exception instanceof ServletException || exception instanceof MissingParametersException) {
			status = HttpServletResponse.SC_NOT_FOUND;
		}
		response.setStatus(status);
	}

	/**
	 * @param response
	 * @return Printwriter out
	 * @throws ServerException
	 */
	private PrintWriter getPrintWriter(final HttpServletResponse response) throws IOException {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (final IOException e) {
			ExceptionHandler.getInstance().handleError(e);
			throw new IOException("problem get printwriter", e);
		}
		return out;
	}

	/**
	 * @param request
	 * @param response
	 * @param out
	 */
	private void handleRequest(final Request request, final HttpServletResponse response, final PrintWriter out) {
		try {
			request.handleRequest();
		} catch (final ServletException | MissingParametersException | DatabaseLayerException | WrongHashException | DuplicateHashException
				| NotificationException e) {
			handleException(e, response, out);
		}
	}

	/**
	 * String get Body reads Body of request into one String
	 *
	 * @param request
	 * @return String body
	 */
	private String getBody(final HttpServletRequest request) {
		String body = "";
		try {
			final StringBuilder stringBuilder = new StringBuilder();
			final BufferedReader bodyBufferedreader = request.getReader();
			String line = "";
			while ((line = bodyBufferedreader.readLine()) != null) {
				stringBuilder.append(line);
			}
			bodyBufferedreader.close();
			body = stringBuilder.toString();
		} catch (final IOException e) {
			ExceptionHandler.getInstance().handleError(e);
		}
		return body;
	}
}