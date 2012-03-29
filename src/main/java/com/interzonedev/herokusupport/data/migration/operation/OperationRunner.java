package com.interzonedev.herokusupport.data.migration.operation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.interzonedev.herokusupport.data.migration.MigrationService;

public class OperationRunner {

	private Log log = LogFactory.getLog(getClass());

	public MigrationResult doOperation(MigrationTask migrationOperation, MigrationService migrationService) {

		log.debug("doOperation: Perform " + migrationOperation);

		MigrationResult result = null;

		switch (migrationOperation) {
			case MIGRATE:

				break;
			case CLEAN:

				break;
			case HISTORY:
				// TODO - Make this instance and others like it beans in a local Spring container.
				result = (new HistoryOperation()).doOperation(migrationService);
				break;
			default:
				log.error("doOperation: Unrecognized operation " + migrationOperation);
				break;
		}

		return result;
	}
}
