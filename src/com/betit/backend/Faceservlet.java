package com.betit.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Faceservlet
 */
@WebServlet("/Faceservlet/*")
public class Faceservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	 @SuppressWarnings("unused")
	private class GETRequest {
		    // Accommodate two requests, one for all resources, another for a specific resource
		    private Pattern regExAllPattern = Pattern.compile("/resource");
		    private Pattern regExIdPattern = Pattern.compile("/resource/([0-9]*)");
		    private Pattern regExBetPattern = Pattern.compile("/BET/([0-9]*)|/BET");
		    
		    private Integer id;
		 
		    public GETRequest(String pathInfo) throws ServletException {
		      // regex parse pathInfo
		      Matcher matcher;
		 
		      // Check for ID case first, since the All pattern would also match
		      matcher = regExBetPattern.matcher(pathInfo);
		      if (matcher.find()) {
		        id = Integer.parseInt(matcher.group(1));
		        return;
		      }
		 
		      matcher = regExAllPattern.matcher(pathInfo);
		      if (matcher.find()) return;
		      
		      throw new ServletException("Invalid URI");
		    }
		 
		    public Integer getId() {
		      return id;
		    }
		 
		    public void setId(Integer id) {
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Params").append(request.getParameter("BET") + "|" + request.getParameter("ID"));
		 PrintWriter out = response.getWriter();
		 
		    out.println("GET request handling");
		    out.println(request.getPathInfo());
		    out.println(request.getParameterMap());
		    try {
		      GETRequest resourceValues = new GETRequest(request.getPathInfo());
		      out.println("ID: " + resourceValues.getId());
		    } catch (ServletException e) {
		      response.setStatus(400);
		      response.resetBuffer();
		      e.printStackTrace();
		      out.println(e.toString());
		    }
		    out.close();
		  }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
	protected void doPut (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	protected void doDelete (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
}
