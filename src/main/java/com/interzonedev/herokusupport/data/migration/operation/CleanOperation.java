package com.interzonedev.herokusupport.data.migration.operation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;

public class CleanOperation implements MigrationOperation {

	private Log log = LogFactory.getLog(getClass());

	@Override
	public MigrationResult doOperation(MigrationService migrationService) throws MigrationOperationException {
		log.info("doOperation: start");

		migrationService.doClean();

		log.info("doOperation: end");

		return null;
	}
}
