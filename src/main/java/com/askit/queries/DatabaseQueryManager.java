package com.askit.queries;

// TODO bessers handling für columns
// TODO SQLFactory überall benutzen
// TODO notifications + trigegr for add admin to group after creat egroup & Question to User after Question creation (auch OTQ)
// TODO logische Nachfolgeopertation addUserToGroup
// TODO set und update unterscheiden
// TODO Frage abbrechen -> was passiert und Public und/oder Private
// TODO Sort überall beachten
// TODO langu bei Active/Old PublicQuestions beachten?

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.askit.database.ConnectionFactory;
import com.askit.entities.Answer;
import com.askit.entities.Group;
import com.askit.entities.Notification;
import com.askit.entities.PrivateQuestion;
import com.askit.entities.PublicQuestion;
import com.askit.entities.User;
import com.askit.etc.Constants;
import com.askit.etc.Util;
import com.askit.exception.DriverNotFoundException;
import com.askit.exception.ModellToObjectException;
import com.thirdparty.modelToObject.ResultSetMapper;

public class DatabaseQueryManager implements QueryManager {
	private static final String SCHEMA = Constants.SCHEMA_NAME;
	public final static String[] COLUMNS_USERS = new String[] { "userID", "passwordhash", "phoneNumberHash", "username", "accessionDate",
			"profilePictureURI", "language", "scoreOfGlobal", };
	private final static String[] COLUMNS_GROUPS = new String[] { "groupID", "createDate", "adminID", "groupname", "groupPictureURI" };
	private final static String[] COLUMNS_GROUPS_TO_USER = new String[] { "groupID", "userID", "score" };
	private final static String[] COLUMNS_PUBLIC_QUESTIONS_TO_USERS = new String[] { "questionID", "userID", "choosedAnswerID" };
	private final static String[] COLUMNS_PRIVATE_QUESTION_TO_USERS = new String[] { "questionID", "userID", "choosedAnswerID" };
	private final static String[] COLUMNS_PRIVATE_QUESTION = new String[] { "questionID", "question", "additionalInformation", "hostID ", "groupID",
			"pictureURI", "createDate", "endDate", "optionExtension", "definitionOfEnd", "sumOfUsersToAnswer", "language", "isBet",
			"selectedAnswerID", "finished" };
	private final static String[] COLUMNS_PUBLIC_QUESTION = new String[] { "publicQuestionID", "question", "additionalInformation", "hostID",
			"pictureURI", "createDate", "endDate", "language", "optionExtension", "finished" };
	private final static String[] COLUMNS_ONE_TIME_QUESTION = Util.concatenateTwoArrays(Util.getFromToFromArray(COLUMNS_PRIVATE_QUESTION, 0, 3),
			Util.getFromToFromArray(COLUMNS_PRIVATE_QUESTION, 5, COLUMNS_PRIVATE_QUESTION.length - 1));

	@Override
	public boolean checkUser(final String username, final String passwordHash) throws SQLException, DriverNotFoundException {
		final Connection connection = ConnectionFactory.getInstance().getReaderConnection();
		final String firstPart = SQLFactory.buildSimpleSelectStatement(SCHEMA, Constants.TABLE_USERS);
		final PreparedStatement statement = connection.prepareStatement(firstPart + "passwordHash = ? AND username = ?;");
		statement.setString(1, passwordHash);
		statement.setString(2, username);
		final ResultSet result = statement.executeQuery();
		return result.next();
	}

	// ================================================================================
	// ADD METHODS
	// ================================================================================

	@Override
	public void registerUser(final User user) throws SQLException, DriverNotFoundException {
		final String[] columns = Util.getFromToFromArray(COLUMNS_USERS, 2, 7);
		final String statement = SQLFactory.buildInsertStatement(SCHEMA, Constants.TABLE_USERS, columns);
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setString(1, user.getPasswordHash());
		preparedStatement.setString(2, user.getPhoneNumberHash());
		preparedStatement.setString(3, user.getUsername());
		preparedStatement.setDate(4, new Date(System.currentTimeMillis()));
		preparedStatement.setString(5, user.getProfilePictureURI());
		preparedStatement.setString(6, user.getLanguage());
		preparedStatement.executeUpdate();
	}

