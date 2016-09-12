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
	 * @param username
	 *            the username the username of the user
	 * @param passwordHash
	 *            the passwordhash of the user
	 * @return true, if the combination matches, else false
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
	 * Creates a new Group
	 *
	 * @param group
	 *            the group to create
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void createNewGroup(Group group) throws DatabaseLayerException;

	/**
	 * Crates a new public question
	 *
	 * @param question
	 *            the question to create
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void createPublicQuestion(PublicQuestion question) throws DatabaseLayerException;

	/**
	 *
	 * Creates a new question in a group
	 *
	 * @param question
	 *            the question to create
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void createNewPrivateQuestionInGroup(PrivateQuestion question) throws DatabaseLayerException;

	/**
	 * Creates a new one-time question
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
	 * Adds a user to a group
	 *
	 * @param groupID
	 *            the id of the group
	 * @param userID
	 *            the id of the user
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void addUserToGroup(long groupID, long userID) throws DatabaseLayerException;

	/**
	 * Adds a contact to a user
	 *
	 * @param userIDOfUser
	 *            the id of the user
	 * @param userIDofContact
	 *            the id of the contact
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void addContact(long userIDOfUser, long userIDofContact) throws DatabaseLayerException;

	/**
	 * Adds a user to a one-time question
	 *
	 * @param userID
	 *            the id of the user
	 * @param questionID
	 *            the id of the question
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void addUserToOneTimeQuestion(long userID, long questionID) throws DatabaseLayerException;

	/**
	 * Creates an answer and adds this to a public question
	 *
	 * @param answer
	 *            the answer to create
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void addAnswerToPublicQuestion(Answer answer) throws DatabaseLayerException;

	/**
	 * Creates an answer and adds this to a private question
	 * 
	 * @param answer
	 *            the answer to create
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void addAnswerToPrivateQuestion(Answer answer) throws DatabaseLayerException;

	/**
	 * Adds a user to a public question
	 *
	 * @param questionID
	 *            the id of the question
	 * @param userID
	 *            the id of the user
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void addUserToPublicQuestion(long questionID, long userID) throws DatabaseLayerException;

	// ================================================================================
	// GET METHODS
	// ================================================================================

	/**
	 * Returns public questions in a special area by the langauge and sorts by
	 * createDate and questionID
	 *
	 * @param startIndex
	 *            the startindex, begin is 0 (excluded), so 0 means the first
	 *            row
	 * @param quantity
	 *            how much public questions should be selected
	 * @param language
	 *            the langauge
	 * @return a array of public questions; if no questions where found it
	 *         returns a empty array
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PublicQuestion[] getPublicQuestions(int startIndex, int quantity, String language) throws DatabaseLayerException;

	/**
	 * Returns a specific public question
	 *
	 * @param questionID
	 *            the id of the public question or null of not found
	 * @return the specific
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PublicQuestion getPublicQuestion(long questionID) throws DatabaseLayerException;

	/**
	 * Returns a specific private question
	 * 
	 * @param questionID
	 *            the id of the private question
	 *
	 * @return the specific question or null if not found
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PrivateQuestion getPrivateQuestion(long questionID) throws DatabaseLayerException;

	/**
	 * Returns all questions of a group within an area
	 *
	 * @param groupID
	 *            the if of the group
	 * @param startIndex
	 *            the startindex, begin is 0 (excluded), so 0 means the first
	 * @param quantity
	 *            how much questions should be selected
	 * 
	 * @return a array of private questions; if no question where found it
	 *         returns a empty array
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PrivateQuestion[] getQuestionsOfGroup(long groupID, int startIndex, int quantity) throws DatabaseLayerException;

	/**
	 * Returns the username of a user
	 *
	 * @param userID
	 *            the userID the id of the user
	 * @return the username of the given user or null if not found
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public String getUsername(long userID) throws DatabaseLayerException;

	/**
	 * Returns all users of a public question
	 *
	 * @param questionID
	 *            the questionID
	 * @return a array of the users maybe empty, cause no users are assigned to
	 *         the question or the question wans't found
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public User[] getUsersOfPublicQuestion(long questionID) throws DatabaseLayerException;

	/**
	 * Returns all users of a private question
	 *
	 * @param questionID
	 *            the questionID
	 * @return a array of the users maybe empty, cause no users are assigned to
	 *         the question or the question wans't found
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public User[] getUsersOfPrivateQuestion(long questionID) throws DatabaseLayerException;

	/**
	 * Returns the user which answer with a specific answer to a private
	 * question
	 * 
	 * @param answerID
	 *            the id of the answer
	 * @return a array of the users maybe empty, cause no users answered with
	 *         this answer or the question wasn't found
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public User[] getUsersOfAnswerPrivateQuestion(long answerID) throws DatabaseLayerException;

	/**
	 * Returns the user which answer with a specific answer to a public question
	 *
	 * @param answerID
	 *            the id of the answer
	 * @return a array of the users maybe empty, cause no users answered with
	 *         this answer or the question wasn't found
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public User[] getUsersOfAnswerPublicQuestion(long answerID) throws DatabaseLayerException;

	/**
	 * Returns the users of a group
	 *
	 * @param groupID
	 *            the id of the group
	 * @return a array of the users maybe empty, cause no users are assigned to
	 *         the group or the group wasn't found
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public User[] getUsersOfGroup(long groupID) throws DatabaseLayerException;

	/**
	 * Returns the global score of a user
	 *
	 * @param userID
	 *            the id of the user
	 * @return the gloabl score or null if the user wasn't found
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Long getUserScoreOfGlobal(long userID) throws DatabaseLayerException;

	/**
	 * Returns the score of an user in a group
	 *
	 * @param userID
	 *            the userID the id of the user
	 * @param groupID
	 *            the id of the group
	 * @return the score in the group or null if the user or the group wasn't
	 *         found
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Long getUserScoreInGroup(long userID, long groupID) throws DatabaseLayerException;

	/**
	 * Returns the phonenumber hash of a user
	 *
	 * @param userID
	 *            the id of the user
	 * @return the phonenumber hash or null of the user wasn't found
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public String getPhoneNumberHash(long userID) throws DatabaseLayerException;

	/**
	 * Returns the choosen answer of a user in a public question
	 *
	 * @param questionID
	 *            the id of the public question
	 * @param userID
	 *            the id of the user
	 * @return the choosen answer or null if the user, question or no assigment
	 *         of the user to this question were found
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Answer getChoseAnswerInPublicQuestion(long questionID, long userID) throws DatabaseLayerException;

	/**
	 * Returns the choosen answer of a user in a private question
	 * 
	 * @param questionID
	 *            the id of the private question
	 * @param userID
	 *            the id of the user
	 * @return the choosen answer or null if the user, question or no assigment
	 *         of the user to this question were found
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Answer getChoseAnswerInPrivateQuestion(long questionID, long userID) throws DatabaseLayerException;

	/**
	 * Returns the selected answer of a private question
	 *
	 * @param questionID
	 *            the id of the private question
	 * @return the selected answer or null if the question wasn't found or the
	 *         selected answer wasn't set
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Answer getSelectedAnswerInPrivateQuestion(long questionID) throws DatabaseLayerException;

	/**
	 * Returns the place in the ranking of a user in a group
	 *
	 * @param userID
	 *            the id of the user
	 * @param groupID
	 *            the id of the group
	 * @return the place in the ranking or null if the user, group or no
	 *         assigment of the user to the group were found
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Long getRankingInGroup(long userID, long groupID) throws DatabaseLayerException;

	/**
	 * Returns the password hash of an user
	 *
	 * @param userID
	 *            the id of the user
	 * @return the password hash or null if the user wasn't found or the
	 *         password hash wasn't set
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public String getPasswordHash(long userID) throws DatabaseLayerException;

	/**
	 * Returns the language of an user
	 * 
	 * @param userID
	 *            the id of the user
	 * @return the langauge or null if the user wasn't found
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public String getLanguage(long userID) throws DatabaseLayerException;

	/**
	 * Returns the profile picture uri of an user
	 *
	 * @param userID
	 *            the id of the user
	 * @return the uri or null if the user wasn't found
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public String getProfilePictureURI(long userID) throws DatabaseLayerException;

	/**
	 * Returns the group picture uri of a group
	 *
	 * @param groupID
	 *            the id of the group
	 * @return the uri or null if the group wasn't found
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public String getGroupPictureURI(long groupID) throws DatabaseLayerException;

	/**
	 * Returns the group name of a group
	 *
	 * @param groupID
	 *            the id of the group
	 * @return the group name or null if the user wasn't found
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public String getGroupName(long groupID) throws DatabaseLayerException;

	/**
	 * Returns finished private questions in a group in a specific area
	 *
	 * @param groupID
	 *            the id of the group
	 * @param startIndex
	 *            the startindex, begin is 0 (excluded), so 0 means the first
	 * @param quantity
	 *            how much questions should be selected
	 * @return an array of private questions maybe empty
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PrivateQuestion[] getOldPrivateQuestions(long groupID, int startIndex, int quantity) throws DatabaseLayerException;

	/**
	 * Returns all answers of a public question and counts for each question how
	 * often it was choosed
	 * 
	 * @param questionID
	 *            the id of the public question
	 * @return a pair array may empty of the answers with count
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Pair<Answer, Integer>[] getAnswersOfPublicQuestionAndCount(long questionID) throws DatabaseLayerException;

	/**
	 * Returns all answers of a private question and counts for each question
	 * how often
	 * 
	 * @param questionID
	 *            the id of the private question
	 * @return a pair array may empty of the answers with count
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Pair<Answer, Integer>[] getAnswersOfPrivateQuestionAndCount(long questionID) throws DatabaseLayerException;

	/**
	 * Returns all not finished public questions of an user in a specific area
	 * 
	 * @param userID
	 *            the id of the user
	 * @param startIndex
	 *            the startindex, begin is 0 (excluded), so 0 means the first
	 * @param quantity
	 *            how much questions should be selected
	 * @return an array may empty of public questions
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PublicQuestion[] getActivePublicQuestionsOfUser(long userID, int startIndex, int quantity) throws DatabaseLayerException;

	/**
	 * Returns all not finished private questions of an user in a specific area
	 * 
	 * @param userID
	 *            the id of the user
	 * @param startIndex
	 *            the startindex, begin is 0 (excluded), so 0 means the first
	 * @param quantity
	 *            how much questions should be selected
	 * @return an array may empty of private questions
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PrivateQuestion[] getActivePrivateQuestionsOfUser(long userID, int startIndex, int quantity) throws DatabaseLayerException;

	/**
	 * Returns all finished public questions of an user in a specific area
	 * 
	 * @param userID
	 *            the id of the user
	 * @param startIndex
	 *            the startindex, begin is 0 (excluded), so 0 means the first
	 * @param quantity
	 *            how much questions should be selected
	 * @return an array may empty of public questions
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PublicQuestion[] getOldPublicQuestionsOfUser(long userID, int startIndex, int quantity) throws DatabaseLayerException;

	/**
	 * Returns all finished private questions of an user in a specific area
	 * 
	 * @param userID
	 *            the id of the user
	 * @param startIndex
	 *            the startindex, begin is 0 (excluded), so 0 means the first
	 * @param quantity
	 *            how much questions should be selected
	 * @return an array may empty of private questions
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PrivateQuestion[] getOldPrivateQuestionsOfUser(long userID, int startIndex, int quantity) throws DatabaseLayerException;

	/**
	 * Returns a list of all groups of an user with his score in this group and
	 * also his global score (the group is in this case empty)
	 * 
	 * @param userID
	 *            the id of the user
	 * @return a pair array may empty of groups with scores
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Pair<String, Integer>[] getAllGroupScoresAndGlobalScoreOfUser(long userID) throws DatabaseLayerException;;

	/**
	 * Returns a list of all users in a group with his score in this group
	 * 
	 * @param groupID
	 *            the id of the group
	 * @return a pair array may empty of user name with scores
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Pair<String, Integer>[] getUsersOfGroupsWithScore(long groupID) throws DatabaseLayerException;

	/**
	 * Returns the email of an user
	 * 
	 * @param userID
	 *            the id of the user
	 * @return the email or null if the user wasn't found
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	String getEmail(long userID) throws DatabaseLayerException;

	// ================================================================================
	// SET METHODS
	// ================================================================================

	/**
	 * Updates the langauge of an user
	 * 
	 * @param userID
	 *            the id of the user
	 * @param newLanguage
	 *            the new language
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setLanguage(long userID, String newLanguage) throws DatabaseLayerException;

	/**
	 * Updates the profile picture of an user
	 * 
	 * @param userID
	 *            the id of the user
	 * @param newProfilePictureURI
	 *            the new profile picture uri
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setProfilPictureOfUser(long userID, String newProfilePictureURI) throws DatabaseLayerException;

	/**
	 * Updates the group picture of a group
	 * 
	 * @param groupID
	 *            the id of the group
	 * @param newGroupPictureURI
	 *            the new group picture uri
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setGroupPicture(long groupID, String newGroupPictureURI) throws DatabaseLayerException;

	/**
	 * Updates the password hash of an user
	 * 
	 * @param userID
	 *            the id of the user
	 * @param newPasswordHash
	 *            the new password hash
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setPasswordHash(long userID, String newPasswordHash) throws DatabaseLayerException;

	/**
	 * Sets or updates the choosen answer of a private question
	 * 
	 * @param userID
	 *            the id of the user
	 * @param questionID
	 *            the id of the private question
	 * @param answerID
	 *            the id of the (new) answer
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setChoosedAnswerOfPrivateQuestion(long userID, long questionID, long answerID) throws DatabaseLayerException;

	/**
	 * Sets or updates the choosen answer of a public question
	 * 
	 * @param userID
	 *            the id of the user
	 * @param questionID
	 *            the id of the public question
	 * @param answerID
	 *            the id of the answer
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setChoosedAnswerOfPublicQuestion(long userID, long questionID, long answerID) throws DatabaseLayerException;

	/**
	 * Sets or updates the selected answer of a private question
	 * 
	 * @param questionID
	 *            the id of the private question
	 * @param answerID
	 *            the id of the answer
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setSelectedAnswerOfPrivateQuestion(long questionID, long answerID) throws DatabaseLayerException;

	/**
	 * Updates the group admin of a group
	 * 
	 * @param groupID
	 *            the id of the group
	 * @param newAdmminID
	 *            the id of the admin
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setGroupAdmin(long groupID, long newAdmminID) throws DatabaseLayerException;

	/**
	 * Updates the phone number hash of an user
	 * 
	 * @param userID
	 *            the id of the user
	 * @param newPhoneNumberHash
	 *            the new phone number hash
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setPhoneNumberHash(long userID, String newPhoneNumberHash) throws DatabaseLayerException;

	/**
	 * Update the group name of a group
	 * 
	 * @param groupID
	 *            the id of the group
	 * @param newGroupName
	 *            the new name of the group
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setGroupName(long groupID, String newGroupName) throws DatabaseLayerException;

	/**
	 * Update the username of an user
	 * 
	 * @param userID
	 *            the id of the user
	 * @param newUsername
	 *            the new username
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setUsername(long userID, String newUsername) throws DatabaseLayerException;

	/**
	 * Sets a public question to finish
	 * 
	 * @param questionID
	 *            the id of the public question
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setPublicQuestionToFinish(long questionID) throws DatabaseLayerException;

	/**
	 * Sets a private question to finish
	 * 
	 * @param questionID
	 *            the id of the private question
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setPrivateQuestionToFinish(long questionID) throws DatabaseLayerException;

	/**
	 * Sets or Updates the email adress of an user
	 * 
	 * @param userID
	 *            the id of the user
	 * 
	 * @param newEmail
	 *            the (new) email adress
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void setEmail(long userID, String newEmail) throws DatabaseLayerException;

	// ================================================================================
	// DELETE METHODS
	// ================================================================================

	/**
	 * Deletes a private question
	 * 
	 * @param questionID
	 *            the id of the private question
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void deletePrivateQuestion(long questionID) throws DatabaseLayerException;

	/**
	 * Deletes an user from a group
	 * 
	 * @param groupID
	 *            the id of the group
	 * @param userID
	 *            the id of the user
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void deleteUserFromGroup(long groupID, long userID) throws DatabaseLayerException;

	/**
	 * Deletes a group
	 * 
	 * @param groupID
	 *            the id of the group
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void deleteGroup(long groupID) throws DatabaseLayerException;

	/**
	 * Deletes a contact of a user
	 * 
	 * @param userID
	 *            the id of the user
	 * @param contactID
	 *            the id of the contact
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public void deleteContact(long userID, long contactID) throws DatabaseLayerException;

	// ================================================================================
	// SEARCH METHODS
	// ================================================================================

	/**
	 * Searches within the groups by group name
	 * 
	 * @param userID
	 *            the id of the user
	 * @param nameSearchPattern
	 *            the pattern to search
	 * @return an array of groups matches the search pattern may empty
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public Group[] searchForGroup(long userID, String nameSearchPattern) throws DatabaseLayerException;

	/**
	 * Searches within the private question by question
	 * 
	 * @param questionID
	 *            the id of the private question
	 * @param questionSearchPattern
	 *            the pattern to search
	 * @return an array of private questions matches the search pattern may
	 *         empty
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PrivateQuestion[] searchForPrivateQuestionInGroup(long questionID, String questionSearchPattern) throws DatabaseLayerException;

	/**
	 * Searches within the public question by question and the language
	 * 
	 * @param nameSearchPattern
	 *            the pattern to search
	 * @param language
	 *            the langauge
	 * @return an array of public questions matches the search pattern may empty
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public PublicQuestion[] searchForPublicQuestion(String nameSearchPattern, String language) throws DatabaseLayerException;

	/**
	 * Searches within users by username
	 *
	 * @param searchPattern
	 *            the pattern to search
	 * @return an array of users matches the search pattern may empty
	 * @throws DatabaseLayerException
	 *             if a exception occurs in the database
	 */
	public User[] searchUsersByUsername(String searchPattern) throws DatabaseLayerException;

}