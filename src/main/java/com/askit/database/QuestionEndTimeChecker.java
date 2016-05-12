package com.askit.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.askit.entities.PrivateQuestion;
import com.askit.entities.PublicQuestion;
import com.askit.exception.DatabaseLayerException;
import com.askit.exception.NotificationException;
import com.askit.notification.Notification;
import com.askit.notification.NotificationCodes;
import com.askit.notification.NotificationSupporter;

/**
 * This class is a thread that checks the private and public question if they
 * should be closed.
 * 
 * @author Kai Müller
 * @version 1.0.0.0
 * @since 1.0.0.0
 *
 */
public class QuestionEndTimeChecker extends Thread {

	private static final QuestionEndTimeChecker INSTANCE = new QuestionEndTimeChecker();
	private static final long SLEEP_TIME = 5000L;
	private static final String STATEMENT = "SELECT R.questionID, R.endDate, R.type FROM ( SELECT PR.questionID, PR.endDate, '"
			+ PrivateQuestion.TABLE_NAME + "' as \"type\" FROM APP.PrivateQuestions AS PR "
			+ "WHERE PR.endDate IS NOT NULL AND PR.finished <> 1 AND PR.definitionOfEnd = 1 " + "UNION ALL SELECT PU.questionID, PU.endDate, '"
			+ PublicQuestion.TABLE_NAME + "' as \"type\" FROM APP.PublicQuestions PU " + "WHERE PU.endDate IS NOT NULL AND PU.finished <> 1 )  AS R "
			+ "ORDER BY R.endDate ASC LIMIT 1;";

	private AbstractQuestion currentQuestion;
	private final QueryManager queryManager = new DatabaseQueryManager();

	private QuestionEndTimeChecker() {
	}

	public static synchronized QuestionEndTimeChecker getInstance() {
		return INSTANCE;
	}

	public void startThread() {
		if (!this.isAlive() || this.isInterrupted()) {
			this.start();
		}
	}

	private AbstractQuestion getNext() throws SQLException {
		final Connection connection = ConnectionManager.getInstance().getReaderConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(STATEMENT);
		final ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			final long id = resultSet.getLong(1);
			final long endDate = resultSet.getDate(2).getTime();
			final String tableName = resultSet.getString(3);
			return new AbstractQuestion(id, endDate, tableName);
		}
		return null;
	}

	@Override
	public void run() {
		try {
			currentQuestion = getNext();
			if (currentQuestion != null) {
				final long currentTime = System.currentTimeMillis();
				if (currentTime + SLEEP_TIME < currentQuestion.getTime().longValue()) {
					Thread.sleep(SLEEP_TIME);
				} else {
					final Long questionID = currentQuestion.getId();
					final Notification notification = new Notification("", NotificationCodes.NOTIFICATION_QUESTION_END.getCode(), "questionID",
							questionID.toString());
					if (currentQuestion.getTableName().equals(PrivateQuestion.TABLE_NAME)) {
						queryManager.setPrivateQuestionToFinish(questionID);
						NotificationSupporter.sendNotificationToAllMembersOfPrivateQuestion(notification, questionID);
					} else if (currentQuestion.getTableName().equals(PublicQuestion.TABLE_NAME)) {
						queryManager.setPublicQuestionToFinish(questionID);
						NotificationSupporter.sendNotificationToAllMembersOfPublicQuestion(notification, questionID);
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