package com.interzonedev.herokusupport.data.migration.operation;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;

public interface OperationRunner {
	public MigrationResult doOperation(MigrationTask migrationOperation, MigrationService migrationService)
			throws MigrationOperationException;
}
