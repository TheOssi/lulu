package com.askit.queries;

import org.apache.commons.lang3.tuple.Pair;

import com.askit.entities.Answer;
import com.askit.entities.Group;
import com.askit.entities.PrivateQuestion;
import com.askit.entities.PublicQuestion;
import com.askit.entities.User;
import com.askit.exception.DatabaseLayerException;

public interface QueryManager {

	/*
	 * Private Methods
	 */

	/**
	 * @param username
	 * @param passwordHash
	 * @return
	 * @throws DatabaseLayerException
	 */
	public boolean checkUser(String username, String passwordHash) throws DatabaseLayerException;

	/*
	 * Add and Create Methods
	 */

	/**
	 * Register a User
	 *
	 * @param user
	 * @throws DatabaseLayerException
	 */
	public void createUser(User user) throws DatabaseLayerException;

	/**
	 * create a new Group
	 *
	 * @param group
	 * @throws DatabaseLayerException
	 */
	public void createNewGroup(Group group) throws DatabaseLayerException;

	/**
	 * add a user to a group
	 *
	 * @param groupID
	 * @param userID
	 * @throws DatabaseLayerException
	 * @throws DatabaseLayerException
	 */
	public void addUserToGroup(long groupID, long userID) throws DatabaseLayerException;

	/**
	 * add a contact to a user
	 *
	 * @param userIDOfUser
	 * @param userIDofContact
	 * @throws DatabaseLayerException
	 */
	public void addContact(long userIDOfUser, long userIDofContact) throws DatabaseLayerException;

	/**
	 * add a user to a oneTimeQuestion
	 *
	 * @param userID
	 * @param questionID
	 * @throws DatabaseLayerException
	 */
	public void addUserToOneTimeQuestion(long userID, long questionID) throws DatabaseLayerException;

	/**
	 * crate a new public question
	 *
	 * @param question
	 * @throws DatabaseLayerException
	 */
	public void createPublicQuestion(PublicQuestion question) throws DatabaseLayerException;

	/**
	 *
	 * create a new question in a group
	 *
	 * @param question
	 * @param groupID
	 * @throws DatabaseLayerException
	 */
	public void createNewPrivateQuestionInGroup(PrivateQuestion question) throws DatabaseLayerException;

	/**
	 * create a new oneTimeQuestion
	 *
	 * @param question
	 * @throws DatabaseLayerException
	 */
	public void createOneTimeQuestion(PrivateQuestion question) throws DatabaseLayerException;

	/**
	 *
	 * @param answer
	 * @throws DatabaseLayerException
	 */
	public void addAnswerToPublicQuestion(Answer answer) throws DatabaseLayerException;

	/**
	 *
	 * @param answer
	 * @throws DatabaseLayerException
	 */
	public void addAnswerToPrivateQuestion(Answer answer) throws DatabaseLayerException;

	/**
	 *
	 * @param questionID
	 * @param userID
	 */
	public void addUserToPublicQuestion(long questionID, long userID) throws DatabaseLayerException;

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
	 * @throws DatabaseLayerException
	 */
	public PublicQuestion[] getPublicQuestions(int startIndex, int quantity, String language) throws DatabaseLayerException;

	/**
	 * Returns a specific PublicQuestion
	 *
	 * @return
	 * @param questionID
	 * @throws DatabaseLayerException
	 */
	public PublicQuestion getPublicQuestion(long questionID) throws DatabaseLayerException;

	/**
	 * return a specific PrivateQuestion
	 *
	 * @param questionID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public PrivateQuestion getPrivateQuestion(long questionID) throws DatabaseLayerException;

	/**
	 * returns all questions of a group within a puffer
	 *
	 * @param groupID
	 * @param startIndex
	 * @param quantity
	 * @return
	 * @throws DatabaseLayerException
	 */
	public PrivateQuestion[] getQuestionsOfGroup(long groupID, int startIndex, int quantity) throws DatabaseLayerException;

	/**
	 * search by username
	 *
	 * @param searchPattern
	 * @return
	 * @throws DatabaseLayerException
	 */
	public User[] getUsersByUsername(String searchPattern) throws DatabaseLayerException;

