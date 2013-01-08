package com.interzonedev.herokusupport.data.migration.flyway;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.googlecode.flyway.core.Flyway;
import com.googlecode.flyway.core.init.InitException;
import com.googlecode.flyway.core.metadatatable.MetaDataTableRow;
import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;
import com.interzonedev.herokusupport.data.migration.result.MigrationHistory;
import com.interzonedev.herokusupport.data.migration.result.MigrationResult;
import com.interzonedev.herokusupport.data.migration.result.MigrationStatus;

public class MigrationServiceFlyway extends Flyway implements MigrationService {

	private Logger log = (Logger) LoggerFactory.getLogger(getClass());

	@Override
	public MigrationStatus doInit() throws MigrationOperationException {
		log.debug("doInit: start");

		MigrationStatus status = MigrationStatus.INIT_SUCCEEDED;

		try {
			log.debug("doInit: init schema");
			init();
		} catch (InitException ie) {
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
			MetaDataTableRow status = status();
			String statusToString = metaDataTableRowToString(status);
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
			List<MetaDataTableRow> history = history();
			for (MetaDataTableRow historyItem : history) {
				String historyToString = metaDataTableRowToString(historyItem);
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

	private String metaDataTableRowToString(MetaDataTableRow metaDataTableRow) {
		StringBuilder out = new StringBuilder();
		out.append(metaDataTableRow.getVersion()).append(" - ");
		out.append(metaDataTableRow.getDescription()).append(" - ");
		out.append(metaDataTableRow.getMigrationType()).append(" - ");
		out.append(metaDataTableRow.getState());
		return out.toString();
	}
}
