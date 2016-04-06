package com.askit.face.innerclasses;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;

import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DriverNotFoundException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.MissingParametersException;
import com.askit.exception.WrongHashException;

public class PostRequest extends Request {
	public PostRequest(final String pathInfo, final Map<String, String[]> parameters, final PrintWriter out){
		super(pathInfo,parameters,out);
		
	}
	public void handleRequest() throws MissingParametersException, WrongHashException, DuplicateHashException, DatabaseLayerException, ServletException{
		super.handleRequest();
	}
}
