package com.interzonedev.herokusupport.data.migration.operation.runner;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;
import com.interzonedev.herokusupport.data.migration.operation.MigrationResult;
import com.interzonedev.herokusupport.data.migration.operation.MigrationTask;

public interface OperationRunner {
	public MigrationResult doOperation(MigrationTask migrationTask, MigrationService migrationService)
			throws MigrationOperationException;
}
