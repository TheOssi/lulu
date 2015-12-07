package com.askit.queries;

import java.sql.SQLException;

import org.apache.commons.lang3.tuple.Pair;

import com.askit.entities.Answer;
import com.askit.entities.Group;
import com.askit.entities.PrivateQuestion;
import com.askit.entities.PublicQuestion;
import com.askit.entities.User;
import com.askit.exception.DriverNotFoundException;
import com.askit.exception.ModellToObjectException;

public interface QueryManager {

	// TODO question abbrechen

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
	 *
	 * @param answer
	 * @throws DriverNotFoundException
	 * @throws SQLException
	 */
	public void addAnswerToPublicQuestion(Answer answer) throws SQLException, DriverNotFoundException;

	/**
	 *
	 * @param answer
	 * @throws DriverNotFoundException
	 * @throws SQLException
	 */
	public void addAnswerToPrivateQuestion(Answer answer) throws SQLException, DriverNotFoundException;

	/**
	 *
	 * @param questionID
	 * @param userID
	 */
	public void addUserToPublicQuestion(long questionID, long userID);

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
	 * @throws ModellToObjectException
	 * @throws SQLException
	 * @throws DriverNotFoundException
	 */
	public PublicQuestion getPublicQuesion(long questionID) throws ModellToObjectException, SQLException, DriverNotFoundException;

	/**
	 * return a specific PrivateQuestion
	 *
	 * @param questionID
	 * @return
	 * @throws DriverNotFoundException
	 * @throws SQLException
	 * @throws ModellToObjectException
	 */
	public PrivateQuestion getPrivateQuestion(long questionID) throws ModellToObjectException, SQLException, DriverNotFoundException;

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
	 * @throws DriverNotFoundException
	 * @throws SQLException
	 * @throws ModellToObjectException
	 */
	public User[] getUsersByUsername(String searchPattern) throws ModellToObjectException, SQLException, DriverNotFoundException;

	/**
	 * returns the username of a user
	 *
	 * @param userID
	 * @return
	 * @throws DriverNotFoundException
	 * @throws SQLException
	 */
	public String getUsername(long userID) throws SQLException, DriverNotFoundException;

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
	public User[] getUsersOfAnswerPrivateQuestion(long questionID, long answerID);

	/**
	 *
	 *
	 * @param questionID
	 * @return
	 */
	public User[] getUsersOfAnswerPublicQuestion(long questionID, long answerID);

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
	 * @throws DriverNotFoundException
	 * @throws SQLException
	 */
	public Long getUserScoreOfGlobal(long userID) throws SQLException, DriverNotFoundException;

	/**
	 *
	 * @param userID
	 * @return
	 */
	public Long getUserScoreInGroup(long userID, long groupID);

	/**
	 *
	 * @param userID
	 * @return
	 * @throws DriverNotFoundException
	 * @throws SQLException
	 */
	public String getPhoneNumberHash(long userID) throws SQLException, DriverNotFoundException;

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
	 * returns the place in the Ranking of a user in a group
	 *
	 * @param userID
	 * @param groupID
	 * @return
	 */
	public Long getRankingInGroup(long userID, long groupID);

	/**
	 * returns the passwordHash of a user
	 *
	 * @param userID
	 * @return
	 * @throws DriverNotFoundException
	 * @throws SQLException
	 */
	public String getPasswordHash(long userID) throws SQLException, DriverNotFoundException;

	/**
	 *
	 *
	 * @param userID
	 * @return
	 * @throws DriverNotFoundException
	 * @throws SQLException
	 *
	 */
	public String getLanguage(long userID) throws SQLException, DriverNotFoundException;

	/**
	 * get the profilePictureURI a user
	 *
	 * @param userID
	 * @return
	 * @throws DriverNotFoundException
	 * @throws SQLException
	 */
	public String getProfilePictureURI(long userID) throws SQLException, DriverNotFoundException;

	/**
	 * get the groupPicture
	 *
	 * @param groupID
	 * @return
	 */
	public String getGroupPictureURI(long groupID);

	/**
	 *
	 *
	 * @param groupID
	 * @param startIndex
	 * @param quantity
	 * @return
	 */
	public PrivateQuestion[] getOldPrivateQuestions(long groupID, int startIndex, int quantity);

	/**
	 *
	 * @param questionID
	 * @return
	 */
	public Answer[] getAnswersOfPublicQuestionAndCount(long questionID);

	/**
	 *
	 * @param questionID
	 * @return
	 */
	public Answer[] getAnswersOfPrivateQuestionAndCount(long questionID);

	/**
	 *
	 * @param userID
	 * @param startIndex
	 * @param quantity
	 * @return
	 */
	public PublicQuestion[] getActivePublicQuestionsOfUser(long userID, int startIndex, int quantity);

	/**
	 *
	 * @param userID
	 * @param startIndex
	 * @param quantity
	 * @return
	 */
	public PrivateQuestion[] getActivePrivateQuestionsOfUser(long userID, int startIndex, int quantity);

	/**
	 *
	 * @param userID
	 * @param startIndex
	 * @param quantity
	 * @return
	 */
	public PublicQuestion[] getOldPublicQuestionsOfUser(long userID, int startIndex, int quantity);

	/**
	 *
	 * @param userID
	 * @param startIndex
	 * @param quantity
	 * @return
	 */
	public PrivateQuestion[] getOldPrivateQuestionsOfUser(long userID, int startIndex, int quantity);

	/**
	 *
	 * @param userID
	 * @return
	 */
	public Pair<Group, Integer>[] getAllGroupScoresAndGlobalScoreOfUser(long userID);

	/**
	 *
	 * @param groupID
	 * @return
	 */
	public Pair<User, Integer>[] getUsersOfGroupsWithScore(long groupID);

	/*
	 * Set Methods
	 */

	public void setLanguage(long userID, String newLanguage) throws SQLException, DriverNotFoundException;

	public void setProfilPictureOfUser(long userID, String newProfilePictureURI) throws SQLException, DriverNotFoundException;

	public void setGroupPicture(long groupID, String newGroupPictureURI) throws SQLException, DriverNotFoundException;

	public void setPasswordHash(long userID, String newPasswordHash) throws SQLException, DriverNotFoundException;

	public void setSelectedAnswerOfPublicQuestion(long userID, long questionID, long answerID);

	public void setSelectedAnswerOfPrivateQuestion(long userID, long questionID, long answerID);

	public void setSelectedAnswerOfPrivateQuestion(long questionID, long answerID);

	public void setGroupAdmin(long groupID, long newAdmminID);

	public void setPhoneNumberHash(long userID, String newPhoneNumberHash) throws SQLException, DriverNotFoundException;

	public void setGroupName(long userID, String newGroupName);

	public void setUsername(long userID, String newUsername) throws SQLException, DriverNotFoundException;

	/*
	 * delete Methods
	 */

	public void deletePrivateQuestion(long questionID);

	public void deleteUserFromGroup(long groupID, long userID);

	public void deleteGroup(long groupID);

	public void deleteContact(long userID, long contactID);

	/*
	 * search Methods
	 */

	public Group[] searchForGroup(long userID, String nameSearchPattern);

	public PrivateQuestion[] searchForPrivateQuestionInGroup(long groupID, String groupnameSearchPattern);

	public PublicQuestion[] searchForPublicQuestion(String nameSearchPattern);

	/*
	 * other
	 */

	public void finishPrivateQuestion(long questionID);

}
