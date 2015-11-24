package com.betit.face;

import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import com.betit.exception.DriverNotFoundException;
import com.betit.exception.WrongHashException;
import com.betit.queries.DatabaseQueryManager;

public class GetRequest {
	private Pattern regExBetPattern = Pattern.compile("/BET/([0-9]*)|/BET");
    private Pattern regExBetsPattern = Pattern.compile("/BETS");
   
    private Pattern regExGroupPattern = Pattern.compile("/GROUP/([0-9]*)|/GROUP");
    private Pattern regExGroupsPattern = Pattern.compile("/GROUPS");
    
    private Pattern regExUserPattern = Pattern.compile("/USER/([0-9]*)|/USER");
    private Pattern regExUsersPattern = Pattern.compile("/USERS");
    
    private Pattern regExSessionPattern = Pattern.compile("/SESSION");
    
    
    private Integer id;
    
  
    public GetRequest(String pathInfo, Map<String,String[]> parameters) throws ServletException,SQLException, DriverNotFoundException, WrongHashException {
      
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
