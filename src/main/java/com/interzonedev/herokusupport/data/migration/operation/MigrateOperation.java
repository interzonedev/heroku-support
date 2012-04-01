package com.interzonedev.herokusupport.data.migration.operation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;
import com.interzonedev.herokusupport.data.migration.MigrationStatus;

public class MigrateOperation implements MigrationOperation {

	private Log log = LogFactory.getLog(getClass());

	@Override
	public MigrationResult doOperation(MigrationService migrationService) throws MigrationOperationException {
		log.info("doOperation: start");

		MigrationStatus migrationStatus = migrationService.doMigration();

		log.info("doOperation: Migration status = " + migrationStatus);

		MigrationResult migrationResult = new MigrationResult(migrationStatus);

		log.info("doOperation: end");

		return migrationResult;

	}

}
