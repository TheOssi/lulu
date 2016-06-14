package com.askit.database.trigger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.askit.database.ConnectionManager;
import com.askit.database.sqlHelper.SQLUtil;
import com.askit.exception.DatabaseLayerException;

public class Trigger {

	public static void setPointsForRightAnswerInPrivateBet(final long selectedAnswerID, final long questionID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final int pointsToAdd = Points.POINTS_PRIVATE_BET_RIGHT_ANSWER.getPoints();
			connection = ConnectionManager.getInstance().getWriterConnection();
			final String statement = " UPDATE APP.GroupsToUsers G INNER JOIN APP.PrivateQuestionsToUsers P ON P.choosedAnswerID = ? AND P.questionID = ?"
					+ " SET score = (score + ?)  WHERE G.groupID = ( SELECT groupID FROM PrivateQuestions WHERE questionID = 1 ) AND G.userID = P.userID;";
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, selectedAnswerID);
			preparedStatement.setLong(2, questionID);
			preparedStatement.setInt(3, pointsToAdd);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			SQLUtil.rollbackSilently(connection);
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	public static void setPointsForCreatePublicQuestion(final long userID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final int pointsToAdd = Points.POINTS_PUBLIC_QUESTION_CREATE.getPoints();
			connection = ConnectionManager.getInstance().getWriterConnection();
			final String statement = "UPDATE APP.Users SET scoreOfGlobal = (scoreOfGlobal + ? ) WHERE  userID = ?;";
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setInt(1, pointsToAdd);
			preparedStatement.setLong(2, userID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			SQLUtil.rollbackSilently(connection);
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	public static void setPointsForAnsweringAPublicQuestion(final long userID, final long questionID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			final int pointsToAddForUser = Points.POINTS_PUBLIC_QUESTION_ANSWERED_BY_USER.getPoints();
			connection = ConnectionManager.getInstance().getWriterConnection();
			final String statement = "UPDATE APP.Users SET scoreOfGlobal = (scoreOfGlobal + ? ) WHERE userID = ?";
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setInt(1, pointsToAddForUser);
			preparedStatement.setLong(2, userID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			SQLUtil.rollbackSilently(connection);
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}

		try {
			final int pointsToAddForAdmin = Points.POINTS_PUBLIC_QUESTION_ANSWERED_FOR_HOST.getPoints();
			connection = ConnectionManager.getInstance().getWriterConnection();
			final String statement = "UPDATE APP.Users SET scoreOfGlobal = (scoreOfGlobal + ? ) WHERE userID = (SELECT hostID FROM PublicQuestion WHERE questionID = ?);";
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setInt(1, pointsToAddForAdmin);
			preparedStatement.setLong(2, questionID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			SQLUtil.rollbackSilently(connection);
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	public static void setPointsForAnsweringAPrivateQuestion(final long questionID, final long userID) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final int pointsToAdd = Points.POINTS_PRIVATE_QUESTION_ANSWERED.getPoints();
			connection = ConnectionManager.getInstance().getWriterConnection();
			final String statement = "UPDATE APP.GroupsToUsers SET score = (score + ?) WHERE groupID = ( SELECT groupID FROM PrivateQuestions WHERE questionID = "
					+ "?) AND userID = ?;";
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setInt(1, pointsToAdd);
			preparedStatement.setLong(2, questionID);
			preparedStatement.setLong(3, userID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			SQLUtil.rollbackSilently(connection);
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	public static void afterAnsweringCheckForEndOfQuestionAndSetPoints(final long questionID) throws DatabaseLayerException {
		ResultSet resultSet = null;
		String statement = "";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			// get definition of end
			statement = " SELECT definitionOfEnd FROM final APP.PrivateQuestions WHERE questionID = ? INTO l_definitionOfEnd;";
			resultSet = executeStatementWithQuestionID(questionID, statement);
			resultSet.next();
			final int definitionOfEnd = resultSet.getInt(1);

			if (definitionOfEnd != DefinitionOfEnd.END_BY_TIME.getID()) {

				// Count all Users whitch have allready answerd the questionID
				statement = "SELECT Count(*) FROM APP.PrivateQuestionsToUsers WHERE questionID = ? AND" + "choosedAnswerID <> 0;";
				resultSet = executeStatementWithQuestionID(questionID, statement);
				resultSet.next();
				final int allreadyAnswered = resultSet.getInt(1);

				// Count all Users of this question
				statement = "SELECT Count(*) FROM APP.PrivateQuestionsToUsers WHERE questionID = ?;";
				resultSet = executeStatementWithQuestionID(questionID, statement);
				resultSet.next();
				final int countAllUsers = resultSet.getInt(1);

				boolean allUsersHaveAnswered = false;
				boolean sumOfAnsweredUsersReached = false;

				if (countAllUsers == allreadyAnswered) {
					allUsersHaveAnswered = true;
				}

				if (definitionOfEnd == DefinitionOfEnd.END_BY_SUM_ANERWERED.getID()) {
					statement = "SELECT sumOfUsersToAnswer FROM APP.PrivateQuestions WHERE questionID = ?;";
					resultSet = executeStatementWithQuestionID(questionID, statement);
					resultSet.next();
					final int countUsersToAnswer = resultSet.getInt(1);
					if (countUsersToAnswer == allreadyAnswered) {
						sumOfAnsweredUsersReached = true;
					}
				}

				if (sumOfAnsweredUsersReached && definitionOfEnd == DefinitionOfEnd.END_BY_SUM_ANERWERED.getID() || allUsersHaveAnswered
						&& definitionOfEnd == DefinitionOfEnd.END_BY_ALL_ANSWERED.getID()) {
					statement = "UPDATE APP.PrivateQuestions SET finished = 1 final WHERE questionID = ?;";
					connection = ConnectionManager.getInstance().getWriterConnection();
					preparedStatement = connection.prepareStatement(statement);
					preparedStatement.setLong(1, questionID);
					preparedStatement.executeUpdate();
				}
			}
		} catch (final SQLException exception) {
			SQLUtil.rollbackSilently(connection);
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}

	private static ResultSet executeStatementWithQuestionID(final long questionID, final String statement) throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getInstance().getWriterConnection();
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, questionID);
			return preparedStatement.executeQuery();
		} catch (final SQLException exception) {
			SQLUtil.rollbackSilently(connection);
			throw new DatabaseLayerException(exception);
		} finally {
			SQLUtil.closeSilentlySQL(connection, preparedStatement, null);
		}
	}
}