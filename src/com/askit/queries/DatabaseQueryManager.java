package com.askit.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.askit.database.ConnectionFactory;
import com.askit.database.Constants;
import com.askit.entities.Group;
import com.askit.entities.PrivateQuestion;
import com.askit.entities.PublicQuestion;
import com.askit.entities.User;
import com.askit.exception.DriverNotFoundException;

public class DatabaseQueryManager implements QueryManager {

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
	public void registerUser(final User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createNewGroup(final Group group) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addUserToGroup(final long groupID, final long userID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addContact(final long userIDOfUser, final long userIDofContact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addUserToQuestion(final long userID, final long questionID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addUserToOneTimeQuestion(final long userID, final long questionID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createPublicQuestion(final PublicQuestion question) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createNewQuestionInGroup(final PrivateQuestion question, final long groupID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createOneTimeQuestion(final PrivateQuestion question) {
		// TODO Auto-generated method stub

	}
}