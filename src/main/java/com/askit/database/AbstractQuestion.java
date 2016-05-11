package com.askit.database;

/**
 * 
 * This class is a abstract version if a question, so it can holds the main data
 * from a private and a public question. The tablename is for the differnce
 * between the two types of questions. <br/>
 * 
 * This class is used in the {@link QuestionEndTimeChecker}
 * 
 * @author Kai Müller
 * @since 1.0.0.0
 * @version 1.0.0.0
 *
 */
public class AbstractQuestion {

	private final Long id, time;
	private final String tableName;

	/**
	 * Constructor for a new abstract question.
	 * 
	 * @param id
	 *            the id of the question
	 * @param time
	 *            the end time of the question
	 * @param tableName
	 *            the table of the question (private or public)
	 */
	public AbstractQuestion(final Long id, final Long time, final String tableName) {
		this.id = id;
		this.time = time;
		this.tableName = tableName;
	}

	/**
	 * 
	 * @return the id of the question
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the end time of a question
	 */
	public Long getTime() {
		return time;
	}

	/**
	 * @return the tablename in which in questions is stored
	 */
	public String getTableName() {
		return tableName;
	}
}