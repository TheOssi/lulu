package com.askit.queries;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.askit.database.ConnectionFactory;
import com.askit.entities.Answer;
import com.askit.entities.Group;
import com.askit.entities.PrivateQuestion;
import com.askit.entities.PublicQuestion;
import com.askit.entities.User;
import com.askit.etc.Constants;
import com.askit.etc.Util;
import com.askit.exception.DriverNotFoundException;
import com.askit.exception.ModellToObjectException;
import com.thirdparty.modelToObject.ResultSetMapper;

public class DatabaseQueryManager implements QueryManager {
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

	private String getUserAttribute(final String column, final long userID) throws SQLException, DriverNotFoundException {
		final String[] columns = new String[] { column };
		final Connection connection = ConnectionFactory.getInstance().getReaderConnection();
		String statement = SQLFactory.buildSelectStatement(Constants.SCHEMA_NAME, Constants.TABLE_USERS, columns);
		statement += " WHERE userID = ?";
		final PreparedStatement preparedStatement = connection.prepareStatement(statement);
		preparedStatement.setLong(1, userID);
		final ResultSet resultSet = preparedStatement.executeQuery();
		return resultSet.getString(1);
	}

	private void addAnswerToAnswerTable(final Answer answer, final String table) throws SQLException, DriverNotFoundException {
		final String statement = SQLFactory.buildSimpleInsertStatement(Constants.SCHEMA_NAME, table, 3);
		final Connection connection = ConnectionFactory.getInstance().getWriterConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(statement);
		preparedStatement.setLong(1, answer.getAnswerID());
		preparedStatement.setLong(2, answer.getQuestionID());
		preparedStatement.setString(3, answer.getAnswer());
		preparedStatement.executeUpdate();
	}

	private void createNewQuestion(final PrivateQuestion question, final boolean isOneTime) throws SQLException, DriverNotFoundException {
		final String[] columns = Util.getFromToFromArray(COLUMNS_PRIVATE_QUESTION, 1, 12);
		final String statement = SQLFactory.buildInsertStatement(Constants.SCHEMA_NAME, Constants.TABLE_PRIVATE_QUESTIONS, columns);
		final Connection connection = ConnectionFactory.getInstance().getWriterConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(statement);
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

		// TODO Question to User
	}

	private void addUsersToPublicQuestion(final long questionID, final long groupID) throws SQLException, DriverNotFoundException {

		final Connection connection = ConnectionFactory.getInstance().getWriterConnection();
		final String statement = "INSERT INTO APP." + Constants.TABLE_PRIVATE_QUESTIONS_TO_USERS
				+ " (questionID,userID) VALUES ( ?, ( SELECT * FROM " + Constants.TABLE_GROUPS_TO_USERS + " WHERE groupid = ?) )";
		// TODO better
		final PreparedStatement preparedStatement = connection.prepareStatement(statement);
		preparedStatement.setLong(1, questionID);
		preparedStatement.setLong(2, groupID);
		preparedStatement.executeUpdate();

	}

	@Override
	public boolean checkUser(final String username, final String passwordHash) throws SQLException, DriverNotFoundException {
		final Connection connection = ConnectionFactory.getInstance().getReaderConnection();
		final String firstPart = SQLFactory.buildSimpleSelectStatement(Constants.SCHEMA_NAME, Constants.TABLE_USERS);
		final PreparedStatement statement = connection.prepareStatement(firstPart + "passwordHash = ? AND username = ?;");
		statement.setString(1, passwordHash);
		statement.setString(2, username);
		final ResultSet result = statement.executeQuery();
		return result.next();
	}

	@Override
	public void registerUser(final User user) throws SQLException, DriverNotFoundException {
		final String[] columns = Util.getFromToFromArray(COLUMNS_USERS, 2, 7);
		final String statement = SQLFactory.buildInsertStatement(Constants.SCHEMA_NAME, Constants.TABLE_USERS, columns);
		final Connection connection = ConnectionFactory.getInstance().getWriterConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(statement);
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
		final String statement = SQLFactory.buildInsertStatement(Constants.SCHEMA_NAME, Constants.TABLE_GROUPS, columns);
		final Connection connection = ConnectionFactory.getInstance().getWriterConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(statement);
		preparedStatement.setDate(1, new Date(System.currentTimeMillis()));
		preparedStatement.setLong(2, group.getAdminID());
		preparedStatement.setString(3, group.getGroupname());
		preparedStatement.setString(4, group.getGroupPictureURI());
		preparedStatement.executeUpdate();
		// TODO admin hinzugefügt
	}

	@Override
	public void addUserToGroup(final long groupID, final long userID) throws SQLException, DriverNotFoundException {
		final String[] columns = Util.getFromToFromArray(COLUMNS_GROUPS_TO_USER, 0, 1);
		final String statement = SQLFactory.buildInsertStatement(Constants.SCHEMA_NAME, Constants.TABLE_GROUPS_TO_USERS, columns);
		final Connection connection = ConnectionFactory.getInstance().getWriterConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(statement);
		preparedStatement.setLong(1, groupID);
		preparedStatement.setLong(2, userID);
		preparedStatement.executeUpdate();
	}

