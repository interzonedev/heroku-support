package com.interzonedev.herokusupport.data.migration.operation.runner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;
import com.interzonedev.herokusupport.data.migration.operation.CleanOperation;
import com.interzonedev.herokusupport.data.migration.operation.HistoryOperation;
import com.interzonedev.herokusupport.data.migration.operation.MigrateOperation;
import com.interzonedev.herokusupport.data.migration.operation.MigrationResult;
import com.interzonedev.herokusupport.data.migration.operation.MigrationTask;

public class DefaultOperationRunner implements OperationRunner {

	private Log log = LogFactory.getLog(getClass());

	@Override
	public MigrationResult doOperation(MigrationTask migrationTask, MigrationService migrationService)
			throws MigrationOperationException {
		log.debug("doOperation: Perform " + migrationTask);

		MigrationResult result = null;

		// TODO - Make the MigrationOperation instances beans in a local Spring container.
		switch (migrationTask) {
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
				log.error("doOperation: Unrecognized operation " + migrationTask);
				break;
		}

		log.debug("doOperation: result = " + result);

		return result;

	}

}
