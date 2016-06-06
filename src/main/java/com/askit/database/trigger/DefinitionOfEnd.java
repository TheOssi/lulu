package com.askit.database.trigger;

/**
 * enum for the constants of the definition of end of a question
 * 
 * @author Kai Müller
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public enum DefinitionOfEnd {

	END_BY_TIME(1),
	END_BY_ALL_ANSWERED(2),
	END_BY_SUM_ANERWERED(3);

	private int id;

	private DefinitionOfEnd(final int id) {
		this.id = id;
	}

	/**
	 * get the id of a definition
	 * 
	 * @return the id
	 */
	public int getID() {
		return id;
	}
}