	/**
	 * returns the username of a user
	 *
	 * @param userID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public String getUsername(long userID) throws DatabaseLayerException;

	/**
	 * returns all users of a PublicQuestion
	 *
	 * @param questionID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public User[] getUsersOfPublicQuestion(long questionID) throws DatabaseLayerException;

	/**
	 * returns all users of a PrivateQuestion
	 *
	 * @param questionID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public User[] getUsersOfPrivateQuestion(long questionID) throws DatabaseLayerException;

	/**
	 *
	 * @param questionID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public User[] getUsersOfAnswerPrivateQuestion(long answerID) throws DatabaseLayerException;

	/**
	 *
	 *
	 * @param questionID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public User[] getUsersOfAnswerPublicQuestion(long answerID) throws DatabaseLayerException;

	/**
	 * returns the users of a group
	 *
	 * @param groupID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public User[] getUsersOfGroup(long groupID) throws DatabaseLayerException;

	/**
	 *
	 * @param userID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public Long getUserScoreOfGlobal(long userID) throws DatabaseLayerException;

	/**
	 *
	 * @param userID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public Long getUserScoreInGroup(long userID, long groupID) throws DatabaseLayerException;

	/**
	 *
	 * @param userID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public String getPhoneNumberHash(long userID) throws DatabaseLayerException;

	/**
	 *
	 * @param questionID
	 * @param userID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public Answer getChoseAnswerInPublicQuestion(long questionID, long userID) throws DatabaseLayerException;

	/**
	 *
	 * @param questionID
	 * @param userID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public Answer getChoseAnswerInPrivateQuestion(long questionID, long userID) throws DatabaseLayerException;

	/**
	 *
	 * @param questionID
	 * @param userID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public Answer getSelectedAnswerInPrivateQuestion(long questionID) throws DatabaseLayerException;

	/**
	 * returns the place in the Ranking of a user in a group
	 *
	 * @param userID
	 * @param groupID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public Long getRankingInGroup(long userID, long groupID) throws DatabaseLayerException;

	/**
	 * returns the passwordHash of a user
	 *
	 * @param userID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public String getPasswordHash(long userID) throws DatabaseLayerException;

	/**
	 * 
	 * @param userID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public String getLanguage(long userID) throws DatabaseLayerException;

	/**
	 * get the profilePictureURI a user
	 *
	 * @param userID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public String getProfilePictureURI(long userID) throws DatabaseLayerException;

	/**
	 * get the groupPicture
	 *
	 * @param groupID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public String getGroupPictureURI(long groupID) throws DatabaseLayerException;

	/**
	 * get the group name
	 *
	 * @param groupID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public String getGroupName(long groupID) throws DatabaseLayerException;

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
	 * @throws DatabaseLayerException
	 */
	public Pair<Answer, Integer>[] getAnswersOfPublicQuestionAndCount(long questionID) throws DatabaseLayerException;

	/**
	 * @param questionID
	 * @return
	 * @throws DatabaseLayerException
	 */
	public Pair<Answer, Integer>[] getAnswersOfPrivateQuestionAndCount(long questionID) throws DatabaseLayerException;

	/**
	 *
	 * @param userID
	 * @param startIndex
	 * @param quantity
	 * @return
	 * @throws DatabaseLayerException
	 */
	public PublicQuestion[] getActivePublicQuestionsOfUser(long userID, int startIndex, int quantity) throws DatabaseLayerException;

	/**
	 *
	 * @param userID
	 * @param startIndex
	 * @param quantity
	 * @return
	 * @throws DatabaseLayerException
	 */
	public PrivateQuestion[] getActivePrivateQuestionsOfUser(long userID, int startIndex, int quantity) throws DatabaseLayerException;

	/**
	 *
	 * @param userID
	 * @param startIndex
	 * @param quantity
	 * @return
	 * @throws DatabaseLayerException
	 */
	public PublicQuestion[] getOldPublicQuestionsOfUser(long userID, int startIndex, int quantity) throws DatabaseLayerException;

	/**
	 *
	 * @param userID
	 * @param startIndex
	 * @param quantity
	 * @return
	 * @throws DatabaseLayerException
	 */
	public PrivateQuestion[] getOldPrivateQuestionsOfUser(long userID, int startIndex, int quantity) throws DatabaseLayerException;

	/**
	 *
	 * @param userID
	 * @return
	 */
	public Pair<Group, Integer>[] getAllGroupScoresAndGlobalScoreOfUser(long userID) throws DatabaseLayerException;;

