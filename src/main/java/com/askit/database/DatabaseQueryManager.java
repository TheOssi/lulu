package com.askit.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.askit.database.sqlHelper.Constants;
import com.askit.database.sqlHelper.ResultSetMapper;
import com.askit.database.sqlHelper.SQLFactory;
import com.askit.database.sqlHelper.SQLUtil;
import com.askit.entities.Answer;
import com.askit.entities.Contact;
import com.askit.entities.Group;
import com.askit.entities.GroupToUser;
import com.askit.entities.PrivateQuestion;
import com.askit.entities.PrivateQuestionToUser;
import com.askit.entities.PublicQuestion;
import com.askit.entities.PublicQuestionToUser;
import com.askit.entities.User;
import com.askit.exception.DatabaseLayerException;

/**
 * The main implemetation of the {@link QueryManager}
 *
 * @author Kai Müller
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class DatabaseQueryManager implements QueryManager {

	private static final String SCHEMA = Constants.SCHEMA_NAME;

	ConnectionManager connectionManager = ConnectionManager.getInstance();

	@Override
	public boolean checkUser(final String username, final String passwordHash) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String whereCondition = User.PASSWORD_HASH + " = ? AND " + User.USERNAME + " = ?";
			final String statement = SQLFactory.buildSelectAllStatementWithWhereCondition(SCHEMA, User.TABLE_NAME, whereCondition);
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setString(1, passwordHash);
			preparedStatement.setString(2, username);
			resultSet = preparedStatement.executeQuery();
			return resultSet.next();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	// ================================================================================
	// CREATE METHODS
	// ================================================================================

	@Override
	public void createNewGroup(final Group group) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String[] columns = new String[] { Group.CREATE_DATE, Group.ADMIN_ID, Group.GROUP_NAME, Group.GROUP_PICTURE_URI };
			final String statement = SQLFactory.buildInsertStatement(SCHEMA, Group.TABLE_NAME, columns);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setDate(1, new Date(group.getCreateDate().getTime()));
			preparedStatement.setLong(2, group.getAdminID());
			preparedStatement.setString(3, group.getGroupname());
			preparedStatement.setString(4, group.getGroupPictureURI());
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	@Override
	public void createPublicQuestion(final PublicQuestion question) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String[] columns = new String[] { PublicQuestion.QUESTION, PublicQuestion.ADDITIONAL_INFORMATION, PublicQuestion.HOST_ID,
					PublicQuestion.PICTURE_URI, PublicQuestion.CREATED_DATE, PublicQuestion.END_DATE, PublicQuestion.OPTION_EXTENSION,
					PublicQuestion.LANGUAGE };
			final String statement = SQLFactory.buildInsertStatement(SCHEMA, PrivateQuestion.TABLE_NAME, columns);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setString(1, question.getQuestion());
			preparedStatement.setString(2, question.getAdditionalInformation());
			preparedStatement.setLong(3, question.getHostID());
			preparedStatement.setString(4, question.getPictureURI());
			preparedStatement.setDate(5, new Date(question.getCreateDate().getTime()));
			preparedStatement.setDate(6, new Date(question.getEndDate().getTime()));
			preparedStatement.setBoolean(7, question.getOptionExtension());
			preparedStatement.setString(8, question.getLanguage());
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	@Override
	public void createNewPrivateQuestionInGroup(final PrivateQuestion question) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String[] columns = new String[] { PrivateQuestion.QUESTION, PrivateQuestion.ADDITIONAL_INFORMATION, PrivateQuestion.HOST_ID,
					PrivateQuestion.GROUP_ID, PrivateQuestion.PICTURE_URI, PrivateQuestion.CREATED_DATE, PrivateQuestion.END_DATE,
					PrivateQuestion.OPTION_EXTENSION, PrivateQuestion.DEFINITION_OF_END, PrivateQuestion.SUM_OF_USERS_TO_ANSWER,
					PrivateQuestion.LANGUAGE, PrivateQuestion.IS_BET };
			final String statement = SQLFactory.buildInsertStatement(SCHEMA, PrivateQuestion.TABLE_NAME, columns);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setString(1, question.getQuestion());
			preparedStatement.setString(2, question.getAdditionalInformation());
			preparedStatement.setLong(3, question.getHostID());
			preparedStatement.setLong(4, question.getGroupID());
			preparedStatement.setString(5, question.getPictureURI());
			preparedStatement.setDate(6, new Date(question.getCreateDate().getTime()));
			preparedStatement.setDate(7, new Date(question.getEndDate().getTime()));
			preparedStatement.setBoolean(8, question.getOptionExtension());
			preparedStatement.setInt(9, question.getDefinitionOfEnd());
			preparedStatement.setInt(10, question.getSumOfUsersToAnswer());
			preparedStatement.setString(11, question.getLanguage());
			preparedStatement.setBoolean(12, question.getIsBet());
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	@Override
	public void createOneTimeQuestion(final PrivateQuestion question) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String[] columns = new String[] { PrivateQuestion.QUESTION, PrivateQuestion.ADDITIONAL_INFORMATION, PrivateQuestion.HOST_ID,
					PrivateQuestion.PICTURE_URI, PrivateQuestion.CREATED_DATE, PrivateQuestion.END_DATE, PrivateQuestion.OPTION_EXTENSION,
					PrivateQuestion.DEFINITION_OF_END, PrivateQuestion.SUM_OF_USERS_TO_ANSWER, PrivateQuestion.LANGUAGE, PrivateQuestion.IS_BET };
			final String statement = SQLFactory.buildInsertStatement(SCHEMA, PrivateQuestion.TABLE_NAME, columns);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setString(1, question.getQuestion());
			preparedStatement.setString(2, question.getAdditionalInformation());
			preparedStatement.setLong(3, question.getHostID());
			preparedStatement.setString(4, question.getPictureURI());
			preparedStatement.setDate(5, new Date(question.getCreateDate().getTime()));
			preparedStatement.setDate(6, new Date(question.getEndDate().getTime()));
			preparedStatement.setBoolean(7, question.getOptionExtension());
			preparedStatement.setInt(8, question.getDefinitionOfEnd());
			preparedStatement.setInt(9, question.getSumOfUsersToAnswer());
			preparedStatement.setString(10, question.getLanguage());
			preparedStatement.setBoolean(11, question.getIsBet());
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	@Override
	public void createUser(final User user) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final List<String> columnList = new ArrayList<String>();
			if (user.getPasswordHash() != null) {
				columnList.add(User.PASSWORD_HASH);
			}
			if (user.getEmail() != null) {
				columnList.add(User.EMAIL);
			}
			columnList.add(User.USERNAME);
			columnList.add(User.ACCESSION_DATE);
			if (user.getProfilePictureURI() != null) {
				columnList.add(User.PROFILEPICTURE_URI);
			}
			columnList.add(User.LANGUAGE);
			final String[] columns = columnList.toArray(new String[columnList.size()]);
			final String statement = SQLFactory.buildInsertStatement(SCHEMA, User.TABLE_NAME, columns);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setString(1, user.getPasswordHash());
			preparedStatement.setString(2, user.getPhoneNumberHash());
			preparedStatement.setString(3, user.getUsername());
			preparedStatement.setDate(4, new Date(user.getAccessionDate().getTime()));
			preparedStatement.setString(5, user.getProfilePictureURI());
			preparedStatement.setString(6, user.getLanguage());
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	// ================================================================================
	// ADD METHODS
	// ================================================================================

	@Override
	public void addUserToGroup(final long groupID, final long userID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String[] columns = new String[] { GroupToUser.GROUP_ID, GroupToUser.USER_ID };
			final String statement = SQLFactory.buildInsertStatement(SCHEMA, GroupToUser.TABLE_NAME, columns);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, groupID);
			preparedStatement.setLong(2, userID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	@Override
	public void addContact(final long userIDOfUser, final long userIDofContact) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String statement = SQLFactory.buildInsertAllStatement(SCHEMA, Contact.TABLE_NAME, 2);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, userIDOfUser);
			preparedStatement.setLong(2, userIDofContact);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	@Override
	public void addUserToOneTimeQuestion(final long userID, final long questionID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String[] columns = new String[] { PrivateQuestionToUser.QUESTION_ID, PrivateQuestionToUser.USER_ID };
			final String statement = SQLFactory.buildInsertStatement(SCHEMA, PrivateQuestionToUser.TABLE_NAME, columns);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, questionID);
			preparedStatement.setLong(2, userID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	@Override
	public void addAnswerToPublicQuestion(final Answer answer) throws DatabaseLayerException {
		addAnswerToAnswerTable(answer, Answer.TABLE_NAME_PUBLIC);
	}

	@Override
	public void addAnswerToPrivateQuestion(final Answer answer) throws DatabaseLayerException {
		addAnswerToAnswerTable(answer, Answer.TABLE_NAME_PRIVATE);
	}

	@Override
	public void addUserToPublicQuestion(final long questionID, final long userID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String[] columns = new String[] { PublicQuestionToUser.QUESTION_ID, PublicQuestionToUser.USER_ID };
			final String statement = SQLFactory.buildInsertStatement(SCHEMA, PublicQuestionToUser.TABLE_NAME, columns);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, questionID);
			preparedStatement.setLong(2, userID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	// ================================================================================
	// GET METHODS
	// ================================================================================

	@Override
	public PublicQuestion[] getPublicQuestions(final int startIndex, final int quantity, final String language) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String whereCondition = PublicQuestion.LANGUAGE + " = ?";
			final String orderByClausel = SQLFactory.buildOrderByStatement(PublicQuestion.CREATED_DATE + " " + SQLFactory.ASCENDING);
			final String limitClausel = SQLFactory.buildLimitStatement();
			final String statement = SQLFactory.buildSelectAllStatementWithWhereConditionLimitClauselOrderByClausel(SCHEMA,
					PublicQuestion.TABLE_NAME, whereCondition, orderByClausel, limitClausel);
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setString(1, language);
			preparedStatement.setInt(2, startIndex);
			preparedStatement.setInt(3, quantity);
			resultSet = preparedStatement.executeQuery();
			final List<PublicQuestion> publicQuestions = ResultSetMapper.mapPublicQuestions(resultSet);
			return publicQuestions.toArray(new PublicQuestion[publicQuestions.size()]);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public PublicQuestion getPublicQuestion(final long questionID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String whereCondition = PublicQuestion.QUESTION_ID + " = ?";
			final String statement = SQLFactory.buildSelectAllStatementWithWhereCondition(SCHEMA, PublicQuestion.TABLE_NAME, whereCondition);
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, questionID);
			resultSet = preparedStatement.executeQuery();
			final List<PublicQuestion> publicQuestions = ResultSetMapper.mapPublicQuestions(resultSet);
			return publicQuestions.get(0);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public PrivateQuestion getPrivateQuestion(final long questionID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String whereCondition = PrivateQuestion.QUESTION_ID + " = ?";
			final String statement = SQLFactory.buildSelectAllStatementWithWhereCondition(SCHEMA, PrivateQuestion.TABLE_NAME, whereCondition);
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, questionID);
			resultSet = preparedStatement.executeQuery();
			final List<PrivateQuestion> privateQuestions = ResultSetMapper.mapPrivateQuestions(resultSet);
			return privateQuestions.get(0);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public PrivateQuestion[] getQuestionsOfGroup(final long groupID, final int startIndex, final int quantity) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String orderByClausel = SQLFactory.buildOrderByStatement(PrivateQuestion.CREATED_DATE + " " + SQLFactory.ASCENDING);
			final String limitClausel = SQLFactory.buildLimitStatement();
			final String whereCondition = PrivateQuestion.GROUP_ID + " = ?";
			final String statement = SQLFactory.buildSelectAllStatementWithWhereConditionLimitClauselOrderByClausel(SCHEMA,
					PrivateQuestion.TABLE_NAME, whereCondition, orderByClausel, limitClausel);
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, groupID);
			preparedStatement.setInt(2, startIndex);
			preparedStatement.setInt(3, quantity);
			resultSet = preparedStatement.executeQuery();
			final List<PrivateQuestion> privateQuestions = ResultSetMapper.mapPrivateQuestions(resultSet);
			return privateQuestions.toArray(new PrivateQuestion[privateQuestions.size()]);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public String getUsername(final long userID) throws DatabaseLayerException {
		return getUserAttributeAsString(User.USERNAME, userID);
	}

	@Override
	public User[] getUsersOfPublicQuestion(final long questionID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String statement = "SELECT U.* FROM Users U JOIN PublicQuestionsToUsers P ON questionID = ? AND P.userID = U.userID ORDER BY U.username";
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, questionID);
			resultSet = preparedStatement.executeQuery();
			return getUserArrayFromResultSet(resultSet);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public User[] getUsersOfPrivateQuestion(final long questionID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String statement = "SELECT U.* FROM Users U JOIN PrivateQuestionsToUsers P ON questionID = ? AND P.userID = U.userID ORDER BY U.username;";
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, questionID);
			resultSet = preparedStatement.executeQuery();
			return getUserArrayFromResultSet(resultSet);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public User[] getUsersOfAnswerPrivateQuestion(final long answerID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String statement = "SELECT U.* FROM Users U JOIN PrivateQuestionsToUsers P ON choosedAnswerID = ? AND P.userID = U.userID ORDER BY U.username;";
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, answerID);
			resultSet = preparedStatement.executeQuery();
			return getUserArrayFromResultSet(resultSet);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public User[] getUsersOfAnswerPublicQuestion(final long answerID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String statement = "SELECT U.* FROM Users U JOIN PublicQuestionsToUsers P ON choosedAnswerID = ? AND P.userID = U.userID ORDER BY U.username;";
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, answerID);
			resultSet = preparedStatement.executeQuery();
			return getUserArrayFromResultSet(resultSet);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public User[] getUsersOfGroup(final long groupID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String statement = "SELECT U.* FROM Users U JOIN GroupsToUsers G ON groupID = ? AND G.userID = U.userID ORDER BY U.username;";
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, groupID);
			resultSet = preparedStatement.executeQuery();
			return getUserArrayFromResultSet(resultSet);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public Long getUserScoreOfGlobal(final long userID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String[] columns = new String[] { User.SCORE_OF_GLOBAL };
			final String whereCondition = User.USER_ID + " = ?";
			final String statement = SQLFactory.buildSelectStatementWithWhereCondition(SCHEMA, User.TABLE_NAME, columns, whereCondition);
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, userID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
			return null;
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public Long getUserScoreInGroup(final long userID, final long groupID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String whereCondition = GroupToUser.GROUP_ID + " = ? AND " + GroupToUser.USER_ID + " = ?";
			final String statement = SQLFactory.buildSelectStatementWithWhereCondition(SCHEMA, GroupToUser.TABLE_NAME,
					new String[] { GroupToUser.SCORE }, whereCondition);
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, groupID);
			preparedStatement.setLong(2, userID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
			return null;
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public String getPhoneNumberHash(final long userID) throws DatabaseLayerException {
		return getUserAttributeAsString(User.PHONENUMBER_HASH, userID);

	}

	@Override
	public Answer getChoseAnswerInPublicQuestion(final long questionID, final long userID) throws DatabaseLayerException {
		return getChoseAnswer(questionID, userID, SCHEMA, PublicQuestionToUser.TABLE_NAME);
	}

	@Override
	public Answer getChoseAnswerInPrivateQuestion(final long questionID, final long userID) throws DatabaseLayerException {
		return getChoseAnswer(questionID, userID, SCHEMA, PrivateQuestionToUser.TABLE_NAME);
	}

	@Override
	public Answer getSelectedAnswerInPrivateQuestion(final long questionID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String statement = "SELECT * FROM AnswersPrivateQuestions WHERE answerID = ( SELECT selectedAnswerID FROM PrivateQuestions WHERE questionID = ? );";
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, questionID);
			resultSet = preparedStatement.executeQuery();
			return ResultSetMapper.mapAnswers(resultSet).get(0);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public Long getRankingInGroup(final long userID, final long groupID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String statement = "SELECT * FROM GroupsToUsers WHERE groupID = ? ORDER BY score ASC;";
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, groupID);
			resultSet = preparedStatement.executeQuery();
			final int resultSetSize = SQLUtil.getSizeOfResultSet(resultSet);
			long placeInRaking = 0;
			if (resultSet.next()) {
				for (int place = 1; place <= resultSetSize; place++) {
					if (resultSet.getLong(User.USER_ID) == userID) {
						placeInRaking = place;
					}
					resultSet.next();
				}
				return new Long(placeInRaking);
			}
			return null;
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public String getPasswordHash(final long userID) throws DatabaseLayerException {
		return getUserAttributeAsString(User.PASSWORD_HASH, userID);
	}

	@Override
	public String getLanguage(final long userID) throws DatabaseLayerException {
		return getUserAttributeAsString(User.LANGUAGE, userID);
	}

	@Override
	public String getProfilePictureURI(final long userID) throws DatabaseLayerException {
		return getUserAttributeAsString(User.PROFILEPICTURE_URI, userID);
	}

	@Override
	public String getEmail(final long userID) throws DatabaseLayerException {
		return getUserAttributeAsString(User.EMAIL, userID);
	}

	@Override
	public String getGroupPictureURI(final long groupID) throws DatabaseLayerException {
		return getGroupAttributeAsString(Group.GROUP_PICTURE_URI, groupID);
	}

	@Override
	public String getGroupName(final long groupID) throws DatabaseLayerException {
		return getGroupAttributeAsString(Group.GROUP_NAME, groupID);
	}

	@Override
	public PrivateQuestion[] getOldPrivateQuestions(final long groupID, final int startIndex, final int quantity) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String statement = "SELECT * FROM PrivateQuestions WHERE groupID = ? ORDER BY createDate ASC LIMIT ?,?";
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, groupID);
			preparedStatement.setInt(2, startIndex);
			preparedStatement.setInt(3, quantity);
			resultSet = preparedStatement.executeQuery();
			final List<PrivateQuestion> privateQuestions = ResultSetMapper.mapPrivateQuestions(resultSet);
			return privateQuestions.toArray(new PrivateQuestion[privateQuestions.size()]);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public Pair<Answer, Integer>[] getAnswersOfPublicQuestionAndCount(final long questionID) throws DatabaseLayerException {
		return getAnswersOfQuestionAndCount(questionID, PublicQuestionToUser.TABLE_NAME, Answer.TABLE_NAME_PUBLIC);
	}

	@Override
	public Pair<Answer, Integer>[] getAnswersOfPrivateQuestionAndCount(final long questionID) throws DatabaseLayerException {
		return getAnswersOfQuestionAndCount(questionID, PrivateQuestionToUser.TABLE_NAME, Answer.TABLE_NAME_PRIVATE);
	}

	@Override
	public PublicQuestion[] getActivePublicQuestionsOfUser(final long userID, final int startIndex, final int quantity) throws DatabaseLayerException {
		return getPublicQuestionsOfUserDependingOnStatus(userID, startIndex, quantity, false);
	}

	@Override
	public PrivateQuestion[] getActivePrivateQuestionsOfUser(final long userID, final int startIndex, final int quantity)
			throws DatabaseLayerException {
		return getPrivateQuestionsOfUserDependingOnStatus(userID, startIndex, quantity, true);
	}

	@Override
	public PublicQuestion[] getOldPublicQuestionsOfUser(final long userID, final int startIndex, final int quantity) throws DatabaseLayerException {
		return getPublicQuestionsOfUserDependingOnStatus(userID, startIndex, quantity, false);
	}

	@Override
	public PrivateQuestion[] getOldPrivateQuestionsOfUser(final long userID, final int startIndex, final int quantity) throws DatabaseLayerException {
		return getPrivateQuestionsOfUserDependingOnStatus(userID, startIndex, quantity, true);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pair<String, Integer>[] getAllGroupScoresAndGlobalScoreOfUser(final long userID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String statement = "SELECT G.groupname, GU.score FROM GroupsToUsers GU WHERE userID = ? JOIN Groups G ON G.groupID = GU.groupID ORDER BY G.score";
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, userID);
			resultSet = preparedStatement.executeQuery();
			final List<Pair<String, Integer>> list = new ArrayList<Pair<String, Integer>>();
			while (resultSet.next()) {
				final Pair<String, Integer> pair = new ImmutablePair<String, Integer>(resultSet.getString(1), resultSet.getInt(2));
				list.add(pair);
			}
			return list.toArray((Pair<String, Integer>[]) new Pair[list.size()]);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pair<String, Integer>[] getUsersOfGroupsWithScore(final long groupID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			final String statement = "SELECT U.username, GU.score FROM GroupsToUser GU JOIN Users U ON GU.userID = U.userID WHERE GU.groupID = ? ORDER BY GU.score";
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, groupID);
			resultSet = preparedStatement.executeQuery();
			final List<Pair<String, Integer>> list = new ArrayList<Pair<String, Integer>>();
			while (resultSet.next()) {
				final Pair<String, Integer> pair = new ImmutablePair<String, Integer>(resultSet.getString(1), resultSet.getInt(2));
				list.add(pair);
			}
			return list.toArray((Pair<String, Integer>[]) new Pair[list.size()]);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	// ================================================================================
	// SET METHODS
	// ================================================================================

	@Override
	public void setLanguage(final long userID, final String newLanguage) throws DatabaseLayerException {
		updateUserAttributes(userID, User.LANGUAGE, newLanguage);
	}

	@Override
	public void setProfilPictureOfUser(final long userID, final String newProfilePictureURI) throws DatabaseLayerException {
		updateUserAttributes(userID, User.PROFILEPICTURE_URI, newProfilePictureURI);
	}

	@Override
	public void setGroupPicture(final long groupID, final String newGroupPictureURI) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String setClausel = Group.GROUP_PICTURE_URI + " = ?";
			final String whereCondition = Group.GROUP_ID + " = ?";
			final String statement = SQLFactory.buildUpdateStatement(SCHEMA, Group.TABLE_NAME, setClausel, whereCondition);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, groupID);
			preparedStatement.setString(2, newGroupPictureURI);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	@Override
	public void setPasswordHash(final long userID, final String newPasswordHash) throws DatabaseLayerException {
		updateUserAttributes(userID, User.PASSWORD_HASH, newPasswordHash);
	}

	@Override
	public void setChoosedAnswerOfPublicQuestion(final long userID, final long questionID, final long answerID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String setClausel = PublicQuestionToUser.CHOOSEN_ANSWER_ID + " = ?";
			final String whereCondition = PublicQuestionToUser.QUESTION_ID + " = ? AND " + PublicQuestionToUser.USER_ID + " = ?";
			final String statement = SQLFactory.buildUpdateStatement(SCHEMA, PublicQuestionToUser.TABLE_NAME, setClausel, whereCondition);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, answerID);
			preparedStatement.setLong(2, userID);
			preparedStatement.setLong(3, questionID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	@Override
	public void setChoosedAnswerOfPrivateQuestion(final long userID, final long questionID, final long answerID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String setClausel = PrivateQuestionToUser.CHOOSEN_ANSWER_ID + " = ?";
			final String whereCondition = PrivateQuestionToUser.QUESTION_ID + " = ? AND " + PrivateQuestionToUser.USER_ID + " = ?";
			final String statement = SQLFactory.buildUpdateStatement(SCHEMA, PrivateQuestionToUser.TABLE_NAME, setClausel, whereCondition);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, answerID);
			preparedStatement.setLong(2, userID);
			preparedStatement.setLong(3, questionID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	@Override
	public void setSelectedAnswerOfPrivateQuestion(final long questionID, final long answerID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String setClausel = PrivateQuestion.SELECTED_ANSWER_ID + " = ?";
			final String whereCondition = PrivateQuestion.QUESTION_ID + " = ?";
			final String statement = SQLFactory.buildUpdateStatement(SCHEMA, PrivateQuestion.TABLE_NAME, setClausel, whereCondition);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, answerID);
			preparedStatement.setLong(2, questionID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	@Override
	public void setGroupAdmin(final long groupID, final long newAdmminID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String setClausel = Group.ADMIN_ID + " = ?";
			final String whereCondition = Group.GROUP_ID + " = ?";
			final String statement = SQLFactory.buildUpdateStatement(SCHEMA, Group.TABLE_NAME, setClausel, whereCondition);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, newAdmminID);
			preparedStatement.setLong(2, groupID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	@Override
	public void setPhoneNumberHash(final long userID, final String newPhoneNumberHash) throws DatabaseLayerException {
		updateUserAttributes(userID, User.PHONENUMBER_HASH, newPhoneNumberHash);
	}

	@Override
	public void setEmail(final long userID, final String newEmail) throws DatabaseLayerException {
		updateUserAttributes(userID, User.EMAIL, newEmail);
	}

	@Override
	public void setGroupName(final long groupID, final String newGroupName) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String setClausel = Group.GROUP_NAME + " = ?";
			final String whereCondition = Group.GROUP_ID + " = ?";
			final String statement = SQLFactory.buildUpdateStatement(SCHEMA, Group.TABLE_NAME, setClausel, whereCondition);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setString(1, newGroupName);
			preparedStatement.setLong(2, groupID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	@Override
	public void setUsername(final long userID, final String newUsername) throws DatabaseLayerException {
		updateUserAttributes(userID, User.USERNAME, newUsername);
	}

	@Override
	public void setPrivateQuestionToFinish(final long questionID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String setClausel = PrivateQuestion.FINISHED + " = ?";
			final String whereCondition = PrivateQuestion.QUESTION_ID + " = ?";
			final String statement = SQLFactory.buildUpdateStatement(SCHEMA, PrivateQuestion.TABLE_NAME, setClausel, whereCondition);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setBoolean(1, true);
			preparedStatement.setLong(2, questionID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	@Override
	public void setPublicQuestionToFinish(final long questionID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String setClausel = PublicQuestion.FINISHED + " = ?";
			final String whereCondition = PublicQuestion.QUESTION_ID + " = ?";
			final String statement = SQLFactory.buildUpdateStatement(SCHEMA, PublicQuestion.TABLE_NAME, setClausel, whereCondition);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setBoolean(1, true);
			preparedStatement.setLong(2, questionID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	// ================================================================================
	// DELETE METHODS
	// ================================================================================

	// Deletes also the user->question relation and all answers
	@Override
	public void deletePrivateQuestion(final long questionID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String whereCondition = PrivateQuestion.QUESTION_ID + " = ?";
			final String statement = SQLFactory.buildDeleteStatement(SCHEMA, PrivateQuestion.TABLE_NAME, whereCondition);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, questionID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	@Override
	public void deleteUserFromGroup(final long groupID, final long userID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String whereCondition = Group.GROUP_ID + " = ? AND " + User.USER_ID + " = ?";
			final String statement = SQLFactory.buildDeleteStatement(SCHEMA, GroupToUser.TABLE_NAME, whereCondition);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, groupID);
			preparedStatement.setLong(2, userID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	// Delete also all PrivateQuestions, the corresponding answers, the
	// user->question realtion and the user->group relation
	@Override
	public void deleteGroup(final long groupID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String whereCondition = Group.GROUP_ID + " = ?";
			final String statement = SQLFactory.buildDeleteStatement(SCHEMA, Group.TABLE_NAME, whereCondition);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, groupID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	@Override
	public void deleteContact(final long userID, final long contactID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String whereCondition = User.USER_ID + " = ? AND" + Contact.CONTACT_ID + " = ?";
			final String statement = SQLFactory.buildDeleteStatement(SCHEMA, Contact.TABLE_NAME, whereCondition);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, userID);
			preparedStatement.setLong(2, userID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	// ================================================================================
	// SEARCH METHODS
	// ================================================================================

	@Override
	public Group[] searchForGroup(final long userID, final String nameSearchPattern) throws DatabaseLayerException {
		throw new DatabaseLayerException("not implemented");
	}

	@Override
	public PrivateQuestion[] searchForPrivateQuestionInGroup(final long groupID, final String questionSearchPattern) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String searchPattern = "%" + questionSearchPattern + "%";
			final String orderByStatement = "ORDER BY " + PrivateQuestion.QUESTION + " " + SQLFactory.ASCENDING;
			final String whereCondition = PrivateQuestion.GROUP_ID + " = ? AND " + PrivateQuestion.QUESTION + " LIKE ? " + orderByStatement;
			final String statement = SQLFactory.buildSelectAllStatementWithWhereCondition(SCHEMA, PrivateQuestion.TABLE_NAME, whereCondition);
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, groupID);
			preparedStatement.setString(2, searchPattern);
			resultSet = preparedStatement.executeQuery();
			final List<PrivateQuestion> privateQuestions = ResultSetMapper.mapPrivateQuestions(resultSet);
			return privateQuestions.toArray(new PrivateQuestion[privateQuestions.size()]);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public PublicQuestion[] searchForPublicQuestion(final String nameSearchPattern, final String language) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String searchPattern = "%" + nameSearchPattern + "%";
			final String orderByStatement = "ORDER BY " + PublicQuestion.QUESTION + " " + SQLFactory.ASCENDING;
			final String whereCondition = PublicQuestion.QUESTION + " LIKE ? " + "AND " + PublicQuestion.LANGUAGE + " = ? " + orderByStatement;
			final String statement = SQLFactory.buildSelectAllStatementWithWhereCondition(SCHEMA, PublicQuestion.TABLE_NAME, whereCondition);
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setString(1, searchPattern);
			preparedStatement.setString(2, language);
			resultSet = preparedStatement.executeQuery();
			final List<PublicQuestion> publicQuestions = ResultSetMapper.mapPublicQuestions(resultSet);
			return publicQuestions.toArray(new PublicQuestion[publicQuestions.size()]);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public User[] searchUsersByUsername(final String searchPattern) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String seachPatternWithWildcards = "%" + searchPattern + "%";
			final String whereCondition = User.USERNAME + " Like ? ORDER BY " + User.USERNAME + " ASC";
			final String statement = SQLFactory.buildSelectAllStatementWithWhereCondition(SCHEMA, User.TABLE_NAME, whereCondition);
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setString(1, seachPatternWithWildcards);
			resultSet = preparedStatement.executeQuery();
			return getUserArrayFromResultSet(resultSet);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	// ================================================================================
	// HELPER METHODS
	// ================================================================================

	private void updateUserAttributes(final long userID, final String column, final String newValue) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		final ResultSet resultSet = null;
		try {
			final String setClausel = column + " = ?";
			final String whereCondition = User.USER_ID + " = ?";
			final String statement = SQLFactory.buildUpdateStatement(SCHEMA, User.TABLE_NAME, setClausel, whereCondition);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setString(1, newValue);
			preparedStatement.setLong(2, userID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	private String getUserAttributeAsString(final String column, final long userID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String[] columns = new String[] { column };
			final String whereCondition = User.USER_ID + " = ?";
			final String statement = SQLFactory.buildSelectStatementWithWhereCondition(SCHEMA, User.TABLE_NAME, columns, whereCondition);
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, userID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
			return null;
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	private void addAnswerToAnswerTable(final Answer answer, final String table) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final String statement = SQLFactory.buildInsertAllStatement(SCHEMA, table, 3);
			connection = connectionManager.getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, answer.getAnswerID());
			preparedStatement.setLong(2, answer.getQuestionID());
			preparedStatement.setString(3, answer.getAnswer());
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	private User[] getUserArrayFromResultSet(final ResultSet resultSet) throws SQLException {
		final List<User> users = ResultSetMapper.mapUsers(resultSet);
		return users.toArray(new User[users.size()]);
	}

	private Answer getChoseAnswer(final long questionID, final long userID, final String schema, final String table) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String whereCondition = Answer.QUESTION_ID + " = ? AND userID = ?";
			final String statement = SQLFactory.buildSelectAllStatementWithWhereCondition(SCHEMA, table, whereCondition);
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, questionID);
			preparedStatement.setLong(2, userID);
			resultSet = preparedStatement.executeQuery();
			return ResultSetMapper.mapAnswers(resultSet).get(0);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	private String getGroupAttributeAsString(final String column, final long groupID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String[] columns = new String[] { column };
			final String whereCondition = Group.GROUP_ID + " = ?";
			final String statement = SQLFactory.buildSelectStatementWithWhereCondition(SCHEMA, Group.TABLE_NAME, columns, whereCondition);
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, groupID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
			return null;
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	@SuppressWarnings("unchecked")
	private Pair<Answer, Integer>[] getAnswersOfQuestionAndCount(final long questionID, final String questionToUserTable, final String answerTable)
			throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String statement = "SELECT A.*, " + "( SELECT COUNT(*) FROM " + questionToUserTable
					+ " PU WHERE PU.questionID = ? AND PU.choosedAnswerID = A.answerID) counter" + "FROM " + answerTable + " A "
					+ "WHERE A.questionID = ? ORDER BY counter ASC;";
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, questionID);
			preparedStatement.setLong(1, questionID);
			resultSet = preparedStatement.executeQuery();
			final List<Answer> answers = ResultSetMapper.mapAnswers(resultSet);
			final List<ImmutablePair<Answer, Integer>> answersWithCount = new ArrayList<ImmutablePair<Answer, Integer>>();
			for (int answerCounter = 0; answerCounter < answers.size(); answerCounter++) {
				final Answer answer = answers.get(answerCounter);
				resultSet.absolute(answerCounter + 1);
				final Integer count = resultSet.getInt("counter");
				answersWithCount.add(new ImmutablePair<Answer, Integer>(answer, count));
			}
			return answersWithCount.toArray((Pair<Answer, Integer>[]) new Pair[answersWithCount.size()]);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	private PublicQuestion[] getPublicQuestionsOfUserDependingOnStatus(final long userID, final int startIndex, final int quantity,
			final boolean finished) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String statement = "SELECT P.* FROM " + PublicQuestion.TABLE_NAME + " P JOIN " + PublicQuestionToUser.TABLE_NAME
					+ " PQU ON PQU.userID = ? AND PQU.questionID = P.questionID WHERE finihed = ?";
			final String orderByClausel = SQLFactory.buildOrderByStatement("P.createDate " + SQLFactory.ASCENDING);
			final String limitClausel = SQLFactory.buildLimitStatement();
			final String finalStatement = statement + " " + orderByClausel + " " + limitClausel + ";";
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(finalStatement);
			preparedStatement.setLong(1, userID);
			preparedStatement.setBoolean(2, finished);
			preparedStatement.setInt(3, startIndex);
			preparedStatement.setInt(4, quantity);
			resultSet = preparedStatement.executeQuery();
			final List<PublicQuestion> publicQuestions = ResultSetMapper.mapPublicQuestions(resultSet);
			return publicQuestions.toArray(new PublicQuestion[publicQuestions.size()]);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}

	private PrivateQuestion[] getPrivateQuestionsOfUserDependingOnStatus(final long userID, final int startIndex, final int quantity,
			final boolean finished) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			final String statement = "SELECT P.* FROM " + PrivateQuestion.TABLE_NAME + " P JOIN " + PrivateQuestionToUser.TABLE_NAME
					+ " PQU ON PQU.userID = ? AND PQU.questionID = P.questionID WHRER finihed = ?";
			final String orderByClausel = SQLFactory.buildOrderByStatement("P.createDate " + SQLFactory.ASCENDING);
			final String limitClausel = SQLFactory.buildLimitStatement();
			final String finalStatement = statement + " " + orderByClausel + " " + limitClausel + ";";
			connection = connectionManager.getReaderConnection();
			preparedStatement = connection.prepareStatement(finalStatement);
			preparedStatement.setLong(1, userID);
			preparedStatement.setBoolean(2, finished);
			preparedStatement.setInt(3, startIndex);
			preparedStatement.setInt(4, quantity);
			resultSet = preparedStatement.executeQuery();
			final List<PrivateQuestion> privateQuestions = ResultSetMapper.mapPrivateQuestions(resultSet);
			return privateQuestions.toArray(new PrivateQuestion[privateQuestions.size()]);
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, resultSet);
		}
	}
}