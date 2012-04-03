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

	// TODO - Make the MigrationOperation instances beans in a local Spring container.
	private MigrateOperation migrateOperation = new MigrateOperation();

	private CleanOperation cleanOperation = new CleanOperation();

	private HistoryOperation historyOperation = new HistoryOperation();

	@Override
	public MigrationResult doOperation(MigrationTask migrationTask, MigrationService migrationService)
			throws MigrationOperationException {
		log.debug("doOperation: Perform " + migrationTask);

		MigrationResult result = null;

		switch (migrationTask) {
			case MIGRATE:
				result = migrateOperation.doOperation(migrationService);
				break;
			case CLEAN:
				result = cleanOperation.doOperation(migrationService);
				break;
			case HISTORY:
				result = historyOperation.doOperation(migrationService);
				break;
			default:
				String errorMessage = "doOperation: Unrecognized operation " + migrationTask;
				log.error(errorMessage);
				throw new MigrationOperationException(errorMessage);
		}

		log.debug("doOperation: result = " + result);

		return result;

	}

}
