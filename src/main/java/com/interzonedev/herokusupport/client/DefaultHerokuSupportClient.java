package com.interzonedev.herokusupport.client;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;
import com.interzonedev.herokusupport.data.migration.operation.MigrationTask;
import com.interzonedev.herokusupport.data.migration.operation.runner.OperationRunner;
import com.interzonedev.herokusupport.data.migration.result.MigrationResult;
import com.interzonedev.herokusupport.util.SpringContextUtils;
import com.interzonedev.herokusupport.webserver.JettyWebServer;
import com.interzonedev.herokusupport.webserver.WebServer;
import com.interzonedev.herokusupport.webserver.WebServerParams;
import com.interzonedev.herokusupport.webserver.WebServerType;

public class DefaultHerokuSupportClient implements HerokuSupportClient {

	private static final String[] CONTEXT_FILE_LOCATIONS = { "com/interzonedev/herokusupport/spring/applicationContext.xml" };

	private Logger log = (Logger) LoggerFactory.getLogger(getClass());

	@Override
	public MigrationResult migrateDatabase(MigrationTask migrationTask, MigrationService migrationService)
			throws MigrationOperationException {

		log.info("migrateDatabase: Start " + migrationTask);

		OperationRunner operationRunner = (OperationRunner) SpringContextUtils.getBean(CONTEXT_FILE_LOCATIONS,
				"operationRunner");

		MigrationResult migrationResult = operationRunner.doOperation(migrationTask, migrationService);

		log.debug("migrateDatabase: migrationResult = " + migrationResult);

		return migrationResult;
	}

	@Override
	public void startWebServer(WebServerType webServerType, WebServerParams webServerParams) throws Exception {

		log.info("startWebServer: Start " + webServerType);

		WebServer webServer = null;

		switch (webServerType) {
			case JETTY:
				webServer = new JettyWebServer(webServerParams);
				break;
			default:
				String errorMessage = "startWebServer: Unsupported web server type: " + webServerType;
				log.error(errorMessage);
				throw new IllegalArgumentException(errorMessage);
		}

		webServer.start();

		log.info("startWebServer: Started webserver");
	}

}
