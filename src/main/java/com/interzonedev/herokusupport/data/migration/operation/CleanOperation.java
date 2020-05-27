package com.interzonedev.herokusupport.data.migration.operation;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;
import com.interzonedev.herokusupport.data.migration.result.MigrationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

@Named("cleanOperation")
public class CleanOperation implements MigrationOperation {

    private static final Logger log = LoggerFactory.getLogger(CleanOperation.class);

    @Override
    public MigrationResult doOperation(MigrationService migrationService) throws MigrationOperationException {
        log.info("doOperation: start");

        migrationService.doClean();

        log.info("doOperation: end");

        return null;
    }
}
