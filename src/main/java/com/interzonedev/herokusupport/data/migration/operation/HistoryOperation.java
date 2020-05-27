package com.interzonedev.herokusupport.data.migration.operation;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;
import com.interzonedev.herokusupport.data.migration.result.MigrationHistory;
import com.interzonedev.herokusupport.data.migration.result.MigrationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.List;

@Named("historyOperation")
public class HistoryOperation implements MigrationOperation {
    private static final Logger log = LoggerFactory.getLogger(HistoryOperation.class);

    @Override
    public MigrationResult doOperation(MigrationService migrationService) throws MigrationOperationException {

        log.info("doOperation: start");

        List<MigrationHistory> history = migrationService.getHistory();

        for (MigrationHistory historyItem : history) {
            log.debug("doOperation: historyItem = " + historyItem);
        }

        MigrationResult migrationResult = new MigrationResult(history);

        log.info("doOperation: end");

        return migrationResult;

    }

}
