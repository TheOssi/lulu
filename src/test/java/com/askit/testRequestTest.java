package com.askit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.MissingParametersException;
import com.askit.exception.WrongHashException;
import com.askit.face.SessionManager;
import com.askit.face.innerclasses.GetRequest;
import com.askit.face.innerclasses.PostRequest;

public class testRequestTest {
	public static void main(String[] args) throws DatabaseLayerException, ServletException {
		Map<String, String[]> parameters = new HashMap<String, String[]>();
		String [] ar = new String[3];
		
		String sessionhash = "1234";
		try {
			sessionhash = SessionManager.getInstance().createSession("1234","bla");
			ar[0] = sessionhash;
		} catch (WrongHashException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DuplicateHashException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		parameters.put("SESSIONHASH",ar);
		PrintWriter out =null;
		
		GetRequest post = new GetRequest("/GROUP/1", parameters, out);
		GetRequest post2 = new GetRequest("/GROUPS", parameters, out);
		ar = new String[1];
		ar[0] = "BLA";		
		parameters.put("GROUPNAME", ar);
		ar = new String[1];
		ar[0] = "123";		
		parameters.put("ADMINID", ar);
		parameters.put("PICTUREURL", ar);
		PostRequest realPost = new PostRequest("/GROUP", parameters, out);
		
		try {
			post.handleRequest();
			post2.handleRequest();
			realPost.handleRequest();
		} catch (MissingParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongHashException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateHashException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
