package com.interzonedev.herokusupport.data.migration.flyway;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;
import com.interzonedev.herokusupport.data.migration.result.MigrationHistory;
import com.interzonedev.herokusupport.data.migration.result.MigrationResult;
import com.interzonedev.herokusupport.data.migration.result.MigrationStatus;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MigrationServiceFlyway extends Flyway implements MigrationService {

    private static final Logger log = LoggerFactory.getLogger(MigrationServiceFlyway.class);

    public MigrationServiceFlyway(Configuration configuration) {
        super(configuration);
    }

    @Override
    public MigrationStatus doInit() throws MigrationOperationException {
        log.debug("doInit: start");

        MigrationStatus status = MigrationStatus.INIT_SUCCEEDED;

        try {
            log.debug("doInit: init schema");
            baseline();
        } catch (FlywayException ie) {
            log.info("doInit: Schema already initialized");
        } catch (Throwable t) {
            String errorMessage = "doInit: Error initializing migrations";
            log.error(errorMessage, t);
            throw new MigrationOperationException(errorMessage, t);
        }

        log.debug("doInit: end - status = " + status);

        return status;
    }

    @Override
    public MigrationStatus doMigration() throws MigrationOperationException {
        log.debug("doMigration: start");

        MigrationStatus status = doInit();

        if (MigrationStatus.INIT_SUCCEEDED.equals(status)) {
            status = MigrationStatus.MIGRATION_FAILED;
            try {
                log.debug("doMigration: migrate schema");
                migrate();
                status = MigrationStatus.MIGRATION_SUCCEEDED;
            } catch (Throwable t) {
                String errorMessage = "doMigration: Error migrating schema";
                log.error(errorMessage, t);
                throw new MigrationOperationException(errorMessage, t);
            }
        }

        log.debug("doMigration: end - status = " + status);

        return status;
    }

    @Override
    public void doClean() throws MigrationOperationException {
        log.debug("doClean: start");

        try {
            clean();
        } catch (Throwable t) {
            String errorMessage = "doClean: Error cleaning schema";
            log.error(errorMessage, t);
            throw new MigrationOperationException(errorMessage, t);
        }

        log.debug("doClean: end");
    }

    @Override
    public MigrationResult getStatus() throws MigrationOperationException {
        log.debug("getStatus: start");

        MigrationResult result = null;

        try {
            MigrationInfo status = info().current();
            String statusToString = migrationInfoToString(status);
            result = new MigrationResult(statusToString);
        } catch (Throwable t) {
            String errorMessage = "getStatus: Error getting migration status";
            log.error(errorMessage, t);
            throw new MigrationOperationException(errorMessage, t);
        }

        return result;
    }

    @Override
    public List<MigrationHistory> getHistory() throws MigrationOperationException {
        log.debug("getHistory: start");

        List<MigrationHistory> wrappedHistory = new ArrayList<MigrationHistory>();

        try {
            MigrationInfo[] history = info().applied();
            for (MigrationInfo historyItem : history) {
                String historyToString = migrationInfoToString(historyItem);
                wrappedHistory.add(new MigrationHistory(historyToString));
            }
        } catch (Throwable t) {
            String errorMessage = "getHistory: Error getting migration history";
            log.error(errorMessage, t);
            throw new MigrationOperationException(errorMessage, t);
        }

        log.debug("getHistory: end");

        return wrappedHistory;
    }

    private String migrationInfoToString(MigrationInfo migrationInfo) {
        StringBuilder out = new StringBuilder();
        out.append(migrationInfo.getVersion()).append(" - ");
        out.append(migrationInfo.getDescription()).append(" - ");
        out.append(migrationInfo.getType()).append(" - ");
        out.append(migrationInfo.getState());
        return out.toString();
    }
}
