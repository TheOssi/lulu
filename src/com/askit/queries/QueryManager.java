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
	 * @throws DriverNotFoundException
	 * @throws SQLException
	 */
	public void registerUser(User user) throws SQLException, DriverNotFoundException;

	/**
	 * create a new Group
	 *
	 * @param group
	 * @throws DriverNotFoundException
	 * @throws SQLException
	 */
	public void createNewGroup(Group group) throws SQLException, DriverNotFoundException;

	/**
	 * add a user to a group
	 *
	 * @param groupID
	 * @param userID
	 * @throws DriverNotFoundException
	 * @throws SQLException
	 */
	public void addUserToGroup(long groupID, long userID) throws SQLException, DriverNotFoundException;

	/**
	 * add a contact to a user
	 *
	 * @param userIDOfUser
	 * @param userIDofContact
	 * @throws DriverNotFoundException
	 * @throws SQLException
	 */
	public void addContact(long userIDOfUser, long userIDofContact) throws SQLException, DriverNotFoundException;

	/**
	 * add a user to a oneTimeQuestion
	 *
	 * @param userID
	 * @param questionID
	 * @throws DriverNotFoundException
	 * @throws SQLException
	 */
	public void addUserToOneTimeQuestion(long userID, long questionID) throws SQLException, DriverNotFoundException;

	/**
	 * crate a new public question
	 *
	 * @param question
	 * @throws DriverNotFoundException
	 * @throws SQLException
	 */
	public void createPublicQuestion(PublicQuestion question) throws SQLException, DriverNotFoundException;

	/**
	 *
	 * create a new question in a group
	 *
	 * @param question
	 * @param groupID
	 * @throws DriverNotFoundException
	 * @throws SQLException
	 */
	public void createNewQuestionInGroup(PrivateQuestion question) throws SQLException, DriverNotFoundException;

	/**
	 * create a new oneTimeQuestion
	 *
	 * @param question
	 * @throws DriverNotFoundException
	 * @throws SQLException
	 */
	public void createOneTimeQuestion(PrivateQuestion question) throws SQLException, DriverNotFoundException;

}
