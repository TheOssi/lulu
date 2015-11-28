package com.askit.queries;

import java.sql.SQLException;

import com.askit.entities.Group;
import com.askit.entities.PrivateQuestion;
import com.askit.entities.PublicQuestion;
import com.askit.entities.User;
import com.askit.exception.DriverNotFoundException;

public interface QueryManager {

	/*
	 * Private Methods
	 */

	public boolean checkUser(String username, String passwordHash) throws SQLException, DriverNotFoundException;

	/*
	 * Add and Create Methods
	 */

	/**
	 * Register a User
	 *
	 * @param user
	 */
	public void registerUser(User user);

	/**
	 * create a new Group
	 *
	 * @param group
	 */
	public void createNewGroup(Group group);

	/**
	 * add a user to a group
	 *
	 * @param groupID
	 * @param userID
	 */
	public void addUserToGroup(long groupID, long userID);

	/**
	 * add a contact to a user
	 *
	 * @param userIDOfUser
	 * @param userIDofContact
	 */
	public void addContact(long userIDOfUser, long userIDofContact);

	/**
	 * add a user to a public question
	 *
	 * @param userID
	 * @param questionID
	 */
	public void addUserToQuestion(long userID, long questionID);

	/**
	 * add a user to a oneTimeQuestion
	 *
	 * @param userID
	 * @param questionID
	 */
	public void addUserToOneTimeQuestion(long userID, long questionID);

	/**
	 * crate a new public question
	 *
	 * @param question
	 */
	public void createPublicQuestion(PublicQuestion question);

	/**
	 *
	 * create a new question in a group
	 *
	 * @param question
	 * @param groupID
	 */
	public void createNewQuestionInGroup(PrivateQuestion question, long groupID);

	/**
	 * create a new oneTimeQuestion
	 *
	 * @param question
	 */
	public void createOneTimeQuestion(PrivateQuestion question);

}
