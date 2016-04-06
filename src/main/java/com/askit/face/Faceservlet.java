package com.askit.face;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DriverNotFoundException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.MissingParametersException;
import com.askit.exception.ModellToObjectException;
import com.askit.exception.WrongHashException;
import com.askit.face.innerclasses.GetRequest;
import com.askit.face.innerclasses.PostRequest;
import com.askit.queries.DatabaseQueryManager;
import com.askit.queries.QueryManager;

/**
 * Servlet implementation class Faceservlet
 */
@WebServlet("/Face/*")
public class Faceservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	

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
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Params").append(request.getParameter("BET")
		// + "|" + request.getParameter("ID"));
		final PrintWriter out = response.getWriter();
		final QueryManager qm = new DatabaseQueryManager();
		

//		out.println("GET request handling");
//		out.println(request.getPathInfo());
//		out.println(request.getParameterMap());
		try {
			final GetRequest resourceValues = new GetRequest(request.getPathInfo(), request.getParameterMap(), out);
			resourceValues.handleRequest();
			
		} catch (final ServletException e) {
			JSONBuilder jb = new JSONBuilder();
			out.print(jb.createJSON(e));
		// TODO
	
		}
		catch(final MissingParametersException e){
			JSONBuilder jb = new JSONBuilder();
			out.print(jb.createJSON(e));
		}
	
		catch(final DatabaseLayerException e){
			
		} catch (WrongHashException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateHashException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final PostRequest post = new PostRequest(request.getPathInfo(), request.getParameterMap(), out);
		try {
			post.handleRequest();
		} catch (MissingParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongHashException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateHashException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.close();
	}

	@Override
	protected void doPut(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().print("Put");
	}

	@Override
	protected void doDelete(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}
}
