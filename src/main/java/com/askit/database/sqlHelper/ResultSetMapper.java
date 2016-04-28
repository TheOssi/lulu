package com.askit.database.sqlHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.askit.entities.Answer;
import com.askit.entities.Group;
import com.askit.entities.PrivateQuestion;
import com.askit.entities.PublicQuestion;
import com.askit.entities.User;

public class ResultSetMapper {

	public static List<PrivateQuestion> mapPrivateQuestions(final ResultSet resultSet) throws SQLException {
		final BeanListHandler<PrivateQuestion> listHandler = new BeanListHandler<PrivateQuestion>(PrivateQuestion.class);
		return listHandler.handle(resultSet);
	}

	public static List<PublicQuestion> mapPublicQuestions(final ResultSet resultSet) throws SQLException {
		final BeanListHandler<PublicQuestion> listHandler = new BeanListHandler<PublicQuestion>(PublicQuestion.class);
		return listHandler.handle(resultSet);
	}

	public static List<Group> mapGroups(final ResultSet resultSet) throws SQLException {
		final BeanListHandler<Group> listHandler = new BeanListHandler<Group>(Group.class);
		return listHandler.handle(resultSet);
	}

	public static List<User> mapUsers(final ResultSet resultSet) throws SQLException {
		final BeanListHandler<User> listHandler = new BeanListHandler<User>(User.class);
		return listHandler.handle(resultSet);
	}

	public static List<Answer> mapAnswers(final ResultSet resultSet) throws SQLException {
		final BeanListHandler<Answer> listHandler = new BeanListHandler<Answer>(Answer.class);
		return listHandler.handle(resultSet);
	}
}