package com.askit.database.trigger;

public enum DefinitionOfEnd {

	END_BY_TIME(1),
	END_BY_ALL_ANSWERED(2),
	END_BY_SUM_ANERWERED(3);

	private int id;

	private DefinitionOfEnd(final int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

}