	@Override
	public void createNewGroup(final Group group) throws SQLException, DriverNotFoundException {
		final String[] columns = Util.getFromToFromArray(COLUMNS_GROUPS, 1, 4);
		final String statement = SQLFactory.buildInsertStatement(SCHEMA, Constants.TABLE_GROUPS, columns);
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setDate(1, new Date(System.currentTimeMillis()));
		preparedStatement.setLong(2, group.getAdminID());
		preparedStatement.setString(3, group.getGroupname());
		preparedStatement.setString(4, group.getGroupPictureURI());
		preparedStatement.executeUpdate();
		// TODO add Admin
	}

	@Override
	public void addUserToGroup(final long groupID, final long userID) throws SQLException, DriverNotFoundException {
		final String[] columns = Util.getFromToFromArray(COLUMNS_GROUPS_TO_USER, 0, 1);
		final String statement = SQLFactory.buildInsertStatement(SCHEMA, Constants.TABLE_GROUPS_TO_USERS, columns);
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setLong(1, groupID);
		preparedStatement.setLong(2, userID);
		preparedStatement.executeUpdate();
	}

	@Override
	public void addContact(final long userIDOfUser, final long userIDofContact) throws SQLException, DriverNotFoundException {
		final String statement = SQLFactory.buildSimpleInsertStatement(SCHEMA, Constants.TABLE_CONTACTS, 2);
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setLong(1, userIDOfUser);
		preparedStatement.setLong(2, userIDofContact);
		preparedStatement.executeUpdate();
	}

	@Override
	public void addUserToOneTimeQuestion(final long userID, final long questionID) throws SQLException, DriverNotFoundException {
		final String[] columns = Util.getFromToFromArray(COLUMNS_PRIVATE_QUESTION_TO_USERS, 0, 1);
		final String statement = SQLFactory.buildInsertStatement(SCHEMA, Constants.TABLE_PRIVATE_QUESTIONS_TO_USERS, columns);
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setLong(1, userID);
		preparedStatement.setLong(2, questionID);
		preparedStatement.executeUpdate();
	}

	@Override
	public void createPublicQuestion(final PublicQuestion question) throws SQLException, DriverNotFoundException {
		final String[] columns = Util.getFromToFromArray(COLUMNS_PUBLIC_QUESTION, 1, 8);
		final String statement = SQLFactory.buildInsertStatement(SCHEMA, Constants.TABLE_PRIVATE_QUESTIONS, columns);
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setString(1, question.getQuestion());
		preparedStatement.setString(2, question.getAdditionalInformation());
		preparedStatement.setLong(3, question.getHostID());
		preparedStatement.setString(4, question.getPictureURI());
		preparedStatement.setDate(5, new Date(System.currentTimeMillis()));
		preparedStatement.setDate(6, new Date(question.getEndDate().getTime()));
		preparedStatement.setBoolean(7, question.getOptionExtension());
		preparedStatement.setString(8, question.getLanguage());
		preparedStatement.executeUpdate();
	}

	@Override
	public void createNewQuestionInGroup(final PrivateQuestion question) throws SQLException, DriverNotFoundException {
		createNewQuestion(question, false);
	}

	@Override
	public void createOneTimeQuestion(final PrivateQuestion question) throws SQLException, DriverNotFoundException {
		createNewQuestion(question, true);
	}

	@Override
	public void addAnswerToPublicQuestion(final Answer answer) throws SQLException, DriverNotFoundException {
		addAnswerToAnswerTable(answer, Constants.TABLE_ANSWERS_PUBLIC_QUESTIONS);
	}

	@Override
	public void addAnswerToPrivateQuestion(final Answer answer) throws SQLException, DriverNotFoundException {
		addAnswerToAnswerTable(answer, Constants.TABLE_ANSWERS_PRIVATE_QUESTIONS);
	}

	// ================================================================================
	// GET METHODS
	// ================================================================================

	@Override
	public PublicQuestion[] getPublicQuestions(final int startIndex, final int quantity, final String language) throws SQLException,
			DriverNotFoundException, ModellToObjectException {
		String statement = SQLFactory.buildSimpleSelectStatement(SCHEMA, Constants.TABLE_PUBLIC_QUESTIONS);
		statement += " WHERE langauge = ? ";
		final String finalStatement = SQLFactory.buildStatementForAreaSelect(statement, "createDate ASC", startIndex, quantity);
		final PreparedStatement preparedStatement = getReaderPreparedStatement(finalStatement);
		preparedStatement.setString(1, language);
		final ResultSet resultSet = preparedStatement.executeQuery();
		final List<PublicQuestion> publicQuestions = new ResultSetMapper<PublicQuestion>().mapResultSetToObject(resultSet, PublicQuestion.class);
		return publicQuestions.toArray(new PublicQuestion[publicQuestions.size()]);
	}

