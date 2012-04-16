package com.interzonedev.herokusupport.data.migration.operation;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;
import com.interzonedev.herokusupport.data.migration.result.MigrationResult;

public interface MigrationOperation {
	public MigrationResult doOperation(MigrationService migrationService) throws MigrationOperationException;
}
