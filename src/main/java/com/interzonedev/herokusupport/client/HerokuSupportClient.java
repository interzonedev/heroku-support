package com.interzonedev.herokusupport.client;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;
import com.interzonedev.herokusupport.data.migration.operation.MigrationTask;
import com.interzonedev.herokusupport.data.migration.result.MigrationResult;
import com.interzonedev.herokusupport.webserver.WebServerParams;
import com.interzonedev.herokusupport.webserver.WebServerType;

public interface HerokuSupportClient {
	public MigrationResult migrateDatabase(MigrationTask migrationTask, MigrationService migrationService)
			throws MigrationOperationException;

	public void startWebServer(WebServerType webServerType, WebServerParams webServerParams) throws Exception;
}
