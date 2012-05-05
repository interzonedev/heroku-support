package com.interzonedev.herokusupport.data.migration.operation.runner;

import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;
import com.interzonedev.herokusupport.data.migration.operation.CleanOperation;
import com.interzonedev.herokusupport.data.migration.operation.HistoryOperation;
import com.interzonedev.herokusupport.data.migration.operation.InitOperation;
import com.interzonedev.herokusupport.data.migration.operation.MigrateOperation;
import com.interzonedev.herokusupport.data.migration.operation.MigrationTask;
import com.interzonedev.herokusupport.data.migration.operation.StatusOperation;
import com.interzonedev.herokusupport.data.migration.result.MigrationResult;

@Named("operationRunner")
public class DefaultOperationRunner implements OperationRunner {

	private Log log = LogFactory.getLog(getClass());

	private InitOperation initOperation = new InitOperation();

	private MigrateOperation migrateOperation = new MigrateOperation();

	private CleanOperation cleanOperation = new CleanOperation();

	private StatusOperation statusOperation = new StatusOperation();

	private HistoryOperation historyOperation = new HistoryOperation();

	@Override
	public MigrationResult doOperation(MigrationTask migrationTask, MigrationService migrationService)
			throws MigrationOperationException {
		log.debug("doOperation: Perform " + migrationTask);

		MigrationResult result = null;

		switch (migrationTask) {
			case INIT:
				result = initOperation.doOperation(migrationService);
				break;
			case MIGRATE:
				result = migrateOperation.doOperation(migrationService);
				break;
			case CLEAN:
				result = cleanOperation.doOperation(migrationService);
				break;
			case STATUS:
				result = statusOperation.doOperation(migrationService);
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
