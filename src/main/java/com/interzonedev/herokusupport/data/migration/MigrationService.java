package com.interzonedev.herokusupport.data.migration;

import java.util.List;

import com.interzonedev.herokusupport.data.migration.result.MigrationHistory;
import com.interzonedev.herokusupport.data.migration.result.MigrationStatus;

public interface MigrationService {
	public MigrationStatus doInit() throws MigrationOperationException;

	public MigrationStatus doMigration() throws MigrationOperationException;

	public void doClean() throws MigrationOperationException;

	public List<MigrationHistory> getHistory() throws MigrationOperationException;
}
