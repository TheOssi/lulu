package com.askit.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	private static final long SLEEP_TIME = 5000L;
	// TODO modify statement + edits from other class
	private static final String STATEMENT = "SELECT R.questionID, R.endDate, R.type FROM ( SELECT PR.questionID, PR.endDate, '"
			+ PrivateQuestion.TABLE_NAME + "' as \"type\" FROM APP.PrivateQuestions AS PR "
			+ "WHERE PR.endDate IS NOT NULL AND PR.finished <> 1 AND PR.definitionOfEnd = 1 " + "UNION ALL SELECT PU.questionID, PU.endDate, '"
			+ PublicQuestion.TABLE_NAME + "' as \"type\" FROM APP.PublicQuestions PU " + "WHERE PU.endDate IS NOT NULL AND PU.finished <> 1 )  AS R "
			+ "ORDER BY R.endDate ASC LIMIT 1;";

	private AbstractQuestion[] currentQuestions;
	private final QueryManager queryManager = new DatabaseQueryManager();
	private final List<Long> usedIDs = new ArrayList<Long>();

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

	private AbstractQuestion[] getNext() throws SQLException {
		final Connection connection = ConnectionManager.getInstance().getReaderConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(STATEMENT);
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
		try {
			currentQuestions = getNext();
			if (currentQuestions.length != 0) {
				for (final AbstractQuestion currentQuestion : currentQuestions) {
					final Long questionID = currentQuestion.getId();
					if (!usedIDs.contains(questionID)) {
						usedIDs.add(questionID);
						// TODO notification code
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