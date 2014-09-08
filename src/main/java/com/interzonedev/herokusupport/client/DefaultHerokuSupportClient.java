package com.interzonedev.herokusupport.client;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;
import com.interzonedev.herokusupport.data.migration.operation.MigrationTask;
import com.interzonedev.herokusupport.data.migration.operation.runner.OperationRunner;
import com.interzonedev.herokusupport.data.migration.result.MigrationResult;
import com.interzonedev.herokusupport.util.SpringContextUtils;
import com.interzonedev.herokusupport.webserver.JettyWebServer;
import com.interzonedev.herokusupport.webserver.SecureWebServerParams;
import com.interzonedev.herokusupport.webserver.WebServer;
import com.interzonedev.herokusupport.webserver.WebServerParams;
import com.interzonedev.herokusupport.webserver.WebServerProperties;
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

    @SuppressWarnings("unchecked")
    @Override
    public <T, U> void startWebServer(WebServerType webServerType, WebServerParams webServerParams,
            SecureWebServerParams secureWebServerParams, Consumer<WebServerProperties> getWebServerProperties,
            BiConsumer<T, U> configure) throws Exception {

        log.info("startWebServer: Start " + webServerType);

        WebServer webServer = null;

        switch (webServerType) {
            case JETTY:
                webServer = new JettyWebServer(webServerParams, secureWebServerParams, getWebServerProperties,
                        (BiConsumer<Server, WebAppContext>) configure);
                break;
            default:
                String errorMessage = "startWebServer: Unsupported web server type: " + webServerType;
                log.error(errorMessage);
                throw new IllegalArgumentException(errorMessage);
        }

        log.info("startWebServer: Starting webserver");

        webServer.start();

    }

}
