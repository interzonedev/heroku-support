package com.interzonedev.herokusupport.data.migration;

import com.interzonedev.herokusupport.data.migration.result.MigrationHistory;
import com.interzonedev.herokusupport.data.migration.result.MigrationResult;
import com.interzonedev.herokusupport.data.migration.result.MigrationStatus;

import java.util.List;

public interface MigrationService {
    MigrationStatus doInit() throws MigrationOperationException;

    MigrationStatus doMigration() throws MigrationOperationException;

    void doClean() throws MigrationOperationException;

    MigrationResult getStatus() throws MigrationOperationException;

    List<MigrationHistory> getHistory() throws MigrationOperationException;
}