	/**
	 *
	 * @param groupID
	 * @return
	 */
	public Pair<User, Integer>[] getUsersOfGroupsWithScore(long groupID) throws DatabaseLayerException;;

	/*
	 * Set Methods
	 */

	/**
	 * @param userID
	 * @param newLanguage
	 * @throws DatabaseLayerException
	 */
	public void setLanguage(long userID, String newLanguage) throws DatabaseLayerException;

	/**
	 * @param userID
	 * @param newProfilePictureURI
	 * @throws DatabaseLayerException
	 */
	public void setProfilPictureOfUser(long userID, String newProfilePictureURI) throws DatabaseLayerException;

	/**
	 * @param groupID
	 * @param newGroupPictureURI
	 * @throws DatabaseLayerException
	 */
	public void setGroupPicture(long groupID, String newGroupPictureURI) throws DatabaseLayerException;

	/**
	 * @param userID
	 * @param newPasswordHash
	 * @throws DatabaseLayerException
	 */
	public void setPasswordHash(long userID, String newPasswordHash) throws DatabaseLayerException;

	/**
	 * @param userID
	 * @param questionID
	 * @param answerID
	 * @throws DatabaseLayerException
	 */
	public void setChoosedAnswerOfPrivateQuestion(long userID, long questionID, long answerID) throws DatabaseLayerException;

	/**
	 * @param userID
	 * @param questionID
	 * @param answerID
	 * @throws DatabaseLayerException
	 */
	public void setChoosedAnswerOfPublicQuestion(long userID, long questionID, long answerID) throws DatabaseLayerException;

	/**
	 * @param questionID
	 * @param answerID
	 * @throws DatabaseLayerException
	 */
	public void setSelectedAnswerOfPrivateQuestion(long questionID, long answerID) throws DatabaseLayerException;

	/**
	 * @param groupID
	 * @param newAdmminID
	 * @throws DatabaseLayerException
	 */
	public void setGroupAdmin(long groupID, long newAdmminID) throws DatabaseLayerException;

	/**
	 * @param userID
	 * @param newPhoneNumberHash
	 * @throws DatabaseLayerException
	 */
	public void setPhoneNumberHash(long userID, String newPhoneNumberHash) throws DatabaseLayerException;

	/**
	 * @param groupID
	 * @param newGroupName
	 * @throws DatabaseLayerException
	 */
	public void setGroupName(long groupID, String newGroupName) throws DatabaseLayerException;

	/**
	 * @param userID
	 * @param newUsername
	 * @throws DatabaseLayerException
	 */
	public void setUsername(long userID, String newUsername) throws DatabaseLayerException;

	/*
	 * delete Methods
	 */

	/**
	 * @param questionID
	 * @throws DatabaseLayerException
	 */
	public void deletePrivateQuestion(long questionID) throws DatabaseLayerException;

	/**
	 * @param groupID
	 * @param userID
	 * @throws DatabaseLayerException
	 */
	public void deleteUserFromGroup(long groupID, long userID) throws DatabaseLayerException;

	/**
	 * @param groupID
	 * @throws DatabaseLayerException
	 */
	public void deleteGroup(long groupID) throws DatabaseLayerException;

	/**
	 * @param userID
	 * @param contactID
	 * @throws DatabaseLayerException
	 */
	public void deleteContact(long userID, long contactID) throws DatabaseLayerException;

	/*
	 * search Methods
	 */

	/**
	 * @param userID
	 * @param nameSearchPattern
	 * @return
	 * @throws DatabaseLayerException
	 */
	public Group[] searchForGroup(long userID, String nameSearchPattern) throws DatabaseLayerException;

	/**
	 * @param groupID
	 * @param questionSearchPattern
	 * @return
	 * @throws DatabaseLayerException
	 */
	public PrivateQuestion[] searchForPrivateQuestionInGroup(long groupID, String questionSearchPattern) throws DatabaseLayerException;

	/**
	 * @param nameSearchPattern
	 * @return
	 * @throws DatabaseLayerException
	 */
	public PublicQuestion[] searchForPublicQuestion(String nameSearchPattern) throws DatabaseLayerException;

	/*
	 * other
	 */

	/**
	 * @param questionID
	 * @throws DatabaseLayerException
	 */
	public void setPrivateQuestionToFinish(long questionID) throws DatabaseLayerException;
}
