package com.askit.queries;

// TODO neue SQLFactory
// TODO notifications
// TODO limit mit ? realisieren
// TODO trigegr for add admin to group after creat egroup & Question to User after Question creation (auch OTQ)
// TODO set und update unterscheiden
// TODO Frage abbrechen -> was passiert und Public und/oder Private
// TODO Sort überall beachten
// TODO langu bei Active/Old PublicQuestions beachten?
// TODO UTC

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
import com.askit.entities.Contact;
import com.askit.entities.Group;
import com.askit.entities.GroupToUser;
import com.askit.entities.PrivateQuestion;
import com.askit.entities.PrivateQuestionToUser;
import com.askit.entities.PublicQuestion;
import com.askit.entities.PublicQuestionToUser;
import com.askit.entities.User;
import com.askit.etc.Constants;
import com.askit.etc.Util;
import com.askit.exception.DatabaseLayerException;
import com.askit.exception.DriverNotFoundException;

public class DatabaseQueryManager implements QueryManager {

	private static final String SCHEMA = Constants.SCHEMA_NAME;

	@Override
	public boolean checkUser(final String username, final String passwordHash) throws DatabaseLayerException {
		try {
			final String whereCondition = User.PASSWORD_HASH + " = ? AND " + User.USERNAME + " = ?";
			final String statement = SQLFactory.buildSelectAllStatementWithWhereCondition(SCHEMA, Constants.TABLE_USERS, whereCondition);
			final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
			preparedStatement.setString(1, passwordHash);
			preparedStatement.setString(2, username);
			final ResultSet result = preparedStatement.executeQuery();
			return result.next();
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	// ================================================================================
	// CREATE METHODS
	// ================================================================================

	@Override
	public void createNewGroup(final Group group) throws DatabaseLayerException {
		try {
			final String[] columns = new String[] { Group.CREATE_DATE, Group.ADMIN_ID, Group.GROUP_NAME, Group.GROUP_PICTURE_URI };
			final String statement = SQLFactory.buildInsertStatement(SCHEMA, Constants.TABLE_GROUPS, columns);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
			preparedStatement.setDate(1, new Date(group.getCreateDate().getTime()));
			preparedStatement.setLong(2, group.getAdminID());
			preparedStatement.setString(3, group.getGroupname());
			preparedStatement.setString(4, group.getGroupPictureURI());
			preparedStatement.executeUpdate();
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void createPublicQuestion(final PublicQuestion question) throws DatabaseLayerException {
		try {
			final String[] columns = new String[] { PublicQuestion.QUESTION, PublicQuestion.ADDITIONAL_INFORMATION, PublicQuestion.HOST_ID,
					PublicQuestion.PICTURE_URI, PublicQuestion.CREATED_DATE, PublicQuestion.END_DATE, PublicQuestion.OPTION_EXTENSION,
					PublicQuestion.LANGUAGE };
			final String statement = SQLFactory.buildInsertStatement(SCHEMA, Constants.TABLE_PRIVATE_QUESTIONS, columns);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
			preparedStatement.setString(1, question.getQuestion());
			preparedStatement.setString(2, question.getAdditionalInformation());
			preparedStatement.setLong(3, question.getHostID());
			preparedStatement.setString(4, question.getPictureURI());
			preparedStatement.setDate(5, new Date(question.getCreateDate().getTime()));
			preparedStatement.setDate(6, new Date(question.getEndDate().getTime()));
			preparedStatement.setBoolean(7, question.getOptionExtension());
			preparedStatement.setString(8, question.getLanguage());
			preparedStatement.executeUpdate();
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void createNewPrivateQuestionInGroup(final PrivateQuestion question) throws DatabaseLayerException {
		try {
			final String[] columns = new String[] { PrivateQuestion.QUESTION, PrivateQuestion.ADDITIONAL_INFORMATION, PrivateQuestion.HOST_ID,
					PrivateQuestion.GROUP_ID, PrivateQuestion.PICTURE_URI, PrivateQuestion.CREATED_DATE, PrivateQuestion.END_DATE,
					PrivateQuestion.OPTION_EXTENSION, PrivateQuestion.DEFINITION_OF_END, PrivateQuestion.SUM_OF_USERS_TO_ANSWER,
					PrivateQuestion.LANGUAGE, PrivateQuestion.IS_BET };
			final String statement = SQLFactory.buildInsertStatement(SCHEMA, Constants.TABLE_PRIVATE_QUESTIONS, columns);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
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
		} catch (SQLException | DriverNotFoundException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void createOneTimeQuestion(final PrivateQuestion question) throws DatabaseLayerException {
		try {
			final String[] columns = new String[] { PrivateQuestion.QUESTION, PrivateQuestion.ADDITIONAL_INFORMATION, PrivateQuestion.HOST_ID,
					PrivateQuestion.PICTURE_URI, PrivateQuestion.CREATED_DATE, PrivateQuestion.END_DATE, PrivateQuestion.OPTION_EXTENSION,
					PrivateQuestion.DEFINITION_OF_END, PrivateQuestion.SUM_OF_USERS_TO_ANSWER, PrivateQuestion.LANGUAGE, PrivateQuestion.IS_BET };
			final String statement = SQLFactory.buildInsertStatement(SCHEMA, Constants.TABLE_PRIVATE_QUESTIONS, columns);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
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
		} catch (SQLException | DriverNotFoundException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void createUser(final User user) throws DatabaseLayerException {
		try {
			final String[] columns = new String[] { User.PASSWORD_HASH, User.PHONENUMBER_HASH, User.USERNAME, User.ACCESSION_DATE,
					User.PROFILEPICTURE_URI, User.LANGUAGE };
			final String statement = SQLFactory.buildInsertStatement(SCHEMA, Constants.TABLE_USERS, columns);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
			preparedStatement.setString(1, user.getPasswordHash());
			preparedStatement.setString(2, user.getPhoneNumberHash());
			preparedStatement.setString(3, user.getUsername());
			preparedStatement.setDate(4, new Date(user.getAccessionDate().getTime()));
			preparedStatement.setString(5, user.getProfilePictureURI());
			preparedStatement.setString(6, user.getLanguage());
			preparedStatement.executeUpdate();
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	// ================================================================================
	// ADD METHODS
	// ================================================================================s

	@Override
	public void addUserToGroup(final long groupID, final long userID) throws DatabaseLayerException {
		try {
			final String[] columns = new String[] { GroupToUser.GROUP_ID, GroupToUser.USER_ID };
			final String statement = SQLFactory.buildInsertStatement(SCHEMA, Constants.TABLE_GROUPS_TO_USERS, columns);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
			preparedStatement.setLong(1, groupID);
			preparedStatement.setLong(2, userID);
			preparedStatement.executeUpdate();
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void addContact(final long userIDOfUser, final long userIDofContact) throws DatabaseLayerException {
		try {
			final String statement = SQLFactory.buildInsertAllStatement(SCHEMA, Constants.TABLE_CONTACTS, 2);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
			preparedStatement.setLong(1, userIDOfUser);
			preparedStatement.setLong(2, userIDofContact);
			preparedStatement.executeUpdate();
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void addUserToOneTimeQuestion(final long userID, final long questionID) throws DatabaseLayerException {
		try {
			final String[] columns = new String[] { PrivateQuestionToUser.QUESTION_ID, PrivateQuestionToUser.USER_ID };
			final String statement = SQLFactory.buildInsertStatement(SCHEMA, Constants.TABLE_PRIVATE_QUESTIONS_TO_USERS, columns);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
			preparedStatement.setLong(1, questionID);
			preparedStatement.setLong(2, userID);
			preparedStatement.executeUpdate();
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void addAnswerToPublicQuestion(final Answer answer) throws DatabaseLayerException {
		try {
			addAnswerToAnswerTable(answer, Constants.TABLE_ANSWERS_PUBLIC_QUESTIONS);
		} catch (SQLException | DriverNotFoundException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void addAnswerToPrivateQuestion(final Answer answer) throws DatabaseLayerException {
		try {
			addAnswerToAnswerTable(answer, Constants.TABLE_ANSWERS_PRIVATE_QUESTIONS);
		} catch (SQLException | DriverNotFoundException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void addUserToPublicQuestion(final long questionID, final long userID) throws DatabaseLayerException {
		try {
			final String[] columns = new String[] { PublicQuestionToUser.QUESTION_ID, PublicQuestionToUser.USER_ID };
			final String statement = SQLFactory.buildInsertStatement(SCHEMA, Constants.TABLE_PUBLIC_QUESTIONS_TO_USERS, columns);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
			preparedStatement.setLong(1, questionID);
			preparedStatement.setLong(2, userID);
			preparedStatement.executeUpdate();
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	// ================================================================================
	// GET METHODS
	// ================================================================================

	@Override
	public PublicQuestion[] getPublicQuestions(final int startIndex, final int quantity, final String language) throws DatabaseLayerException {
		try {
			final String whereCondition = PublicQuestion.LANGUAGE + " = ?";
			final String orderByClausel = SQLFactory.buildOrderByStatement(PublicQuestion.CREATED_DATE + " " + SQLFactory.ASCENDING);
			final String limitClausel = SQLFactory.buildLimitStatement(startIndex, quantity);
			final String statement = SQLFactory.buildSelectAllStatementWithWhereConditionLimitClauselOrderByClausel(SCHEMA,
					Constants.TABLE_PUBLIC_QUESTIONS, whereCondition, orderByClausel, limitClausel);
			final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
			preparedStatement.setString(1, language);
			final ResultSet resultSet = preparedStatement.executeQuery();
			final List<PublicQuestion> publicQuestions = ResultSetMapper.mapPublicQuestions(resultSet);
			return publicQuestions.toArray(new PublicQuestion[publicQuestions.size()]);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public PublicQuestion getPublicQuestion(final long questionID) throws DatabaseLayerException {
		try {
			final String whereCondition = PublicQuestion.QUESTION_ID + " = ?";
			final String statement = SQLFactory.buildSelectAllStatementWithWhereCondition(SCHEMA, Constants.TABLE_PUBLIC_QUESTIONS, whereCondition);
			final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
			preparedStatement.setLong(1, questionID);
			final ResultSet resultSet = preparedStatement.executeQuery();
			final List<PublicQuestion> publicQuestions = ResultSetMapper.mapPublicQuestions(resultSet);
			return publicQuestions.get(0);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public PrivateQuestion getPrivateQuestion(final long questionID) throws DatabaseLayerException {
		try {
			final String whereCondition = PrivateQuestion.QUESTION_ID + " = ?";
			final String statement = SQLFactory.buildSelectAllStatementWithWhereCondition(SCHEMA, Constants.TABLE_PRIVATE_QUESTIONS, whereCondition);
			final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
			preparedStatement.setLong(1, questionID);
			final ResultSet resultSet = preparedStatement.executeQuery();
			final List<PrivateQuestion> privateQuestions = ResultSetMapper.mapPrivateQuestions(resultSet);
			return privateQuestions.get(0);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public PrivateQuestion[] getQuestionsOfGroup(final long groupID, final int startIndex, final int quantity) throws DatabaseLayerException {
		try {
			final String orderByClausel = SQLFactory.buildOrderByStatement(PrivateQuestion.CREATED_DATE + " " + SQLFactory.ASCENDING);
			final String limitClausel = SQLFactory.buildLimitStatement(startIndex, quantity);
			final String whereCondition = PrivateQuestion.GROUP_ID + " = ?";
			final String statement = SQLFactory.buildSelectAllStatementWithWhereConditionLimitClauselOrderByClausel(SCHEMA,
					Constants.TABLE_PRIVATE_QUESTIONS, whereCondition, orderByClausel, limitClausel);
			final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
			preparedStatement.setLong(1, groupID);
			final ResultSet resultSet = preparedStatement.executeQuery();
			final List<PrivateQuestion> privateQuestions = ResultSetMapper.mapPrivateQuestions(resultSet);
			return privateQuestions.toArray(new PrivateQuestion[privateQuestions.size()]);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public String getUsername(final long userID) throws DatabaseLayerException {
		try {
			final ResultSet resultSet = getUserAttribute(User.USERNAME, userID);
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
			return null;
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public User[] getUsersOfPublicQuestion(final long questionID) throws DatabaseLayerException {
		try {
			final String statement = "SELECT U.* FROM Users U JOIN PublicQuestionsToUsers P ON questionID = ? AND P.userID = U.userID ORDER BY U.username";
			final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
			preparedStatement.setLong(1, questionID);
			return getUserArrayFromReaderPreparedStatement(preparedStatement);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public User[] getUsersOfPrivateQuestion(final long questionID) throws DatabaseLayerException {
		try {
			final String statement = "SELECT U.* FROM Users U JOIN PrivateQuestionsToUsers P ON questionID = ? AND P.userID = U.userID ORDER BY U.username;";
			final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
			preparedStatement.setLong(1, questionID);
			return getUserArrayFromReaderPreparedStatement(preparedStatement);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public User[] getUsersOfAnswerPrivateQuestion(final long answerID) throws DatabaseLayerException {
		try {
			final String statement = "SELECT U.* FROM Users U JOIN PrivateQuestionsToUsers P ON choosedAnswerID = ? AND P.userID = U.userID ORDER BY U.username;";
			final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
			preparedStatement.setLong(1, answerID);
			return getUserArrayFromReaderPreparedStatement(preparedStatement);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public User[] getUsersOfAnswerPublicQuestion(final long answerID) throws DatabaseLayerException {
		try {
			final String statement = "SELECT U.* FROM Users U JOIN PublicQuestionsToUsers P ON choosedAnswerID = ? AND P.userID = U.userID ORDER BY U.username;";
			final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
			preparedStatement.setLong(1, answerID);
			return getUserArrayFromReaderPreparedStatement(preparedStatement);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public User[] getUsersOfGroup(final long groupID) throws DatabaseLayerException {
		try {
			final String statement = "SELECT U.* FROM Users U JOIN GroupsToUsers G ON groupID = ? AND G.userID = U.userID ORDER BY U.username;";
			final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
			preparedStatement.setLong(1, groupID);
			return getUserArrayFromReaderPreparedStatement(preparedStatement);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public Long getUserScoreOfGlobal(final long userID) throws DatabaseLayerException {
		try {
			final ResultSet resultSet = getUserAttribute(User.SCORE_OF_GLOBAL, userID);
			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
			return null;
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public Long getUserScoreInGroup(final long userID, final long groupID) throws DatabaseLayerException {
		try {
			final String whereCondition = GroupToUser.GROUP_ID + " = ? AND " + GroupToUser.USER_ID + " = ?";
			final String statement = SQLFactory.buildSelectStatementWithWhereCondition(SCHEMA, Constants.TABLE_GROUPS_TO_USERS,
					new String[] { GroupToUser.SCORE }, whereCondition);
			final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
			preparedStatement.setLong(1, groupID);
			preparedStatement.setLong(2, userID);
			final ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
			return null;
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public String getPhoneNumberHash(final long userID) throws DatabaseLayerException {
		try {
			final ResultSet resultSet = getUserAttribute(User.PHONENUMBER_HASH, userID);
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
			return null;
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public Answer getChoseAnswerInPublicQuestion(final long questionID, final long userID) throws DatabaseLayerException {
		try {
			return getChoseAnswer(questionID, userID, SCHEMA, Constants.TABLE_PUBLIC_QUESTIONS_TO_USERS);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public Answer getChoseAnswerInPrivateQuestion(final long questionID, final long userID) throws DatabaseLayerException {
		try {
			return getChoseAnswer(questionID, userID, SCHEMA, Constants.TABLE_PRIVATE_QUESTIONS_TO_USERS);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public Answer getSelectedAnswerInPrivateQuestion(final long questionID) throws DatabaseLayerException {
		try {
			final String statement = "SELECT * FROM AnswersPrivateQuestions WHERE answerID = ( SELECT selectedAnswerID FROM PrivateQuestions WHERE questionID = ? );";
			final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
			preparedStatement.setLong(1, questionID);
			return mapSingleAnswerToObject(preparedStatement);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public Long getRankingInGroup(final long userID, final long groupID) throws DatabaseLayerException {
		try {
			final String statement = "SELECT * FROM GroupsToUsers WHERE groupID = ? ORDER BY score ASC;";
			final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
			preparedStatement.setLong(1, groupID);
			final ResultSet resultSet = preparedStatement.executeQuery();
			final int resultSetSize = Util.getSizeOfResultSet(resultSet);
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
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public String getPasswordHash(final long userID) throws DatabaseLayerException {
		try {
			final ResultSet resultSet = getUserAttribute(User.PHONENUMBER_HASH, userID);
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
			return null;
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public String getLanguage(final long userID) throws DatabaseLayerException {
		try {
			final ResultSet resultSet = getUserAttribute(User.LANGUAGE, userID);
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
			return null;
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public String getProfilePictureURI(final long userID) throws DatabaseLayerException {
		try {
			final ResultSet resultSet = getUserAttribute(User.PROFILEPICTURE_URI, userID);
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
			return null;
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public String getGroupPictureURI(final long groupID) throws DatabaseLayerException {
		try {
			return getGroupAttribute(Group.GROUP_PICTURE_URI, groupID);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public String getGroupName(final long groupID) throws DatabaseLayerException {
		try {
			return getGroupAttribute(Group.GROUP_NAME, groupID);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public PrivateQuestion[] getOldPrivateQuestions(final long groupID, final int startIndex, final int quantity) {
		// TODO von nur einer Gruppe??
		// TODO sort
		return null;
	}

	@Override
	public Pair<Answer, Integer>[] getAnswersOfPublicQuestionAndCount(final long questionID) throws DatabaseLayerException {
		try {
			return getAnswersOfQuestionAndCount(questionID, Constants.TABLE_PUBLIC_QUESTIONS_TO_USERS, Constants.TABLE_ANSWERS_PUBLIC_QUESTIONS);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public Pair<Answer, Integer>[] getAnswersOfPrivateQuestionAndCount(final long questionID) throws DatabaseLayerException {
		try {
			return getAnswersOfQuestionAndCount(questionID, Constants.TABLE_PRIVATE_QUESTIONS_TO_USERS, Constants.TABLE_ANSWERS_PRIVATE_QUESTIONS);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public PublicQuestion[] getActivePublicQuestionsOfUser(final long userID, final int startIndex, final int quantity) throws DatabaseLayerException {
		try {
			return getPublicQuestionsOfUserDependingOnStatus(userID, startIndex, quantity, false);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public PrivateQuestion[] getActivePrivateQuestionsOfUser(final long userID, final int startIndex, final int quantity)
			throws DatabaseLayerException {
		try {
			return getPrivateQuestionsOfUserDependingOnStatus(userID, startIndex, quantity, true);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public PublicQuestion[] getOldPublicQuestionsOfUser(final long userID, final int startIndex, final int quantity) throws DatabaseLayerException {
		try {
			return getPublicQuestionsOfUserDependingOnStatus(userID, startIndex, quantity, false);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}

	}

	@Override
	public PrivateQuestion[] getOldPrivateQuestionsOfUser(final long userID, final int startIndex, final int quantity) throws DatabaseLayerException {
		try {
			return getPrivateQuestionsOfUserDependingOnStatus(userID, startIndex, quantity, true);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public Pair<String, Integer>[] getAllGroupScoresAndGlobalScoreOfUser(final long userID) {
		// TODO sort

		final String statement = "SELECT G.*, GU.score FROM GroupsToUsers GU WHERE userID = ? JOIN Groups G ON G.groupID = GU.groupID";

		return null;
	}

	@Override
	public Pair<User, Integer>[] getUsersOfGroupsWithScore(final long groupID) {
		// TODO todo
		// TODO sort
		return null;
	}

	// ================================================================================
	// SET METHODS
	// ================================================================================

	@Override
	public void setLanguage(final long userID, final String newLanguage) throws DatabaseLayerException {
		try {
			updateUserAttributes(userID, User.LANGUAGE, newLanguage);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void setProfilPictureOfUser(final long userID, final String newProfilePictureURI) throws DatabaseLayerException {
		try {
			updateUserAttributes(userID, User.PROFILEPICTURE_URI, newProfilePictureURI);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void setGroupPicture(final long groupID, final String newGroupPictureURI) throws DatabaseLayerException {
		try {
			final String setClausel = Group.GROUP_PICTURE_URI + " = ?";
			final String whereCondition = Group.GROUP_ID + " = ?";
			final String statement = SQLFactory.buildUpdateStatement(SCHEMA, Constants.TABLE_GROUPS, setClausel, whereCondition);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
			preparedStatement.setLong(1, groupID);
			preparedStatement.setString(2, newGroupPictureURI);
			preparedStatement.executeUpdate();
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void setPasswordHash(final long userID, final String newPasswordHash) throws DatabaseLayerException {
		try {
			updateUserAttributes(userID, User.PASSWORD_HASH, newPasswordHash);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void setChoosedAnswerOfPublicQuestion(final long userID, final long questionID, final long answerID) throws DatabaseLayerException {
		try {
			final String setClausel = PublicQuestionToUser.CHOOSEN_ANSWER_ID + " = ?";
			final String whereCondition = PublicQuestionToUser.QUESTION_ID + " = ? AND " + PublicQuestionToUser.USER_ID + " = ?";
			final String statement = SQLFactory.buildUpdateStatement(SCHEMA, Constants.TABLE_PUBLIC_QUESTIONS_TO_USERS, setClausel, whereCondition);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
			preparedStatement.setLong(1, answerID);
			preparedStatement.setLong(2, userID);
			preparedStatement.setLong(3, questionID);
			preparedStatement.executeUpdate();
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void setChoosedAnswerOfPrivateQuestion(final long userID, final long questionID, final long answerID) throws DatabaseLayerException {
		try {
			final String setClausel = PrivateQuestionToUser.CHOOSEN_ANSWER_ID + " = ?";
			final String whereCondition = PrivateQuestionToUser.QUESTION_ID + " = ? AND " + PrivateQuestionToUser.USER_ID + " = ?";
			final String statement = SQLFactory.buildUpdateStatement(SCHEMA, Constants.TABLE_PRIVATE_QUESTIONS_TO_USERS, setClausel, whereCondition);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
			preparedStatement.setLong(1, answerID);
			preparedStatement.setLong(2, userID);
			preparedStatement.setLong(3, questionID);
			preparedStatement.executeUpdate();
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void setSelectedAnswerOfPrivateQuestion(final long questionID, final long answerID) throws DatabaseLayerException {
		try {
			final String setClausel = PrivateQuestion.SELECTED_ANSWER_ID + " = ?";
			final String whereCondition = PrivateQuestion.QUESTION_ID + " = ?";
			final String statement = SQLFactory.buildUpdateStatement(SCHEMA, Constants.TABLE_PRIVATE_QUESTIONS, setClausel, whereCondition);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
			preparedStatement.setLong(1, answerID);
			preparedStatement.setLong(2, questionID);
			preparedStatement.executeUpdate();
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void setGroupAdmin(final long groupID, final long newAdmminID) throws DatabaseLayerException {
		try {
			final String setClausel = Group.ADMIN_ID + " = ?";
			final String whereCondition = Group.GROUP_ID + " = ?";
			final String statement = SQLFactory.buildUpdateStatement(SCHEMA, Constants.TABLE_GROUPS, setClausel, whereCondition);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
			preparedStatement.setLong(1, newAdmminID);
			preparedStatement.setLong(2, groupID);
			preparedStatement.executeUpdate();
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void setPhoneNumberHash(final long userID, final String newPhoneNumberHash) throws DatabaseLayerException {
		try {
			updateUserAttributes(userID, User.PHONENUMBER_HASH, newPhoneNumberHash);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void setGroupName(final long groupID, final String newGroupName) throws DatabaseLayerException {
		try {
			final String setClausel = Group.GROUP_NAME + " = ?";
			final String whereCondition = Group.GROUP_ID + " = ?";
			final String statement = SQLFactory.buildUpdateStatement(SCHEMA, Constants.TABLE_GROUPS, setClausel, whereCondition);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
			preparedStatement.setString(1, newGroupName);
			preparedStatement.setLong(2, groupID);
			preparedStatement.executeUpdate();
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void setUsername(final long userID, final String newUsername) throws DatabaseLayerException {
		try {
			updateUserAttributes(userID, User.USERNAME, newUsername);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void setPrivateQuestionToFinish(final long questionID) throws DatabaseLayerException {
		try {
			final String setClausel = PrivateQuestion.FINISHED + " = ?";
			final String whereCondition = PrivateQuestion.QUESTION_ID + " = ?";
			final String statement = SQLFactory.buildUpdateStatement(SCHEMA, Constants.TABLE_PRIVATE_QUESTIONS, setClausel, whereCondition);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
			preparedStatement.setBoolean(1, true);
			preparedStatement.setLong(2, questionID);
			preparedStatement.executeUpdate();
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	// ================================================================================
	// DELETE METHODS
	// ================================================================================

	@Override
	public void deletePrivateQuestion(final long questionID) throws DatabaseLayerException {
		try {
			final String whereCondition = PrivateQuestion.QUESTION_ID + " = ?";
			final String statement = SQLFactory.buildDeleteStatement(SCHEMA, Constants.TABLE_PRIVATE_QUESTIONS, whereCondition);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
			preparedStatement.setLong(1, questionID);
			preparedStatement.executeUpdate();
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void deleteUserFromGroup(final long groupID, final long userID) throws DatabaseLayerException {
		try {
			final String whereCondition = Group.GROUP_ID + " = ? AND " + User.USER_ID + " = ?";
			final String statement = SQLFactory.buildDeleteStatement(SCHEMA, Constants.TABLE_GROUPS_TO_USERS, whereCondition);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
			preparedStatement.setLong(1, groupID);
			preparedStatement.setLong(2, userID);
			preparedStatement.executeUpdate();
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void deleteGroup(final long groupID) throws DatabaseLayerException {
		try {
			final String whereCondition = Group.GROUP_ID + " = ?";
			final String statement = SQLFactory.buildDeleteStatement(SCHEMA, Constants.TABLE_GROUPS, whereCondition);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
			preparedStatement.setLong(1, groupID);
			preparedStatement.executeUpdate();
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public void deleteContact(final long userID, final long contactID) throws DatabaseLayerException {
		try {
			final String whereCondition = User.USER_ID + " = ? AND" + Contact.CONTACT_ID + " = ?";
			final String statement = SQLFactory.buildDeleteStatement(SCHEMA, Constants.TABLE_CONTACTS, whereCondition);
			final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
			preparedStatement.setLong(1, userID);
			preparedStatement.setLong(2, userID);
			preparedStatement.executeUpdate();
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
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
		try {
			final String searchPattern = "%" + questionSearchPattern + "%";
			final String whereCondition = PrivateQuestion.GROUP_ID + " = ? AND " + PrivateQuestion.QUESTION + " LIKE ?";
			final String statement = SQLFactory.buildSelectAllStatementWithWhereCondition(SCHEMA, Constants.TABLE_PRIVATE_QUESTIONS, whereCondition);
			final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
			preparedStatement.setLong(1, groupID);
			preparedStatement.setString(2, searchPattern);
			final ResultSet resultSet = preparedStatement.executeQuery();
			final List<PrivateQuestion> privateQuestions = ResultSetMapper.mapPrivateQuestions(resultSet);
			return privateQuestions.toArray(new PrivateQuestion[privateQuestions.size()]);
		} catch (SQLException | DriverNotFoundException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public PublicQuestion[] searchForPublicQuestion(final String nameSearchPattern) throws DatabaseLayerException {
		try {
			final String searchPattern = "%" + nameSearchPattern + "%";
			final String whereCondition = PublicQuestion.QUESTION + " LIKE ?";
			final String statement = SQLFactory.buildSelectAllStatementWithWhereCondition(SCHEMA, Constants.TABLE_PUBLIC_QUESTIONS, whereCondition);
			final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
			preparedStatement.setString(1, searchPattern);
			final ResultSet resultSet = preparedStatement.executeQuery();
			final List<PublicQuestion> publicQuestions = ResultSetMapper.mapPublicQuestions(resultSet);
			return publicQuestions.toArray(new PublicQuestion[publicQuestions.size()]);
		} catch (SQLException | DriverNotFoundException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	@Override
	public User[] searchUsersByUsername(final String searchPattern) throws DatabaseLayerException {
		try {
			final String seachPatternWithWildcards = "%" + searchPattern + "%";
			final String whereCondition = User.USERNAME + " Like ? ORDER BY " + User.USERNAME + " ASC"; // TODO
			final String statement = SQLFactory.buildSelectAllStatementWithWhereCondition(SCHEMA, Constants.TABLE_USERS, whereCondition);
			final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
			preparedStatement.setString(1, seachPatternWithWildcards);
			return getUserArrayFromReaderPreparedStatement(preparedStatement);
		} catch (DriverNotFoundException | SQLException exception) {
			throw new DatabaseLayerException(exception);
		}
	}

	// ================================================================================
	// HELPER METHODS
	// ================================================================================

	private void updateUserAttributes(final long userID, final String column, final String newValue) throws SQLException, DriverNotFoundException {
		final String setClausel = column + " = ?";
		final String whereCondition = User.USER_ID + " = ?";
		final String statement = SQLFactory.buildUpdateStatement(SCHEMA, Constants.TABLE_USERS, setClausel, whereCondition);
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setString(1, newValue);
		preparedStatement.setLong(2, userID);
		preparedStatement.executeUpdate();
	}

	private ResultSet getUserAttribute(final String column, final long userID) throws SQLException, DriverNotFoundException {
		final String[] columns = new String[] { column };
		final String whereCondition = User.USER_ID + " = ?";
		final String statement = SQLFactory.buildSelectStatementWithWhereCondition(SCHEMA, Constants.TABLE_USERS, columns, whereCondition);
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, userID);
		return preparedStatement.executeQuery();
	}

	private void addAnswerToAnswerTable(final Answer answer, final String table) throws SQLException, DriverNotFoundException {
		final String statement = SQLFactory.buildInsertAllStatement(SCHEMA, table, 3);
		final PreparedStatement preparedStatement = getWriterPreparedStatement(statement);
		preparedStatement.setLong(1, answer.getAnswerID());
		preparedStatement.setLong(2, answer.getQuestionID());
		preparedStatement.setString(3, answer.getAnswer());
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

	private User[] getUserArrayFromReaderPreparedStatement(final PreparedStatement preparedStatement) throws SQLException {
		final ResultSet resultSet = preparedStatement.executeQuery();
		final List<User> users = ResultSetMapper.mapUsers(resultSet);
		return users.toArray(new User[users.size()]);
	}

	private Answer getChoseAnswer(final long questionID, final long userID, final String schema, final String table) throws SQLException,
			DriverNotFoundException {
		final String whereCondition = Answer.QUESTION_ID + " = ? AND userID = ?";
		final String statement = SQLFactory.buildSelectAllStatementWithWhereCondition(SCHEMA, table, whereCondition);
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, questionID);
		preparedStatement.setLong(2, userID);
		return mapSingleAnswerToObject(preparedStatement);
	}

	private Answer mapSingleAnswerToObject(final PreparedStatement preparedStatement) throws SQLException {
		final ResultSet resultSet = preparedStatement.executeQuery();
		final List<Answer> answers = ResultSetMapper.mapAnswers(resultSet);
		return answers.get(0);
	}

	private String getGroupAttribute(final String column, final long groupID) throws SQLException, DriverNotFoundException {
		final String[] columns = new String[] { column };
		final String whereCondition = Group.GROUP_ID + " = ?";
		final String statement = SQLFactory.buildSelectStatementWithWhereCondition(SCHEMA, Constants.TABLE_GROUPS, columns, whereCondition);
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, groupID);
		final ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			return resultSet.getString(1);
		}
		return null;
	}

	// TODO empty
	@SuppressWarnings("unchecked")
	private Pair<Answer, Integer>[] getAnswersOfQuestionAndCount(final long questionID, final String questionToUserTable, final String answerTable)
			throws SQLException, DriverNotFoundException {
		final String statement = "SELECT A.*, " + "( SELECT COUNT(*) FROM " + questionToUserTable
				+ " PU WHERE PU.questionID = ? AND PU.choosedAnswerID = A.answerID) counter" + "FROM " + answerTable + " A "
				+ "WHERE A.questionID = ? ORDER BY counter ASC;";
		final PreparedStatement preparedStatement = getReaderPreparedStatement(statement);
		preparedStatement.setLong(1, questionID);
		preparedStatement.setLong(1, questionID);
		final ResultSet resultSet = preparedStatement.executeQuery();
		final List<Answer> answers = ResultSetMapper.mapAnswers(resultSet);
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
			final boolean finished) throws SQLException, DriverNotFoundException {
		final String statement = "SELECT P.* FROM " + Constants.TABLE_PUBLIC_QUESTIONS + " P JOIN " + Constants.TABLE_PUBLIC_QUESTIONS_TO_USERS
				+ " PQU ON PQU.userID = ? AND PQU.questionID = P.questionID WHERE finihed = ?";
		final ResultSet resultSet = getQuestionsOfUserDependingOnStatus(userID, startIndex, quantity, finished, statement);
		final List<PublicQuestion> publicQuestions = ResultSetMapper.mapPublicQuestions(resultSet);
		return publicQuestions.toArray(new PublicQuestion[publicQuestions.size()]);
	}

	private PrivateQuestion[] getPrivateQuestionsOfUserDependingOnStatus(final long userID, final int startIndex, final int quantity,
			final boolean finished) throws SQLException, DriverNotFoundException {
		final String statement = "SELECT P.* FROM " + Constants.TABLE_PRIVATE_QUESTIONS + " P JOIN " + Constants.TABLE_PRIVATE_QUESTIONS_TO_USERS
				+ " PQU ON PQU.userID = ? AND PQU.questionID = P.questionID WHRER finihed = ?";
		final ResultSet resultSet = getQuestionsOfUserDependingOnStatus(userID, startIndex, quantity, finished, statement);
		final List<PrivateQuestion> privateQuestions = ResultSetMapper.mapPrivateQuestions(resultSet);
		return privateQuestions.toArray(new PrivateQuestion[privateQuestions.size()]);
	}

	private ResultSet getQuestionsOfUserDependingOnStatus(final long userID, final int startIndex, final int quantity, final boolean finished,
			final String statement) throws SQLException, DriverNotFoundException {
		final String orderByClausel = SQLFactory.buildOrderByStatement("P.createDate " + SQLFactory.ASCENDING);
		final String limitClausel = SQLFactory.buildLimitStatement(startIndex, quantity);
		final String finalStatement = statement + " " + orderByClausel + " " + limitClausel + ";";
		final PreparedStatement preparedStatement = getReaderPreparedStatement(finalStatement);
		preparedStatement.setLong(1, userID);
		preparedStatement.setBoolean(2, finished);
		return preparedStatement.executeQuery();
	}
}