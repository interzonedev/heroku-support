package com.interzonedev.herokusupport.data.migration.operation;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;
import com.interzonedev.herokusupport.data.migration.result.MigrationResult;
import com.interzonedev.herokusupport.data.migration.result.MigrationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

@Named("initOperation")
public class InitOperation implements MigrationOperation {

    private static final Logger log = LoggerFactory.getLogger(InitOperation.class);

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
