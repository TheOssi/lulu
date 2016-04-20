package com.askit.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.askit.etc.Util;
import com.askit.exception.DatabaseLayerException;

public class Trigger {

	public static void setPointsForRightAnswerInPrivateBet(final long selectedAnswerID, final long questionID, final long groupID)
			throws DatabaseLayerException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			final int pointsToAdd = Points.POINTS_PRIVATE_BET_RIGHT_ANSWER.getPoints();
			connection = ConnectionManager.getInstance().getWriterConnection();
			final String statement = " UPDATE APP.GroupsToUsers G INNER JOIN APP.PrivateQuestionsToUsers P ON P.choosedAnswerID = ? AND P.questionID = ?"
					+ " SET score = (score + ?)  WHERE G.groupID = ? AND G.userID = P.userID;";
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setLong(1, selectedAnswerID);
			preparedStatement.setLong(2, questionID);
			preparedStatement.setInt(3, pointsToAdd);
			preparedStatement.setLong(4, groupID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			Util.closeSilentlySQL(preparedStatement, null);
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
			throw new DatabaseLayerException(exception);
		} finally {
			Util.closeSilentlySQL(preparedStatement, null);
		}
	}

	// TODO if one goes wrong?
	public static void setPointsForAnsweringAPublicQuestion(final long userID, final long hostID) throws DatabaseLayerException {
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
			throw new DatabaseLayerException(exception);
		} finally {
			Util.closeSilentlySQL(preparedStatement, null);
		}

		try {
			final int pointsToAddForAdmin = Points.POINTS_PUBLIC_QUESTION_ANSWERED_FOR_HOST.getPoints();
			connection = ConnectionManager.getInstance().getWriterConnection();
			final String statement = "UPDATE APP.Users SET scoreOfGlobal = (scoreOfGlobal + ? ) WHERE userID = ?;";
			preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setInt(1, pointsToAddForAdmin);
			preparedStatement.setLong(2, hostID);
			preparedStatement.executeUpdate();
		} catch (final SQLException exception) {
			throw new DatabaseLayerException(exception);
		} finally {
			Util.closeSilentlySQL(preparedStatement, null);
		}
	}

}
