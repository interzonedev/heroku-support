package com.interzonedev.herokusupport.client;

import com.interzonedev.herokusupport.data.migration.MigrationOperationException;
import com.interzonedev.herokusupport.data.migration.MigrationService;
import com.interzonedev.herokusupport.data.migration.operation.MigrationTask;
import com.interzonedev.herokusupport.data.migration.result.MigrationResult;
import com.interzonedev.herokusupport.webserver.SecureWebServerParams;
import com.interzonedev.herokusupport.webserver.WebServerParams;
import com.interzonedev.herokusupport.webserver.WebServerProperties;
import com.interzonedev.herokusupport.webserver.WebServerType;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface HerokuSupportClient {
    MigrationResult migrateDatabase(MigrationTask migrationTask, MigrationService migrationService)
            throws MigrationOperationException;

    <T, U> void startWebServer(WebServerType webServerType, WebServerParams webServerParams,
                               SecureWebServerParams secureWebServerParams,
                               Consumer<WebServerProperties> getWebServerProperties, BiConsumer<T, U> configure)
            throws Exception;
}
