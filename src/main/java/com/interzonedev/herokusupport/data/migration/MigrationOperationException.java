package com.interzonedev.herokusupport.data.migration;

public class MigrationOperationException extends Exception {

	private static final long serialVersionUID = 1L;

	public MigrationOperationException() {
		super();
	}

	public MigrationOperationException(String message) {
		super(message);
	}

	public MigrationOperationException(Throwable t) {
		super(t);
	}

	public MigrationOperationException(String message, Throwable t) {
		super(message, t);
	}

}
