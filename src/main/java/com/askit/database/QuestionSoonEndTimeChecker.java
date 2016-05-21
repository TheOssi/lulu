package com.askit.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.askit.entities.PrivateQuestion;
import com.askit.entities.PublicQuestion;
import com.askit.exception.DatabaseLayerException;
import com.askit.exception.NotificationException;
import com.askit.notification.Notification;
import com.askit.notification.NotificationCodes;
import com.askit.notification.NotificationSupporter;

public class QuestionSoonEndTimeChecker extends Thread {
	private static final QuestionSoonEndTimeChecker INSTANCE = new QuestionSoonEndTimeChecker();
	private static final long SLEEP_TIME = 5000L; // 5s
	private static final long SOON_END_TIME = 14400000L; // 2h
	private static final long THRESHOLD_LEFT = 60000L; // 1min
	private static final long THRESHOLD_RIGHT = 60000L; // 1min
	private static final String STATEMENT = "SELECT R.questionID, R.endDate, R.type FROM ( SELECT PR.questionID, PR.endDate, '"
			+ PrivateQuestion.TABLE_NAME + "' as \"type\" FROM APP.PrivateQuestions AS PR "
			+ "WHERE PR.endDate IS NOT NULL AND PR.endDate BETWEEN ? AND ? AND PR.finished <> 1 AND PR.definitionOfEnd = 1 "
			+ "UNION ALL SELECT PU.questionID, PU.endDate, '" + PublicQuestion.TABLE_NAME + "' as \"type\" FROM APP.PublicQuestions PU "
			+ "WHERE PU.endDate IS NOT NULL AND PU.endDate BETWEEN ? AND ? AND PU.finished <> 1 )  AS R ORDER BY R.endDate ASC LIMIT 1;";

	// TODO is thread safe?
	private final List<AbstractQuestion> usedQuestions = Collections.synchronizedList(new ArrayList<AbstractQuestion>());

	private QuestionSoonEndTimeChecker() {
	}

	public static synchronized QuestionSoonEndTimeChecker getInstance() {
		return INSTANCE;
	}

	public void startThread() {
		if (!this.isAlive() || this.isInterrupted()) {
			this.start();
		}
	}

	public void removeUsedQuestion(final AbstractQuestion question) {
		usedQuestions.remove(question);
	}

	private AbstractQuestion[] getNext() throws SQLException {
		final long currentTime = System.currentTimeMillis();
		final Connection connection = ConnectionManager.getInstance().getReaderConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(STATEMENT);
		preparedStatement.setDate(1, new Date(currentTime - SOON_END_TIME + THRESHOLD_RIGHT));
		preparedStatement.setDate(2, new Date(currentTime - SOON_END_TIME - THRESHOLD_LEFT));
		preparedStatement.setDate(3, new Date(currentTime - SOON_END_TIME + THRESHOLD_RIGHT));
		preparedStatement.setDate(4, new Date(currentTime - SOON_END_TIME - THRESHOLD_LEFT));
		final ResultSet resultSet = preparedStatement.executeQuery();
		final List<AbstractQuestion> questions = new ArrayList<AbstractQuestion>();
		while (resultSet.next()) {
			final long id = resultSet.getLong(1);
			final long endDate = resultSet.getDate(2).getTime();
			final String tableName = resultSet.getString(3);
			questions.add(new AbstractQuestion(id, endDate, tableName));
		}
		return questions.toArray(new AbstractQuestion[questions.size()]);
	}

	@Override
	public void run() {
		AbstractQuestion[] currentQuestions = null;
		final QueryManager queryManager = new DatabaseQueryManager();
		while (true) {
			try {
				currentQuestions = getNext();
				if (currentQuestions.length != 0) {
					for (final AbstractQuestion currentQuestion : currentQuestions) {
						final Long questionID = currentQuestion.getId();
						final AbstractQuestion questionToCheck = new AbstractQuestion(currentQuestion.getId(), null, currentQuestion.getTableName());
						if (!usedQuestions.contains(questionToCheck)) {
							usedQuestions.add(questionToCheck);
							final Notification notification = new Notification("", NotificationCodes.NOTIFICATION_QUESTION_SOON_END.getCode(),
									"questionID", questionID.toString());
							if (currentQuestion.getTableName().equals(PrivateQuestion.TABLE_NAME)) {
								queryManager.setPrivateQuestionToFinish(questionID);
								NotificationSupporter.sendNotificationToAllMembersOfPrivateQuestion(notification, questionID);
							} else if (currentQuestion.getTableName().equals(PublicQuestion.TABLE_NAME)) {
								queryManager.setPublicQuestionToFinish(questionID);
								NotificationSupporter.sendNotificationToAllMembersOfPublicQuestion(notification, questionID);
							}
						}
					}
				} else {
					Thread.sleep(SLEEP_TIME);
				}
			} catch (final SQLException | DatabaseLayerException | NotificationException e) {
				e.printStackTrace();
			} catch (final InterruptedException e) {
				e.printStackTrace();
				startThread();
			}
		}
	}
}