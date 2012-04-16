package com.interzonedev.herokusupport.data.migration.operation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;
import com.interzonedev.herokusupport.data.migration.result.MigrationResult;

public class StatusOperation implements MigrationOperation {
	private Log log = LogFactory.getLog(getClass());

	@Override
	public MigrationResult doOperation(MigrationService migrationService) throws MigrationOperationException {

		log.info("doOperation: start");

		MigrationResult migrationResult = migrationService.getStatus();

		log.debug("doOperation: migrationResult = " + migrationResult);

		log.info("doOperation: end");

		return migrationResult;

	}

}
