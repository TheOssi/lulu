package com.askit.face.innerclasses;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;

import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DuplicateHashException;
import com.askit.exception.MissingParametersException;
import com.askit.exception.WrongHashException;
import com.askit.queries.DatabaseQueryManager;
import com.askit.queries.QueryManager;

public class PutRequest extends Request{

	public PutRequest(String pathInfo, Map<String, String[]> parameters, PrintWriter out) {
		super(pathInfo, parameters, out);
		
	}
	
	public void handleRequest() throws MissingParametersException, WrongHashException, DuplicateHashException, DatabaseLayerException, ServletException{
		super.handleRequest();
		final QueryManager queryManager = new DatabaseQueryManager();
		Long groupID = null;
		Long questionID = null;
		Long userID = null;
		boolean isPublic = false;
		String language = null;
		Long answerID = null;
		String userName = null;
		String passwordHash = null;
		String phoneNumberHash = null;
		Long adminID = null;
		String groupName = null;
		String pictureUrl = null;
		
		
		queryManager.setChoosedAnswerOfPrivateQuestion(userID, questionID, answerID);
		queryManager.setChoosedAnswerOfPublicQuestion(userID, questionID, answerID);
		queryManager.setGroupAdmin(groupID, adminID);
		queryManager.setGroupName(groupID, groupName);
		queryManager.setGroupPicture(groupID, pictureUrl);
		queryManager.setLanguage(userID, language);
		queryManager.setPasswordHash(userID, passwordHash);
		queryManager.setPhoneNumberHash(userID, phoneNumberHash);
		queryManager.setProfilPictureOfUser(userID, pictureUrl );
		queryManager.setSelectedAnswerOfPrivateQuestion(questionID, answerID);
		queryManager.setUsername(userID,userName);
		queryManager.getSelectedAnswerInPrivateQuestion(questionID);
	}
}
