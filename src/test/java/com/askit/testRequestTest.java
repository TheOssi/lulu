package com.askit;

import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.MissingParametersException;
import com.askit.exception.WrongHashException;
import com.askit.face.innerclasses.PostRequest;

public class testRequestTest {
	public static void main(String[] args) throws DatabaseLayerException, ServletException {
		Map<String, String[]> parameters = new HashMap<String, String[]>();
		String [] ar = new String[3];
		ar[0] = "1234";
		parameters.put("SESSIONHASH",ar);
		PrintWriter out = null;
		PostRequest post = new PostRequest("sdf", parameters, out);
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
		}
	}
	
}
