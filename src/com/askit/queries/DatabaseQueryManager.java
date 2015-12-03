package com.askit.queries;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.askit.database.ConnectionFactory;
import com.askit.database.Constants;
import com.askit.entities.Group;
import com.askit.entities.PrivateQuestion;
import com.askit.entities.PublicQuestion;
import com.askit.entities.User;
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
		final String[] columns = Util.getFromToFromArray(COLUMNS_PRIVATE_QUESTION, 1, 12);
		final String statement = SQLFactory.buildInsertStatement(Constants.SCHEMA_NAME, Constants.TABLE_PRIVATE_QUESTIONS, columns);
		final Connection connection = ConnectionFactory.getInstance().getWriterConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(statement);
		preparedStatement.setString(1, question.getQuestion());
		preparedStatement.setString(2, question.getAdditionalInformation());
		preparedStatement.setLong(3, question.getHostID());
		preparedStatement.setLong(4, question.getGroupID());
		preparedStatement.setString(5, question.getPictureURI());
		preparedStatement.setDate(6, new Date(System.currentTimeMillis()));
		preparedStatement.setDate(7, new Date(question.getEndDate().getTime()));
		preparedStatement.setBoolean(8, question.getOptionExtension());
		preparedStatement.setInt(9, question.getDefinitionOfEnd());
		preparedStatement.setInt(10, question.getSumOfUsersToAnswer());
		preparedStatement.setString(11, question.getLanguage());
		preparedStatement.setBoolean(12, question.getIsBet());
		preparedStatement.executeUpdate();
		// TODO Question to User?
	}

	@Override
	public void createOneTimeQuestion(final PrivateQuestion question) throws SQLException, DriverNotFoundException {
		final String[] columns = Util.getFromToFromArray(COLUMNS_ONE_TIME_QUESTION, 1, 11);
		final String statement = SQLFactory.buildInsertStatement(Constants.SCHEMA_NAME, Constants.TABLE_PRIVATE_QUESTIONS_TO_USERS, columns);
		final Connection connection = ConnectionFactory.getInstance().getWriterConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(statement);
		preparedStatement.setString(1, question.getQuestion());
		preparedStatement.setString(2, question.getAdditionalInformation());
		preparedStatement.setLong(3, question.getHostID());
		preparedStatement.setString(4, question.getPictureURI());
		preparedStatement.setDate(5, new Date(System.currentTimeMillis()));
		preparedStatement.setDate(6, new Date(question.getEndDate().getTime()));
		preparedStatement.setBoolean(7, question.getOptionExtension());
		preparedStatement.setInt(8, question.getDefinitionOfEnd());
		preparedStatement.setInt(9, question.getSumOfUsersToAnswer());
		preparedStatement.setString(10, question.getLanguage());
		preparedStatement.setBoolean(11, question.getIsBet());
		preparedStatement.executeUpdate();
	}

	/*
	 * GET METHODS
	 */

	@Override
	public PublicQuestion[] getPublicQuestions(final int startIndex, final int quantity) throws SQLException, DriverNotFoundException,
			ModellToObjectException {
		final Connection connection = ConnectionFactory.getInstance().getReaderConnection();
		final String statement = SQLFactory.buildStatementForAreaSelect(Constants.SCHEMA_NAME, Constants.TABLE_PUBLIC_QUESTIONS, "createDate ASC",
				startIndex, quantity);
		final PreparedStatement preparedStatement = connection.prepareStatement(statement);
		final ResultSet resultSet = preparedStatement.executeQuery();
		final List<PublicQuestion> publicQuestions = new ResultSetMapper<PublicQuestion>().mapRersultSetToObject(resultSet, PublicQuestion.class);
		return publicQuestions.toArray(new PublicQuestion[publicQuestions.size()]);
	}

	private int getSizeOfResultSet(final ResultSet resultSet) throws SQLException {
		final int currentRow = resultSet.getRow();
		resultSet.last();
		final int lastRow = resultSet.getRow();
		resultSet.absolute(currentRow);
		return lastRow;
	}

}