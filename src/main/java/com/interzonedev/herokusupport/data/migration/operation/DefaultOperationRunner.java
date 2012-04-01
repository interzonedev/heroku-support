package com.interzonedev.herokusupport.data.migration.operation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;

public class DefaultOperationRunner implements OperationRunner {

	private Log log = LogFactory.getLog(getClass());

	@Override
	public MigrationResult doOperation(MigrationTask migrationOperation, MigrationService migrationService)
			throws MigrationOperationException {
		log.debug("doOperation: Perform " + migrationOperation);

		MigrationResult result = null;

		// TODO - Make the MigrationOperation instances beans in a local Spring container.
		switch (migrationOperation) {
			case MIGRATE:
				result = (new MigrateOperation()).doOperation(migrationService);
				break;
			case CLEAN:
				result = (new CleanOperation()).doOperation(migrationService);
				break;
			case HISTORY:
				result = (new HistoryOperation()).doOperation(migrationService);
				break;
			default:
				log.error("doOperation: Unrecognized operation " + migrationOperation);
				break;
		}

		log.debug("doOperation: result = " + result);

		return result;

	}

}
