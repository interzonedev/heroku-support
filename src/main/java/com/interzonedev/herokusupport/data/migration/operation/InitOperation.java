package com.interzonedev.herokusupport.data.migration.operation;

import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;
import com.interzonedev.herokusupport.data.migration.result.MigrationResult;
import com.interzonedev.herokusupport.data.migration.result.MigrationStatus;

@Named("initOperation")
public class InitOperation implements MigrationOperation {

	private Log log = LogFactory.getLog(getClass());

	@Override
	public MigrationResult doOperation(MigrationService migrationService) throws MigrationOperationException {
		log.info("doOperation: start");

		MigrationStatus migrationStatus = migrationService.doInit();

		log.info("doOperation: Migration status = " + migrationStatus);

		MigrationResult migrationResult = new MigrationResult(migrationStatus);

		log.info("doOperation: end");

		return migrationResult;

	}

}
