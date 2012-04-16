package com.interzonedev.herokusupport.data.migration.result;

public class MigrationHistory {
	private Object history;

	public MigrationHistory(Object history) {

		this.history = history;
	}

	public Object getHistory() {
		return history;
	}

	@Override
	public String toString() {
		String output = "null";

		if (null != history) {
			output = history.toString();
		}

		return output;
	}
}
