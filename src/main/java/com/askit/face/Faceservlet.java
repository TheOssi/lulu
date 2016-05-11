package com.askit.face;

//TODO add trigger calls
//TODO add notification calls

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DuplicateHashException;
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
 * @author lelmac
 * @version 1.0.0.0
 * @since 1.0.0.0
 *
 *        Servlet implementation class Faceservlet
 */
@WebServlet("/Face/*")
public class Faceservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Faceservlet() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig) Init:
	 * Runs all the basic methods at the beginning.
	 */
	@Override
	public void init(final ServletConfig config) {
		try {
			super.init();
		} catch (final ServletException e) {
			e.printStackTrace();
		}
		SessionManager.getInstance().start();
		//NotificationSender.getInstace().startThread();
		RegIDHandler.getInstance();
		NotificationHandler.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) {

		final PrintWriter out = getPrintWriterSlienty(response);

		final GetRequest resourceValues = new GetRequest(request.getPathInfo(), request.getParameterMap(), out);
		handleRequest(resourceValues, response, out);
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) {
		final PrintWriter out = getPrintWriterSlienty(response);
		final PostRequest post = new PostRequest(request.getPathInfo(), request.getParameterMap(), out);
		handleRequest(post, response, out);
		out.close();
	}

	@Override
	protected void doPut(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final PrintWriter out = getPrintWriterSlienty(response);
		final PutRequest put = new PutRequest(request.getPathInfo(), request.getParameterMap(), out);
		handleRequest(put, response, out);
		out.close();
	}

	@Override
	protected void doDelete(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final PrintWriter out = getPrintWriterSlienty(response);
		final DeleteRequest delete = new DeleteRequest(request.getPathInfo(), request.getParameterMap(), out);
		handleRequest(delete, response, out);
		out.close();

	}

	/*
	 * handles various Exceptions and prints stacktrace to log
	 */
	private void handleException(final Exception exception, final HttpServletResponse response, final PrintWriter out) {
		final JSONBuilder jsonBuilder = new JSONBuilder();
		int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		// TODO nicht nach auﬂen geben
		out.print(jsonBuilder.createJSON(exception));
		exception.printStackTrace();

		if (exception instanceof DuplicateHashException || exception instanceof WrongHashException) {
			status = HttpServletResponse.SC_UNAUTHORIZED;
		} else if (exception instanceof ServletException || exception instanceof MissingParametersException) {
			status = HttpServletResponse.SC_NOT_FOUND;
		}
		response.setStatus(status);
	}

	private PrintWriter getPrintWriterSlienty(final HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return out;
	}

	private void handleRequest(final Request request, final HttpServletResponse response, final PrintWriter out) {
		try {
			request.handleRequest();
		} catch (final ServletException | MissingParametersException | DatabaseLayerException | WrongHashException
				| DuplicateHashException | NotificationException e) {
			handleException(e, response, out);
		}
	}
}