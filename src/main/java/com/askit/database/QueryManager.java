package com.askit.database;

import org.apache.commons.lang3.tuple.Pair;

import com.askit.entities.Answer;
import com.askit.entities.Group;
import com.askit.entities.PrivateQuestion;
import com.askit.entities.PublicQuestion;
import com.askit.entities.User;
import com.askit.exception.DatabaseLayerException;

/**
 * All read, write, alter and delete methods that are allowed on the database
 * are defined in this interface.
 * 
 * @author Kai Müller
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public interface QueryManager {

	/**
	 * Checks if the combination of username and passwordHash is present in the
	 * database.
	 * 
	 * @param user
	 *            the username the username of the user
	 * @param passwordHash
	 *            the passwordhash of the user
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public boolean checkUser(String username, String passwordHash) throws DatabaseLayerException;

	// ================================================================================
	// CREATE METHODS
	// ================================================================================

	/**
	 * adds a user to the database
	 *
	 * @param user
	 *            the user to add
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void createUser(User user) throws DatabaseLayerException;

	/**
	 * create a new Group
	 *
	 * @param user
	 *            the group to add
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void createNewGroup(Group group) throws DatabaseLayerException;

	/**
	 * crate a new public question
	 *
	 * @param question
	 *            the question to create
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void createPublicQuestion(PublicQuestion question) throws DatabaseLayerException;

	/**
	 *
	 * create a new question in a group
	 *
	 * @param question
	 *            the question to create
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void createNewPrivateQuestionInGroup(PrivateQuestion question) throws DatabaseLayerException;

	/**
	 * create a new oneTimeQuestion
	 *
	 * @param question
	 *            the question to create
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void createOneTimeQuestion(PrivateQuestion question) throws DatabaseLayerException;

	// ================================================================================
	// ADD METHODS
	// ================================================================================

	/**
	 * add a user to a group
	 *
	 * @param groupID
	 *            the groupID of the group
	 * @param userID
	 *            the id of the user
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void addUserToGroup(long groupID, long userID) throws DatabaseLayerException;

	/**
	 * add a contact to a user
	 *
	 * @param userIDOfUser
	 *            the id of the user
	 * @param userIDofContact
	 *            the id of the contact to ad
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void addContact(long userIDOfUser, long userIDofContact) throws DatabaseLayerException;

	/**
	 * add a user to a oneTimeQuestion
	 *
	 * @param userID
	 *            the id of the user
	 * @param questionID
	 *            the questionID
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void addUserToOneTimeQuestion(long userID, long questionID) throws DatabaseLayerException;

	/**
	 * add answer to a public question
	 *
	 * @param answer
	 *            the answer to add
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void addAnswerToPublicQuestion(Answer answer) throws DatabaseLayerException;

	/**
	 * add a answer to a private question
	 * 
	 * @param answer
	 *            the answer to add
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void addAnswerToPrivateQuestion(Answer answer) throws DatabaseLayerException;

	/**
	 * add a user to a public question
	 *
	 * @param question
	 *            the questionID
	 * @param user
	 *            the id of the user
	 */
	public void addUserToPublicQuestion(long questionID, long userID) throws DatabaseLayerException;

	// ================================================================================
	// GET METHODS
	// ================================================================================

	/**
	 * returns public question in a special area of index sort by createDate and
	 * questionID
	 *
	 * @param startIndex
	 *            the startindex; begin is 0 (excluded)
	 * @param quantity
	 *            how much public Questions should be selected
	 * @param language
	 *            the langauge
	 * @return a list of public questions
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PublicQuestion[] getPublicQuestions(int startIndex, int quantity, String language) throws DatabaseLayerException;

	/**
	 * Returns a specific PublicQuestion
	 *
	 * @return
	 * @param question
	 *            the questionID
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PublicQuestion getPublicQuestion(long questionID) throws DatabaseLayerException;

	/**
	 * return a specific PrivateQuestion
	 *
	 * @param question
	 *            the questionID
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PrivateQuestion getPrivateQuestion(long questionID) throws DatabaseLayerException;

	/**
	 * returns all questions of a group within a puffer
	 *
	 * @param user
	 *            the groupID
	 * @param startIndex
	 * @param quantity
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PrivateQuestion[] getQuestionsOfGroup(long groupID, int startIndex, int quantity) throws DatabaseLayerException;

	/**
	 * search by username
	 *
	 * @param searchPattern
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public User[] searchUsersByUsername(String searchPattern) throws DatabaseLayerException;

	/**
	 * returns the username of a user
	 *
	 * @param user
	 *            the userID the id of the user
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public String getUsername(long userID) throws DatabaseLayerException;

	/**
	 * returns all users of a PublicQuestion
	 *
	 * @param question
	 *            the questionID
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public User[] getUsersOfPublicQuestion(long questionID) throws DatabaseLayerException;

	/**
	 * returns all users of a PrivateQuestion
	 *
	 * @param question
	 *            the questionID
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public User[] getUsersOfPrivateQuestion(long questionID) throws DatabaseLayerException;

	/**
	 *
	 * @param question
	 *            the questionID
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public User[] getUsersOfAnswerPrivateQuestion(long answerID) throws DatabaseLayerException;

	/**
	 *
	 *
	 * @param question
	 *            the questionID
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public User[] getUsersOfAnswerPublicQuestion(long answerID) throws DatabaseLayerException;

	/**
	 * returns the users of a group
	 *
	 * @param user
	 *            the groupID
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public User[] getUsersOfGroup(long groupID) throws DatabaseLayerException;

	/**
	 *
	 * @param user
	 *            the userID the id of the user
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Long getUserScoreOfGlobal(long userID) throws DatabaseLayerException;

	/**
	 *
	 * @param user
	 *            the userID the id of the user
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Long getUserScoreInGroup(long userID, long groupID) throws DatabaseLayerException;

	/**
	 *
	 * @param user
	 *            the userID the id of the user
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public String getPhoneNumberHash(long userID) throws DatabaseLayerException;

	/**
	 *
	 * @param question
	 *            the questionID
	 * @param user
	 *            the userID the id of the user
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Answer getChoseAnswerInPublicQuestion(long questionID, long userID) throws DatabaseLayerException;

	/**
	 *
	 * @param question
	 *            the questionID
	 * @param user
	 *            the userID the id of the user
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Answer getChoseAnswerInPrivateQuestion(long questionID, long userID) throws DatabaseLayerException;

	/**
	 *
	 * @param question
	 *            the questionID
	 * @param user
	 *            the userID the id of the user
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Answer getSelectedAnswerInPrivateQuestion(long questionID) throws DatabaseLayerException;

	/**
	 * returns the place in the Ranking of a user in a group
	 *
	 * @param user
	 *            the userID the id of the user
	 * @param user
	 *            the groupID
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Long getRankingInGroup(long userID, long groupID) throws DatabaseLayerException;

	/**
	 * returns the passwordHash of a user
	 *
	 * @param user
	 *            the userID the id of the user
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public String getPasswordHash(long userID) throws DatabaseLayerException;

	/**
	 * 
	 * @param user
	 *            the userID the id of the user
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public String getLanguage(long userID) throws DatabaseLayerException;

	/**
	 * get the profilePictureURI a user
	 *
	 * @param user
	 *            the userID the id of the user
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public String getProfilePictureURI(long userID) throws DatabaseLayerException;

	/**
	 * get the groupPicture
	 *
	 * @param user
	 *            the groupID
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public String getGroupPictureURI(long groupID) throws DatabaseLayerException;

	/**
	 * get the group name
	 *
	 * @param user
	 *            the groupID
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public String getGroupName(long groupID) throws DatabaseLayerException;

	/**
	 *
	 *
	 * @param user
	 *            the groupID
	 * @param startIndex
	 * @param quantity
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PrivateQuestion[] getOldPrivateQuestions(long groupID, int startIndex, int quantity) throws DatabaseLayerException;

	/**
	 *
	 * @param question
	 *            the questionID
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Pair<Answer, Integer>[] getAnswersOfPublicQuestionAndCount(long questionID) throws DatabaseLayerException;

	/**
	 * @param question
	 *            the questionID
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Pair<Answer, Integer>[] getAnswersOfPrivateQuestionAndCount(long questionID) throws DatabaseLayerException;

	/**
	 *
	 * @param user
	 *            the userID the id of the user
	 * @param startIndex
	 * @param quantity
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PublicQuestion[] getActivePublicQuestionsOfUser(long userID, int startIndex, int quantity) throws DatabaseLayerException;

	/**
	 *
	 * @param user
	 *            the userID the id of the user
	 * @param startIndex
	 * @param quantity
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PrivateQuestion[] getActivePrivateQuestionsOfUser(long userID, int startIndex, int quantity) throws DatabaseLayerException;

	/**
	 *
	 * @param user
	 *            the userID the id of the user
	 * @param startIndex
	 * @param quantity
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PublicQuestion[] getOldPublicQuestionsOfUser(long userID, int startIndex, int quantity) throws DatabaseLayerException;

	/**
	 *
	 * @param user
	 *            the userID the id of the user
	 * @param startIndex
	 * @param quantity
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PrivateQuestion[] getOldPrivateQuestionsOfUser(long userID, int startIndex, int quantity) throws DatabaseLayerException;

	/**
	 *
	 * @param user
	 *            the userID the id of the user
	 * @return
	 */
	public Pair<String, Integer>[] getAllGroupScoresAndGlobalScoreOfUser(long userID) throws DatabaseLayerException;;

	/**
	 *
	 * @param user
	 *            the groupID
	 * @return
	 */
	public Pair<String, Integer>[] getUsersOfGroupsWithScore(long groupID) throws DatabaseLayerException;

	/**
	 * @param userID
	 * @return
	 * @throws DatabaseLayerException
	 */
	String getEmail(long userID) throws DatabaseLayerException;

	// ================================================================================
	// SET METHODS
	// ================================================================================

	/**
	 * @param user
	 *            the userID the id of the user
	 * @param newLanguage
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setLanguage(long userID, String newLanguage) throws DatabaseLayerException;

	/**
	 * @param user
	 *            the userID the id of the user
	 * @param newProfilePictureURI
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setProfilPictureOfUser(long userID, String newProfilePictureURI) throws DatabaseLayerException;

	/**
	 * @param user
	 *            the groupID
	 * @param newGroupPictureURI
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setGroupPicture(long groupID, String newGroupPictureURI) throws DatabaseLayerException;

	/**
	 * @param user
	 *            the userID the id of the user
	 * @param newPasswordHash
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setPasswordHash(long userID, String newPasswordHash) throws DatabaseLayerException;

	/**
	 * @param user
	 *            the userID the id of the user
	 * @param question
	 *            the questionID
	 * @param answerID
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setChoosedAnswerOfPrivateQuestion(long userID, long questionID, long answerID) throws DatabaseLayerException;

	/**
	 * @param user
	 *            the userID the id of the user
	 * @param question
	 *            the questionID
	 * @param answerID
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setChoosedAnswerOfPublicQuestion(long userID, long questionID, long answerID) throws DatabaseLayerException;

	/**
	 * @param question
	 *            the questionID
	 * @param answerID
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setSelectedAnswerOfPrivateQuestion(long questionID, long answerID) throws DatabaseLayerException;

	/**
	 * @param user
	 *            the groupID
	 * @param newAdmminID
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setGroupAdmin(long groupID, long newAdmminID) throws DatabaseLayerException;

	/**
	 * @param user
	 *            the userID the id of the user
	 * @param newPhoneNumberHash
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setPhoneNumberHash(long userID, String newPhoneNumberHash) throws DatabaseLayerException;

	/**
	 * @param user
	 *            the groupID
	 * @param newGroupName
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setGroupName(long groupID, String newGroupName) throws DatabaseLayerException;

	/**
	 * @param user
	 *            the userID the id of the user
	 * @param newUsername
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setUsername(long userID, String newUsername) throws DatabaseLayerException;

	/**
	 * @param user
	 *            the userID the id of the user
	 * @param newUsername
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setPublicQuestionToFinish(long questionID) throws DatabaseLayerException;

	/**
	 * @param question
	 *            the questionID
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setPrivateQuestionToFinish(long questionID) throws DatabaseLayerException;

	/**
	 * @param userID
	 * @param newEmail
	 * @throws DatabaseLayerException
	 */
	public void setEmail(long userID, String newEmail) throws DatabaseLayerException;

	// ================================================================================
	// DELETE METHODS
	// ================================================================================

	/**
	 * @param question
	 *            the questionID
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void deletePrivateQuestion(long questionID) throws DatabaseLayerException;

	/**
	 * @param user
	 *            the groupID
	 * @param user
	 *            the userID the id of the user
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void deleteUserFromGroup(long groupID, long userID) throws DatabaseLayerException;

	/**
	 * @param user
	 *            the groupID
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void deleteGroup(long groupID) throws DatabaseLayerException;

	/**
	 * @param user
	 *            the userID the id of the user
	 * @param contactID
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void deleteContact(long userID, long contactID) throws DatabaseLayerException;

	// ================================================================================
	// SEARCH METHODS
	// ================================================================================

	/**
	 * @param user
	 *            the userID the id of the user
	 * @param nameSearchPattern
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Group[] searchForGroup(long userID, String nameSearchPattern) throws DatabaseLayerException;

	/**
	 * @param user
	 *            the groupID
	 * @param question
	 *            the questionSearchPattern
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PrivateQuestion[] searchForPrivateQuestionInGroup(long groupID, String questionSearchPattern) throws DatabaseLayerException;

	/**
	 * @param nameSearchPattern
	 * @return
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PublicQuestion[] searchForPublicQuestion(String nameSearchPattern, String language) throws DatabaseLayerException;

}