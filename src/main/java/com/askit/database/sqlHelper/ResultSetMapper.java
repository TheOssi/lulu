package com.askit.database.sqlHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.askit.entities.Answer;
import com.askit.entities.Group;
import com.askit.entities.PrivateQuestion;
import com.askit.entities.PrivateQuestionToUser;
import com.askit.entities.PublicQuestion;
import com.askit.entities.User;

/**
 * All map methods for the database. Each method maps a result set to a bean
 * class.
 * 
 * @author Kai Müller
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public class ResultSetMapper {

	/**
	 * Maps a result set to the {@link PrivateQuestionToUser} bean class
	 * 
	 * @param resultSet
	 *            the result set to map
	 * @return a list of private questions
	 * @throws SQLException
	 *             if a sql exception occurs
	 */
	public static List<PrivateQuestion> mapPrivateQuestions(final ResultSet resultSet) throws SQLException {
		final BeanListHandler<PrivateQuestion> listHandler = new BeanListHandler<PrivateQuestion>(PrivateQuestion.class);
		return listHandler.handle(resultSet);
	}

	/**
	 * Maps a result set to the {@link PublicQuestion} bean class
	 * 
	 * @param resultSet
	 *            the result set to map
	 * @return a list of public questions
	 * @throws SQLException
	 *             if a sql exception occurs
	 */
	public static List<PublicQuestion> mapPublicQuestions(final ResultSet resultSet) throws SQLException {
		final BeanListHandler<PublicQuestion> listHandler = new BeanListHandler<PublicQuestion>(PublicQuestion.class);
		return listHandler.handle(resultSet);
	}

	/**
	 * Maps a result set to the {@link Group} bean class
	 * 
	 * @param resultSet
	 *            the result set to map
	 * @return a list of groups
	 * @throws SQLException
	 *             if a sql exception occurs
	 */
	public static List<Group> mapGroups(final ResultSet resultSet) throws SQLException {
		final BeanListHandler<Group> listHandler = new BeanListHandler<Group>(Group.class);
		return listHandler.handle(resultSet);
	}

	/**
	 * Maps a result set to the {@link User} bean class
	 * 
	 * @param resultSet
	 *            the result set to map
	 * @return a list of users
	 * @throws SQLException
	 *             if a sql exception occurs
	 */
	public static List<User> mapUsers(final ResultSet resultSet) throws SQLException {
		final BeanListHandler<User> listHandler = new BeanListHandler<User>(User.class);
		return listHandler.handle(resultSet);
	}

	/**
	 * Maps a result set to the {@link Answer} bean class
	 * 
	 * @param resultSet
	 *            the result set to map
	 * @return a list of answers
	 * @throws SQLException
	 *             if a sql exception occurs
	 */
	public static List<Answer> mapAnswers(final ResultSet resultSet) throws SQLException {
		final BeanListHandler<Answer> listHandler = new BeanListHandler<Answer>(Answer.class);
		return listHandler.handle(resultSet);
	}
}