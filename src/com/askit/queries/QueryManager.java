package com.askit.queries;

import java.sql.SQLException;

import com.askit.entities.Answer;
import com.askit.entities.Group;
import com.askit.entities.PrivateQuestion;
import com.askit.entities.PublicQuestion;
import com.askit.entities.User;
import com.askit.exception.DriverNotFoundException;
import com.askit.exception.ModellToObjectException;

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

	/**
	 * add answer to a question
	 *
	 * @param groupID
	 * @param question
	 */
	public void addAnswerToQuestion(long groupID, String question);

	/*
	 * Get Methods
	 */

	/**
	 * returns public question in a special area of index sort by createDate and
	 * questionID
	 *
	 * @param startIndex
	 *            the startindex; beginn is 0 (excluded)
	 * @param quantity
	 *            how much public Questions should be selected
	 * @param language
	 *            the langauge
	 * @return
	 * @throws DriverNotFoundException
	 * @throws SQLException
	 * @throws ModellToObjectException
	 */
	public PublicQuestion[] getPublicQuestions(int startIndex, int quantity, String language) throws SQLException, DriverNotFoundException,
			ModellToObjectException;

	/**
	 * Returns a specific PublicQuestion
	 *
	 * @return
	 * @param questionID
	 */
	public PublicQuestion getPublicQuesion(long questionID);

	/**
	 * return a specific PrivateQuestion
	 *
	 * @param questionID
	 * @return
	 */
	public PrivateQuestion getPrivateQuestion(long questionID);

	/**
	 * returns all questions of a group within a puffer
	 *
	 * @param groupID
	 * @param startIndex
	 * @param quantity
	 * @return
	 */
	public PrivateQuestion[] getQuestionsOfGroup(long groupID, int startIndex, int quantity);

	/**
	 * search by username
	 *
	 * @param searchPattern
	 * @return
	 */
	public User[] getUsersByUsername(String searchPattern);

	/**
	 * returns the username of a user
	 *
	 * @param userID
	 * @return
	 */
	public String getUsername(long userID);

	/**
	 * returns all users of a PublicQuestion
	 *
	 * @param questionID
	 * @return
	 */
	public User[] getUsersOfPublicQuestion(long questionID);

	/**
	 * returns all users of a PrivateQuestion
	 *
	 * @param questionID
	 * @return
	 */
	public User[] getUsersOfPrivateQuestion(long questionID);

	/**
	 *
	 * @param questionID
	 * @return
	 */
	public User[] getUsersOfAnswerPrivateQuestion(long questionID);

	/**
	 *
	 *
	 * @param questionID
	 * @return
	 */
	public User[] getUsersOfAnswerPublicQuestion(long questionID);

	/**
	 * returns the users of a group
	 *
	 * @param groupID
	 * @return
	 */
	public User[] getUsersOfGroup(long groupID);

	/**
	 *
	 * @param userID
	 * @return
	 */
	public Long getUserScoreOfGlobal(long userID);

	/**
	 *
	 * @param userID
	 * @return
	 */
	public Long getUserScoreInGroup(long userID);

	/**
	 *
	 * @param userID
	 * @return
	 */
	public String getPhoneNumberHash(long userID);

	/**
	 *
	 * @param questionID
	 * @param userID
	 * @return
	 */
	public Answer getSelectedAnswerInPublicQuestion(long questionID, long userID);

	/**
	 *
	 * @param questionID
	 * @param userID
	 * @return
	 */
	public Answer getSelectedAnswerInPrivateQuestion(long questionID, long userID);

	/**
	 *
	 * @param userID
	 * @param groupID
	 * @return
	 */
	public Long getRankingInGroup(long userID, long groupID);

	/**
	 * returns the place in the Ranking of a user in a group
	 *
	 * @param userID
	 * @return
	 */
	public String getPasswordHash(long userID);

	/**
	 * returns the passwordHash of a user
	 *
	 * @param userID
	 * @return
	 */
	public String getLanguage(long userID);

	/**
	 *
	 * @param userID
	 * @return
	 */
	public String getProfilePictureURI(long userID);

	/**
	 * get the profilePictureURI a user
	 *
	 * @param groupID
	 * @return
	 */
	public String getGroupPictureURI(long groupID);

	/**
	 * get the groupPicture
	 *
	 * @param groupID
	 * @param startIndex
	 * @param quantity
	 * @return
	 */
	public PrivateQuestion[] getOldPrivateQuestions(long groupID, int startIndex, int quantity);

	/*
	 * Set Methods
	 */

}
