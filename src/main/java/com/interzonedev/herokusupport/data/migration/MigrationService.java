package com.interzonedev.herokusupport.data.migration;

import java.util.List;

public interface MigrationService {
	public MigrationStatus doMigration();

	public void doClean();

	public List<MigrationHistory> getHistory();
}
