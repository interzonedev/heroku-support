package com.interzonedev.herokusupport.data.migration.operation;

import com.interzonedev.herokusupport.data.migration.MigrationService;

public interface MigrationOperation {
	public MigrationResult doOperation(MigrationService migrationService);
}