	@Override
	public void addContact(final long userIDOfUser, final long userIDofContact) throws SQLException, DriverNotFoundException {
		final String statement = SQLFactory.buildSimpleInsertStatement(Constants.SCHEMA_NAME, Constants.TABLE_CONTACTS, 2);
		final Connection connection = ConnectionFactory.getInstance().getWriterConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(statement);
		preparedStatement.setLong(1, userIDOfUser);
		preparedStatement.setLong(2, userIDofContact);
		preparedStatement.executeUpdate();
	}

	@Override
	public void addUserToOneTimeQuestion(final long userID, final long questionID) throws SQLException, DriverNotFoundException {
		final String[] columns = Util.getFromToFromArray(COLUMNS_PRIVATE_QUESTION_TO_USERS, 0, 1);
		final String statement = SQLFactory.buildInsertStatement(Constants.SCHEMA_NAME, Constants.TABLE_PRIVATE_QUESTIONS_TO_USERS, columns);
		final Connection connection = ConnectionFactory.getInstance().getWriterConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(statement);
		preparedStatement.setLong(1, userID);
		preparedStatement.setLong(2, questionID);
		preparedStatement.executeUpdate();

	}

	@Override
	public void createPublicQuestion(final PublicQuestion question) throws SQLException, DriverNotFoundException {
		final String[] columns = Util.getFromToFromArray(COLUMNS_PUBLIC_QUESTION, 1, 8);
		final String statement = SQLFactory.buildInsertStatement(Constants.SCHEMA_NAME, Constants.TABLE_PRIVATE_QUESTIONS, columns);
		final Connection connection = ConnectionFactory.getInstance().getWriterConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(statement);
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
		// TODO host automatisch hinzugefügt
	}

	@Override
	public void addAnswerToPublicQuestion(final Answer answer) throws SQLException, DriverNotFoundException {
		addAnswerToAnswerTable(answer, Constants.TABLE_ANSWERS_PUBLIC_QUESTIONS);
	}

	@Override
	public void addAnswerToPrivateQuestion(final Answer answer) throws SQLException, DriverNotFoundException {
		addAnswerToAnswerTable(answer, Constants.TABLE_ANSWERS_PRIVATE_QUESTIONS);
	}

	/*
	 * GET METHODS
	 */

	@Override
	public PublicQuestion[] getPublicQuestions(final int startIndex, final int quantity, final String language) throws SQLException,
			DriverNotFoundException, ModellToObjectException {
		final Connection connection = ConnectionFactory.getInstance().getReaderConnection();
		String statement = SQLFactory.buildSimpleSelectStatement(Constants.SCHEMA_NAME, Constants.TABLE_PUBLIC_QUESTIONS);
		statement += " WHERE langauge = ? ";
		final String finalStatement = SQLFactory.buildStatementForAreaSelect(statement, "createDate ASC", startIndex, quantity);
		final PreparedStatement preparedStatement = connection.prepareStatement(finalStatement);
		preparedStatement.setString(1, language);
		final ResultSet resultSet = preparedStatement.executeQuery();
		final List<PublicQuestion> publicQuestions = new ResultSetMapper<PublicQuestion>().mapResultSetToObject(resultSet, PublicQuestion.class);
		return publicQuestions.toArray(new PublicQuestion[publicQuestions.size()]);
	}

	@Override
	public PublicQuestion getPublicQuesion(final long questionID) throws ModellToObjectException, SQLException, DriverNotFoundException {
		final Connection connection = ConnectionFactory.getInstance().getReaderConnection();
		String statement = SQLFactory.buildSimpleSelectStatement(Constants.SCHEMA_NAME, Constants.TABLE_PUBLIC_QUESTIONS);
		statement += " WHERE questionID = ?";
		final PreparedStatement preparedStatement = connection.prepareStatement(statement);
		preparedStatement.setLong(1, questionID);
		final ResultSet resultSet = preparedStatement.executeQuery();
		final List<PublicQuestion> publicQuestions = new ResultSetMapper<PublicQuestion>().mapResultSetToObject(resultSet, PublicQuestion.class);
		return publicQuestions.get(0);
	}

	@Override
	public PrivateQuestion getPrivateQuestion(final long questionID) throws ModellToObjectException, SQLException, DriverNotFoundException {
		final Connection connection = ConnectionFactory.getInstance().getReaderConnection();
		String statement = SQLFactory.buildSimpleSelectStatement(Constants.SCHEMA_NAME, Constants.TABLE_PRIVATE_QUESTIONS);
		statement += " WHERE questionID = ?";
		final PreparedStatement preparedStatement = connection.prepareStatement(statement);
		preparedStatement.setLong(1, questionID);
		final ResultSet resultSet = preparedStatement.executeQuery();
		final List<PrivateQuestion> privateQuestions = new ResultSetMapper<PrivateQuestion>().mapResultSetToObject(resultSet, PrivateQuestion.class);
		return privateQuestions.get(0);
	}

