package com.interzonedev.herokusupport.data.migration;

import java.util.List;

public interface MigrationService {
	public MigrationStatus doMigration() throws MigrationOperationException;

	public void doClean() throws MigrationOperationException;

	public List<MigrationHistory> getHistory() throws MigrationOperationException;
}
