package com.interzonedev.herokusupport.data.migration.operation.runner;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;
import com.interzonedev.herokusupport.data.migration.operation.CleanOperation;
import com.interzonedev.herokusupport.data.migration.operation.HistoryOperation;
import com.interzonedev.herokusupport.data.migration.operation.InitOperation;
import com.interzonedev.herokusupport.data.migration.operation.MigrateOperation;
import com.interzonedev.herokusupport.data.migration.operation.MigrationOperation;
import com.interzonedev.herokusupport.data.migration.operation.MigrationTask;
import com.interzonedev.herokusupport.data.migration.operation.StatusOperation;
import com.interzonedev.herokusupport.data.migration.result.MigrationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named("operationRunner")
public class DefaultOperationRunner implements OperationRunner {

    private static final Logger log = LoggerFactory.getLogger(DefaultOperationRunner.class);

    @Inject
    private InitOperation initOperation;

    @Inject
    private MigrateOperation migrateOperation;

    @Inject
    private CleanOperation cleanOperation;

    @Inject
    private StatusOperation statusOperation;

    @Inject
    private HistoryOperation historyOperation;

    @Override
    public MigrationResult doOperation(MigrationTask migrationTask, MigrationService migrationService)
            throws MigrationOperationException {
        log.debug("doOperation: Perform " + migrationTask);

        MigrationOperation migrationOperation = null;

        switch (migrationTask) {
            case INIT:
                migrationOperation = initOperation;
                break;
            case MIGRATE:
                migrationOperation = migrateOperation;
                break;
            case CLEAN:
                migrationOperation = cleanOperation;
                break;
            case STATUS:
                migrationOperation = statusOperation;
                break;
            case HISTORY:
                migrationOperation = historyOperation;
                break;
            default:
                String errorMessage = "doOperation: Unrecognized operation " + migrationTask;
                log.error(errorMessage);
                throw new MigrationOperationException(errorMessage);
        }

        MigrationResult result = migrationOperation.doOperation(migrationService);

        log.debug("doOperation: result = " + result);

        return result;

    }
}
