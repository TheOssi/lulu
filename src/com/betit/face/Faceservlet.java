package com.betit.face;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.betit.queries.DatabaseQueryManager;
import com.betit.queries.QueryManager;

/**
 * Servlet implementation class Faceservlet
 */
@WebServlet("/Faceservlet/*")
public class Faceservlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
       
	
	 @SuppressWarnings("unused")
	private class GETRequest {
		    // Accommodate two requests, one for all resources, another for a specific resource
		    
		    private Pattern regExBetPattern = Pattern.compile("/BET/([0-9]*)|/BET");
		    private Pattern regExBetsPattern = Pattern.compile("/BETS");
		   
		    private Pattern regExGroupPattern = Pattern.compile("/GROUP/([0-9]*)|/GROUP");
		    private Pattern regExGroupsPattern = Pattern.compile("/GROUPS");
		    
		    private Pattern regExUserPattern = Pattern.compile("/USER/([0-9]*)|/USER");
		    private Pattern regExUsersPattern = Pattern.compile("/USERS");
		    
		    private Pattern regExSessionPattern = Pattern.compile("/SESSION");
		    
		    
		    private Integer id;
		    
		  
		    public GETRequest(String pathInfo, Map<String,String[]> parameters) throws ServletException {
		      
		      Matcher matcher;
		 
		  
		      matcher = regExSessionPattern.matcher(pathInfo);
		      if (matcher.find()) {
		    	  String hash[];
		    	  hash = parameters.get("HASH");
		    	  SessionManager.getInstance().createSession(hash[0]);
		      }
		      
		      
		      matcher = regExBetPattern.matcher(pathInfo);
		      if (matcher.find()) {
		        id = Integer.parseInt(matcher.group(1));
		        DatabaseQueryManager qm = new DatabaseQueryManager();
		        
		      }
		 
		      matcher = regExBetsPattern.matcher(pathInfo);
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
		 QueryManager qm = new DatabaseQueryManager();
		
		    out.println("GET request handling");
		    out.println(request.getPathInfo());
		    out.println(request.getParameterMap());
		    try {
		      GETRequest resourceValues = new GETRequest(request.getPathInfo(),request.getParameterMap());
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
