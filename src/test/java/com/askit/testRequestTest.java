package com.askit;

import java.io.PrintWriter;
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
	public static void main(final String[] args) throws DatabaseLayerException, ServletException {
		final Map<String, String[]> parameters = new HashMap<String, String[]>();
		String[] ar = new String[3];

		String sessionhash = "1234";
		try {
			sessionhash = SessionManager.getInstance().createSession("1234", "bla");
			ar[0] = sessionhash;
		} catch (final WrongHashException e1) {
			e1.printStackTrace();
		} catch (final DuplicateHashException e1) {
			e1.printStackTrace();
		}
		parameters.put("SESSIONHASH", ar);
		final PrintWriter out = null;

		final GetRequest post = new GetRequest("/GROUP/1", parameters, out);
		final GetRequest post2 = new GetRequest("/GROUPS", parameters, out);
		ar = new String[1];
		ar[0] = "BLA";
		parameters.put("GROUPNAME", ar);
		ar = new String[1];
		ar[0] = "123";
		parameters.put("ADMINID", ar);
		parameters.put("PICTUREURL", ar);
		final PostRequest realPost = new PostRequest("/GROUP", parameters, out);

		try {
			post.handleRequest();
			post2.handleRequest();
			realPost.handleRequest();
		} catch (final MissingParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final WrongHashException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final DuplicateHashException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