	@Override
	public PublicQuestion getPublicQuestion(final long questionID) throws ModellToObjectException, SQLException, DriverNotFoundException {
		String statement = SQLFactory.buildSimpleSelectStatement(SCHEMA, Constants.TABLE_PUBLIC_QUESTIONS);
		statement += " WHERE questionID = ?";
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, questionID);
		final ResultSet resultSet = preparedStatement.executeQuery();
		final List<PublicQuestion> publicQuestions = new ResultSetMapper<PublicQuestion>().mapResultSetToObject(resultSet, PublicQuestion.class);
		return publicQuestions.get(0);
	}

	@Override
	public PrivateQuestion getPrivateQuestion(final long questionID) throws ModellToObjectException, SQLException, DriverNotFoundException {
		String statement = SQLFactory.buildSimpleSelectStatement(SCHEMA, Constants.TABLE_PRIVATE_QUESTIONS);
		statement += " WHERE questionID = ?";
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, questionID);
		final ResultSet resultSet = preparedStatement.executeQuery();
		final List<PrivateQuestion> privateQuestions = new ResultSetMapper<PrivateQuestion>().mapResultSetToObject(resultSet, PrivateQuestion.class);
		return privateQuestions.get(0);
	}

	@Override
	public PrivateQuestion[] getQuestionsOfGroup(final long groupID, final int startIndex, final int quantity) throws SQLException,
			ModellToObjectException, DriverNotFoundException {
		String statement = SQLFactory.buildSimpleSelectStatement(SCHEMA, Constants.TABLE_PRIVATE_QUESTIONS);
		statement += " WHERE groupID = ?";
		statement = SQLFactory.buildStatementForAreaSelect(statement, "createDate ASC", startIndex, quantity);
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, groupID);
		final ResultSet resultSet = preparedStatement.executeQuery();
		final List<PrivateQuestion> privateQuestions = new ResultSetMapper<PrivateQuestion>().mapResultSetToObject(resultSet, PrivateQuestion.class);
		return privateQuestions.toArray(new PrivateQuestion[privateQuestions.size()]);
	}

	@Override
	public User[] getUsersByUsername(String searchPattern) throws ModellToObjectException, SQLException, DriverNotFoundException {
		final String[] columns = new String[] { COLUMNS_USERS[0], COLUMNS_USERS[3], COLUMNS_USERS[4] };
		String statement = SQLFactory.buildSelectStatement(SCHEMA, Constants.TABLE_USERS, columns);
		searchPattern += "%";
		statement += " WHERE username LIKE ? ORDER BY username ASC";
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setString(1, searchPattern);
		return getUserArrayFromReaderPreparedStatement(preparedStatement);
	}

	@Override
	public String getUsername(final long userID) throws SQLException, DriverNotFoundException {
		return getUserAttribute(COLUMNS_USERS[3], userID);
	}

	@Override
	public User[] getUsersOfPublicQuestion(final long questionID) throws SQLException, ModellToObjectException, DriverNotFoundException {
		// TODO sort
		final String statement = "SELECT U.* FROM Users U JOIN PublicQuestionsToUsers P ON questionID = ? AND P.userID = U.userID";
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, questionID);
		return getUserArrayFromReaderPreparedStatement(preparedStatement);
	}

	@Override
	public User[] getUsersOfPrivateQuestion(final long questionID) throws SQLException, ModellToObjectException, DriverNotFoundException {
		final String statement = "SELECT U.* FROM Users U JOIN PrivateQuestionsToUsers P ON questionID = ? AND P.userID = U.userID ORDER BY U.username;";
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, questionID);
		return getUserArrayFromReaderPreparedStatement(preparedStatement);
	}

	@Override
	public User[] getUsersOfAnswerPrivateQuestion(final long answerID) throws SQLException, ModellToObjectException, DriverNotFoundException {
		final String statement = "SELECT U.* FROM Users U JOIN PrivateQuestionsToUsers P ON choosedAnswerID = ? AND P.userID = U.userID ORDER BY U.username;";
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, answerID);
		return getUserArrayFromReaderPreparedStatement(preparedStatement);
	}

	@Override
	public User[] getUsersOfAnswerPublicQuestion(final long answerID) throws SQLException, ModellToObjectException, DriverNotFoundException {
		final String statement = "SELECT U.* FROM Users U JOIN PublicQuestionsToUsers P ON choosedAnswerID = ? AND P.userID = U.userID ORDER BY U.username;";
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, answerID);
		return getUserArrayFromReaderPreparedStatement(preparedStatement);
	}

	@Override
	public User[] getUsersOfGroup(final long groupID) throws SQLException, ModellToObjectException, DriverNotFoundException {
		final String statement = "SELECT U.* FROM Users U JOIN GroupsToUsers G ON groupID = ? AND G.userID = U.userID ORDER BY U.username;";
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, groupID);
		return getUserArrayFromReaderPreparedStatement(preparedStatement);
	}

	@Override
	public Long getUserScoreOfGlobal(final long userID) throws SQLException, DriverNotFoundException {
		return Long.parseLong(getUserAttribute(COLUMNS_USERS[7], userID));
	}

	@Override
	public Long getUserScoreInGroup(final long userID, final long groupID) throws SQLException, DriverNotFoundException {
		final String statement = "SELECT score FROM GroupsToUsers WHERE groupID = ? AND userID = ?;";
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, groupID);
		preparedStatement.setLong(2, userID);
		return preparedStatement.executeQuery().getLong(1);
	}

	@Override
	public String getPhoneNumberHash(final long userID) throws SQLException, DriverNotFoundException {
		return getUserAttribute(COLUMNS_USERS[2], userID);
	}

	@Override
	public Answer getChoseAnswerInPublicQuestion(final long questionID, final long userID) throws SQLException, ModellToObjectException,
			DriverNotFoundException {
		return getChoseAnswer(questionID, userID, SCHEMA, Constants.TABLE_PUBLIC_QUESTIONS_TO_USERS);
	}

	@Override
	public Answer getChoseAnswerInPrivateQuestion(final long questionID, final long userID) throws SQLException, ModellToObjectException,
			DriverNotFoundException {
		return getChoseAnswer(questionID, userID, SCHEMA, Constants.TABLE_PRIVATE_QUESTIONS_TO_USERS);
	}

	@Override
	public Answer getSelectedAnswerInPrivateQuestion(final long questionID) throws SQLException, ModellToObjectException, DriverNotFoundException {
		final String statement = "SELECT * FROM AnswersPrivateQuestions WHERE answerID = ( SELECT selectedAnswerID FROM PrivateQuestions WHERE questionID = ? );";
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, questionID);
		return mapSingleAnswerToObject(preparedStatement);
	}

	@Override
	public Long getRankingInGroup(final long userID, final long groupID) throws SQLException, DriverNotFoundException {
		final String statement = "SELECT * FROM GroupsToUsers WHERE groupID = ? ORDER BY score ASC;";
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, groupID);
		final ResultSet resultSet = preparedStatement.executeQuery();
		final int resultSetSize = Util.getSizeOfResultSet(resultSet);
		long placeInRaking = 0;
		for (int place = 1; place <= resultSetSize; place++) {
			if (resultSet.getLong("userID") == userID) {
				placeInRaking = place;
			}
		}
		return new Long(placeInRaking);
	}

	@Override
	public String getPasswordHash(final long userID) throws SQLException, DriverNotFoundException {
		return getUserAttribute(COLUMNS_USERS[1], userID);
	}

	@Override
	public String getLanguage(final long userID) throws SQLException, DriverNotFoundException {
		return getUserAttribute(COLUMNS_USERS[6], userID);
	}

	@Override
	public String getProfilePictureURI(final long userID) throws SQLException, DriverNotFoundException {
		return getUserAttribute(COLUMNS_USERS[5], userID);
	}

	@Override
	public String getGroupPictureURI(final long groupID) throws SQLException, DriverNotFoundException {
		return getGroupAttribute("groupPictureURI", groupID);
	}

	@Override
	public String getGroupName(final long groupID) throws SQLException, DriverNotFoundException {
		return getGroupAttribute("groupname", groupID);
	}

	@Override
	public PrivateQuestion[] getOldPrivateQuestions(final long groupID, final int startIndex, final int quantity) {
		// TODO von nur einer Gruppe??
		// TODO sort
		return null;
	}

	@Override
	public Pair<Answer, Integer>[] getAnswersOfPublicQuestionAndCount(final long questionID) throws SQLException, DriverNotFoundException,
			ModellToObjectException {
		return getAnswersOfQuestionAndCount(questionID, Constants.TABLE_PUBLIC_QUESTIONS_TO_USERS, Constants.TABLE_ANSWERS_PUBLIC_QUESTIONS);
	}

	@Override
	public Pair<Answer, Integer>[] getAnswersOfPrivateQuestionAndCount(final long questionID) throws SQLException, DriverNotFoundException,
			ModellToObjectException {
		return getAnswersOfQuestionAndCount(questionID, Constants.TABLE_PRIVATE_QUESTIONS_TO_USERS, Constants.TABLE_ANSWERS_PRIVATE_QUESTIONS);
	}

	@Override
	public PublicQuestion[] getActivePublicQuestionsOfUser(final long userID, final int startIndex, final int quantity)
			throws DriverNotFoundException, ModellToObjectException, SQLException {
		return getPublicQuestionsOfUserDependingOnStatus(userID, startIndex, quantity, false);
	}

	@Override
	public PrivateQuestion[] getActivePrivateQuestionsOfUser(final long userID, final int startIndex, final int quantity)
			throws DriverNotFoundException, ModellToObjectException, SQLException {
		return getPrivateQuestionsOfUserDependingOnStatus(userID, startIndex, quantity, true);
	}

	@Override
	public PublicQuestion[] getOldPublicQuestionsOfUser(final long userID, final int startIndex, final int quantity) throws DriverNotFoundException,
			ModellToObjectException, SQLException {
		return getPublicQuestionsOfUserDependingOnStatus(userID, startIndex, quantity, false);

	}

	@Override
	public PrivateQuestion[] getOldPrivateQuestionsOfUser(final long userID, final int startIndex, final int quantity)
			throws DriverNotFoundException, ModellToObjectException, SQLException {
		return getPrivateQuestionsOfUserDependingOnStatus(userID, startIndex, quantity, true);
	}

	@Override
	public Pair<Group, Integer>[] getAllGroupScoresAndGlobalScoreOfUser(final long userID) {
		// TODO sort
		// TODO todo
		return null;
	}

	@Override
	public Pair<User, Integer>[] getUsersOfGroupsWithScore(final long groupID) {
		// TODO Auto-generated method stub
		// TODO sort
		return null;
	}

	// ================================================================================
	// SET METHODS
	// ================================================================================

	@Override
	public void setLanguage(final long userID, final String newLanguage) throws SQLException, DriverNotFoundException {
		updateUserAttributes(userID, COLUMNS_USERS[6], newLanguage);
	}

	@Override
	public void setProfilPictureOfUser(final long userID, final String newProfilePictureURI) throws SQLException, DriverNotFoundException {
		updateUserAttributes(userID, COLUMNS_USERS[5], newProfilePictureURI);
	}

	@Override
	public void setGroupPicture(final long groupID, final String newGroupPictureURI) throws SQLException, DriverNotFoundException {
		String statement = SQLFactory.buildBeginOfUpdateStatement(SCHEMA, Constants.TABLE_GROUPS_TO_USERS);
		statement += " " + COLUMNS_GROUPS[4] + " = ? WHERE " + COLUMNS_GROUPS[0] + " = ?;";
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setLong(1, groupID);
		preparedStatement.setString(2, newGroupPictureURI);
		preparedStatement.executeUpdate();
	}

	@Override
	public void setPasswordHash(final long userID, final String newPasswordHash) throws SQLException, DriverNotFoundException {
		updateUserAttributes(userID, COLUMNS_USERS[1], newPasswordHash);
	}

	@Override
	public void setChoosedAnswerOfPublicQuestion(final long userID, final long questionID, final long answerID) {
		// klären ob gleichzeit mit add
	}

	@Override
	public void setChoosedAnswerOfPrivateQuestion(final long userID, final long questionID, final long answerID) throws SQLException,
			DriverNotFoundException {
		final String statement = "UPDATE PrivateQuestionToUsers SET choosedAnswerID = ? WHERE userID = ? AND questionID = ?;";
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setLong(1, answerID);
		preparedStatement.setLong(2, userID);
		preparedStatement.setLong(3, questionID);
		preparedStatement.executeUpdate();
	}

	@Override
	public void setSelectedAnswerOfPrivateQuestion(final long questionID, final long answerID) throws SQLException, DriverNotFoundException {
		final String statement = "UPDATE PrivateQuestion SET selectedAnswerID = ? WHERE questionID = ?;";
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setLong(1, answerID);
		preparedStatement.setLong(2, questionID);
		preparedStatement.executeUpdate();
	}

	@Override
	public void setGroupAdmin(final long groupID, final long newAdmminID) throws SQLException, DriverNotFoundException {
		final String statement = "UPDATE Groups SET adminID = ? WHERE groupID = ?;";
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setLong(1, newAdmminID);
		preparedStatement.setLong(2, groupID);
		preparedStatement.executeUpdate();
	}

	@Override
	public void setPhoneNumberHash(final long userID, final String newPhoneNumberHash) throws SQLException, DriverNotFoundException {
		updateUserAttributes(userID, COLUMNS_USERS[2], newPhoneNumberHash);
	}

	@Override
	public void setGroupName(final long groupID, final String newGroupName) throws SQLException, DriverNotFoundException {
		final String statement = "UPDATE Groups SET groupName = ? WHERE groupID = ?;";
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setString(1, newGroupName);
		preparedStatement.setLong(2, groupID);
		preparedStatement.executeUpdate();
	}

	@Override
	public void setUsername(final long userID, final String newUsername) throws SQLException, DriverNotFoundException {
		updateUserAttributes(userID, COLUMNS_USERS[3], newUsername);
	}

	// ================================================================================
	// DELETE METHODS
	// ================================================================================

	@Override
	public void deletePrivateQuestion(final long questionID) throws SQLException, DriverNotFoundException {
		String statement = SQLFactory.buildDeleteStatement(SCHEMA, Constants.TABLE_PRIVATE_QUESTIONS);
		statement += "questionID = ?;";
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setLong(1, questionID);
		preparedStatement.executeUpdate();
	}

	@Override
	public void deleteUserFromGroup(final long groupID, final long userID) throws SQLException, DriverNotFoundException {
		String statement = SQLFactory.buildDeleteStatement(SCHEMA, Constants.TABLE_GROUPS_TO_USERS);
		statement += "groupID = ? AND userID = ?;";
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setLong(1, groupID);
		preparedStatement.setLong(2, userID);
		preparedStatement.executeUpdate();
	}

	@Override
	public void deleteGroup(final long groupID) throws SQLException, DriverNotFoundException {
		String statement = SQLFactory.buildDeleteStatement(SCHEMA, Constants.TABLE_GROUPS);
		statement += "groupID = ?;";
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setLong(1, groupID);
		preparedStatement.executeUpdate();
	}

	@Override
	public void deleteContact(final long userID, final long contactID) throws SQLException, DriverNotFoundException {
		String statement = SQLFactory.buildDeleteStatement(SCHEMA, Constants.TABLE_CONTACTS);
		statement += "userID = ? AND contactID = ?;";
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setLong(1, userID);
		preparedStatement.setLong(2, userID);
		preparedStatement.executeUpdate();
	}

	@Override
	public Group[] searchForGroup(final long userID, final String nameSearchPattern) throws SQLException, DriverNotFoundException {
		// TODO nur die Gruppen in denen ich schon bin
		return null;
	}

	@Override
	public PrivateQuestion[] searchForPrivateQuestionInGroup(final long groupID, final String questionSearchPattern) throws SQLException,
			DriverNotFoundException {
		// TODO
		return null;
	}

	@Override
	public PublicQuestion[] searchForPublicQuestion(final String nameSearchPattern) throws SQLException, DriverNotFoundException {
		// TODO
		return null;
	}

	@Override
	public void finishPrivateQuestion(final long questionID) throws SQLException, DriverNotFoundException {
		final String statement = "UPDATE PrivateQuestion SET finish = ? WHERE questionID = ?;";
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setBoolean(1, true);
		preparedStatement.setLong(2, questionID);
		preparedStatement.executeUpdate();
	}

	@Override
	public void addUserToPublicQuestion(final long questionID, final long userID) {
		// TODO gleichzeitig mit beantworten?
	}

	// ================================================================================
	// HELPER METHODS
	// ================================================================================

	private void updateUserAttributes(final long userID, final String column, final String newValue) throws SQLException, DriverNotFoundException {
		String statement = SQLFactory.buildBeginOfUpdateStatement(SCHEMA, Constants.TABLE_USERS);
		statement += " " + column + " = ? WHERE " + COLUMNS_USERS[0] + " = ?";
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setString(1, newValue);
		preparedStatement.setLong(2, userID);
		preparedStatement.executeUpdate();
	}

	private String getUserAttribute(final String column, final long userID) throws SQLException, DriverNotFoundException {
		final String[] columns = new String[] { column };
		String statement = SQLFactory.buildSelectStatement(SCHEMA, Constants.TABLE_USERS, columns);
		statement += " WHERE userID = ?";
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, userID);
		final ResultSet resultSet = preparedStatement.executeQuery();
		return resultSet.getString(1);
	}

	private void addAnswerToAnswerTable(final Answer answer, final String table) throws SQLException, DriverNotFoundException {
		final String statement = SQLFactory.buildSimpleInsertStatement(SCHEMA, table, 3);
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setLong(1, answer.getAnswerID());
		preparedStatement.setLong(2, answer.getQuestionID());
		preparedStatement.setString(3, answer.getAnswer());
		preparedStatement.executeUpdate();
	}

	private void createNewQuestion(final PrivateQuestion question, final boolean isOneTime) throws SQLException, DriverNotFoundException {
		final String[] columns = Util.getFromToFromArray(COLUMNS_PRIVATE_QUESTION, 1, 12);
		final String statement = SQLFactory.buildInsertStatement(SCHEMA, Constants.TABLE_PRIVATE_QUESTIONS, columns);
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setString(1, question.getQuestion());
		preparedStatement.setString(2, question.getAdditionalInformation());
		preparedStatement.setLong(3, question.getHostID());
		if (!isOneTime) {
			preparedStatement.setLong(4, question.getGroupID());
		}
		preparedStatement.setString(5, question.getPictureURI());
		preparedStatement.setDate(6, new Date(System.currentTimeMillis()));
		preparedStatement.setDate(7, new Date(question.getEndDate().getTime()));
		preparedStatement.setBoolean(8, question.getOptionExtension());
		preparedStatement.setInt(9, question.getDefinitionOfEnd());
		preparedStatement.setInt(10, question.getSumOfUsersToAnswer());
		preparedStatement.setString(11, question.getLanguage());
		preparedStatement.setBoolean(12, question.getIsBet());
		preparedStatement.executeUpdate();
		if (!isOneTime) {
			addUsersToPublicQuestion(0, 0);
		}
	}

	private void addUsersToPublicQuestion(final long questionID, final long groupID) throws SQLException, DriverNotFoundException {
		String innerSelect = "( "
				+ SQLFactory.buildSelectStatement(SCHEMA, Constants.TABLE_GROUPS_TO_USERS, new String[] { COLUMNS_GROUPS_TO_USER[0] });
		innerSelect += "groupID = ? )";
		String statement = SQLFactory.buildBeginOfInsertStatement(SCHEMA, Constants.TABLE_PRIVATE_QUESTIONS_TO_USERS, new String[] {
				COLUMNS_PRIVATE_QUESTION_TO_USERS[0], COLUMNS_PRIVATE_QUESTION_TO_USERS[1] });
		statement += "?, " + innerSelect + " );";
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setLong(1, questionID);
		preparedStatement.setLong(2, groupID);
		preparedStatement.executeUpdate();
	}

	private PreparedStatement getReaderPreparedStatement(final String statement) throws SQLException, DriverNotFoundException {
		final Connection connection = ConnectionFactory.getInstance().getReaderConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(statement);
		return preparedStatement;
	}

	private PreparedStatement getWriterPreparedStatement(final String statement) throws SQLException, DriverNotFoundException {
		final Connection connection = ConnectionFactory.getInstance().getWriterConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(statement);
		return preparedStatement;
	}

	private User[] getUserArrayFromReaderPreparedStatement(final PreparedStatement preparedStatement) throws SQLException, ModellToObjectException {
		final ResultSet resultSet = preparedStatement.executeQuery();
		final List<User> users = new ResultSetMapper<User>().mapResultSetToObject(resultSet, User.class);
		return users.toArray(new User[users.size()]);
	}

	private Answer getChoseAnswer(final long questionID, final long userID, final String schema, final String table) throws SQLException,
			DriverNotFoundException, ModellToObjectException {
		final String statement = "SELECT * FROM " + table + " WHERE questionID = ? AND userID = ?;";
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, questionID);
		preparedStatement.setLong(2, userID);
		return mapSingleAnswerToObject(preparedStatement);
	}

	private Answer mapSingleAnswerToObject(final PreparedStatement preparedStatement) throws SQLException, ModellToObjectException {
		final ResultSet resultSet = preparedStatement.executeQuery();
		final List<Answer> answers = new ResultSetMapper<Answer>().mapResultSetToObject(resultSet, Answer.class);
		return answers.get(0);
	}

	private String getGroupAttribute(final String column, final long groupID) throws SQLException, DriverNotFoundException {
		final String statement = "SELECT " + column + " FROM Groups WHERE groupID = ?;";
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, groupID);
		return preparedStatement.executeQuery().getString(1);
	}

	@SuppressWarnings("unchecked")
	private Pair<Answer, Integer>[] getAnswersOfQuestionAndCount(final long questionID, final String questionToUserTable, final String answerTable)
			throws SQLException, DriverNotFoundException, ModellToObjectException {
		final String statement = "SELECT A.*, " + "( SELECT COUNT(*) FROM " + questionToUserTable
				+ " PU WHERE PU.questionID = ? AND PU.choosedAnswerID = A.answerID) counter" + "FROM " + answerTable + " A "
				+ "WHERE A.questionID = ? ORDER BY counter ASC;";
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, questionID);
		preparedStatement.setLong(1, questionID);
		final ResultSet resultSet = preparedStatement.executeQuery();
		final List<Answer> answers = new ResultSetMapper<Answer>().mapResultSetToObject(resultSet, Answer.class);
		final List<ImmutablePair<Answer, Integer>> answersWithCount = new ArrayList<ImmutablePair<Answer, Integer>>();
		for (int answerCounter = 0; answerCounter < answers.size(); answerCounter++) {
			final Answer answer = answers.get(answerCounter);
			resultSet.absolute(answerCounter + 1);
			final Integer count = resultSet.getInt("counter");
			answersWithCount.add(new ImmutablePair<Answer, Integer>(answer, count));
		}
		return answersWithCount.toArray((Pair<Answer, Integer>[]) new Pair[answersWithCount.size()]);
	}

	private PublicQuestion[] getPublicQuestionsOfUserDependingOnStatus(final long userID, final int startIndex, final int quantity,
			final boolean finished) throws SQLException, DriverNotFoundException, ModellToObjectException {
		final String statement = "SELECT P.* FROM " + Constants.TABLE_PUBLIC_QUESTIONS + " P JOIN " + Constants.TABLE_PUBLIC_QUESTIONS_TO_USERS
				+ " PQU ON PQU.userID = ? AND PQU.questionID = P.questionID WHERE finihed = ?";
		final ResultSet resultSet = getQuestionsOfUserDependingOnStatus(userID, startIndex, quantity, finished, statement);
		final List<PublicQuestion> publicQuestions = new ResultSetMapper<PublicQuestion>().mapResultSetToObject(resultSet, PublicQuestion.class);
		return publicQuestions.toArray(new PublicQuestion[publicQuestions.size()]);
	}

	private PrivateQuestion[] getPrivateQuestionsOfUserDependingOnStatus(final long userID, final int startIndex, final int quantity,
			final boolean finished) throws SQLException, DriverNotFoundException, ModellToObjectException {
		final String statement = "SELECT P.* FROM " + Constants.TABLE_PRIVATE_QUESTIONS + " P JOIN " + Constants.TABLE_PRIVATE_QUESTIONS_TO_USERS
				+ " PQU ON PQU.userID = ? AND PQU.questionID = P.questionID WHRER finihed = ?";
		final ResultSet resultSet = getQuestionsOfUserDependingOnStatus(userID, startIndex, quantity, finished, statement);
		final List<PrivateQuestion> privateQuestions = new ResultSetMapper<PrivateQuestion>().mapResultSetToObject(resultSet, PrivateQuestion.class);
		return privateQuestions.toArray(new PrivateQuestion[privateQuestions.size()]);
	}

	private ResultSet getQuestionsOfUserDependingOnStatus(final long userID, final int startIndex, final int quantity, final boolean finished,
			String statement) throws SQLException, DriverNotFoundException {
		statement = SQLFactory.buildStatementForAreaSelect(statement, "P.createDate ASC", startIndex, quantity);
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, userID);
		preparedStatement.setBoolean(2, finished);
		return preparedStatement.executeQuery();
	}

	@Override
	public Notification[] getNotifications(final long userID) {
		// TODO Auto-generated method stub
		return null;
	}
}