	@Override
	public PrivateQuestion[] getQuestionsOfGroup(final long groupID, final int startIndex, final int quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User[] getUsersByUsername(String searchPattern) throws ModellToObjectException, SQLException, DriverNotFoundException {
		final Connection connection = ConnectionFactory.getInstance().getReaderConnection();
		final String[] columns = new String[] { COLUMNS_USERS[0], COLUMNS_USERS[3], COLUMNS_USERS[4] };
		String statement = SQLFactory.buildSelectStatement(Constants.SCHEMA_NAME, Constants.TABLE_USERS, columns);
		searchPattern += "%";
		statement += " WHERE username LIKE ?";
		final PreparedStatement preparedStatement = connection.prepareStatement(statement);
		preparedStatement.setString(1, searchPattern);
		final ResultSet resultSet = preparedStatement.executeQuery();
		final List<User> users = new ResultSetMapper<User>().mapResultSetToObject(resultSet, User.class);
		return users.toArray(new User[users.size()]);
	}

	@Override
	public String getUsername(final long userID) throws SQLException, DriverNotFoundException {
		return getUserAttribute(COLUMNS_USERS[3], userID);
	}

	@Override
	public User[] getUsersOfPublicQuestion(final long questionID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User[] getUsersOfPrivateQuestion(final long questionID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User[] getUsersOfAnswerPrivateQuestion(final long questionID, final long answerID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User[] getUsersOfAnswerPublicQuestion(final long questionID, final long answerID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User[] getUsersOfGroup(final long groupID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getUserScoreOfGlobal(final long userID) throws SQLException, DriverNotFoundException {
		return Long.parseLong(getUserAttribute(COLUMNS_USERS[8], userID));
	}

	@Override
	public Long getUserScoreInGroup(final long userID, final long groupID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPhoneNumberHash(final long userID) throws SQLException, DriverNotFoundException {
		return getUserAttribute(COLUMNS_USERS[2], userID);
	}

	@Override
	public Answer getSelectedAnswerInPublicQuestion(final long questionID, final long userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Answer getSelectedAnswerInPrivateQuestion(final long questionID, final long userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getRankingInGroup(final long userID, final long groupID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPasswordHash(final long userID) throws SQLException, DriverNotFoundException {
		return getUserAttribute(COLUMNS_USERS[1], userID);
	}

	@Override
	public String getLanguage(final long userID) throws SQLException, DriverNotFoundException {
		return getUserAttribute(COLUMNS_USERS[7], userID);
	}

	@Override
	public String getProfilePictureURI(final long userID) throws SQLException, DriverNotFoundException {
		return getUserAttribute(COLUMNS_USERS[6], userID);
	}

	@Override
	public String getGroupPictureURI(final long groupID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrivateQuestion[] getOldPrivateQuestions(final long groupID, final int startIndex, final int quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Answer[] getAnswersOfPublicQuestionAndCount(final long questionID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Answer[] getAnswersOfPrivateQuestionAndCount(final long questionID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PublicQuestion[] getActivePublicQuestionsOfUser(final long userID, final int startIndex, final int quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrivateQuestion[] getActivePrivateQuestionsOfUser(final long userID, final int startIndex, final int quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PublicQuestion[] getOldPublicQuestionsOfUser(final long userID, final int startIndex, final int quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrivateQuestion[] getOldPrivateQuestionsOfUser(final long userID, final int startIndex, final int quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<Group, Integer>[] getAllGroupScoresAndGlobalScoreOfUser(final long userID) {
		return null;
	}

	@Override
	public Pair<User, Integer>[] getUsersOfGroupsWithScore(final long groupID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLanguage(final long userID, final String newLanguage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setProfilPictureOfUser(final long userID, final String newProfilePictureURI) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setGroupPicture(final long groupID, final String newGroupPictureURI) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPasswordHash(final long userID, final String newPasswordHash) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSelectedAnswerOfPublicQuestion(final long userID, final long questionID, final long answerID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSelectedAnswerOfPrivateQuestion(final long userID, final long questionID, final long answerID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSelectedAnswerOfPrivateQuestion(final long questionID, final long answerID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setGroupAdmin(final long groupID, final long newAdmminID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPhoneNumberHash(final long userID, final String newPhoneNumberHash) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setGroupName(final long userID, final String newGroupName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUsername(final long userID, final String newUsername) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePrivateQuestion(final long questionID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUserFromGroup(final long groupID, final long userID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteGroup(final long groupID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteContact(final long userID, final long contactID) {
		// TODO Auto-generated method stub

	}

	@Override
	public Group[] searchForGroup(final long userID, final String nameSearchPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrivateQuestion[] searchForPrivateQuestionInGroup(final long groupID, final String groupnameSearchPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PublicQuestion[] searchForPublicQuestion(final String nameSearchPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void finishPrivateQuestion(final long questionID) {
		// TODO Auto-generated method stub

	}

}