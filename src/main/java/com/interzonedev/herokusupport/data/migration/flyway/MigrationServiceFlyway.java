package com.interzonedev.herokusupport.data.migration.flyway;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.flyway.core.Flyway;
import com.googlecode.flyway.core.init.InitException;
import com.googlecode.flyway.core.metadatatable.MetaDataTableRow;
import com.interzonedev.herokusupport.data.migration.MigrationHistory;
import com.interzonedev.herokusupport.data.migration.MigrationService;
import com.interzonedev.herokusupport.data.migration.MigrationStatus;

public class MigrationServiceFlyway extends Flyway implements MigrationService {

	private final Log log = LogFactory.getLog(getClass());

	@Override
	public MigrationStatus doMigration() {
		log.debug("doMigration: start");

		MigrationStatus status = MigrationStatus.INIT_SUCCEEDED;

		try {
			log.debug("doMigration: init schema");
			init();
		} catch (InitException ie) {
			log.info("doMigration: Schema already initialized");
		} catch (Throwable t) {
			log.error("doMigration: Error migrating schema", t);
			status = MigrationStatus.INIT_FAILED;
		}

		if (MigrationStatus.INIT_SUCCEEDED.equals(status)) {
			status = MigrationStatus.MIGRATION_FAILED;
			try {
				log.debug("doMigration: migrate schema");
				migrate();
				status = MigrationStatus.MIGRATION_SUCCEEDED;
			} catch (Throwable t) {
				log.error("doMigration: Error migrating schema", t);
			}
		}

		log.debug("doMigration: end - status = " + status);

		return status;
	}

	@Override
	public void doClean() {
		log.debug("doClean: start");

		try {
			clean();
		} catch (Throwable t) {
			log.error("doClean: Error cleaning schema", t);
		}

		log.debug("doClean: end");
	}

	@Override
	public List<MigrationHistory> getHistory() {
		log.debug("getHistory: start");

		List<MigrationHistory> wrappedHistory = new ArrayList<MigrationHistory>();

		try {
			List<MetaDataTableRow> history = history();
			for (MetaDataTableRow historyItem : history) {
				wrappedHistory.add(new MigrationHistory(historyItem));
			}
		} catch (Throwable t) {
			log.error("getHistory: Error getting schema history", t);
		}

		log.debug("getHistory: end");

		return wrappedHistory;
	}

}
