package com.interzonedev.herokusupport.data.migration.operation;

import java.util.List;

import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;
import com.interzonedev.herokusupport.data.migration.result.MigrationHistory;
import com.interzonedev.herokusupport.data.migration.result.MigrationResult;

@Named("historyOperation")
public class HistoryOperation implements MigrationOperation {
	private Log log = LogFactory.getLog(getClass());

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
