package com.interzonedev.herokusupport.webserver;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyWebServer implements WebServer {

    private static Logger log = (Logger) LoggerFactory.getLogger(JettyWebServer.class);

    private final WebServerParams webServerParams;

    private final SecureWebServerParams secureWebServerParams;

    private final Consumer<WebServerProperties> getWebServerProperties;

    private final BiConsumer<Server, WebAppContext> configure;

    public JettyWebServer(WebServerParams webServerParams, SecureWebServerParams secureWebServerParams,
            Consumer<WebServerProperties> getWebServerProperties, BiConsumer<Server, WebAppContext> configure) {
        this.webServerParams = webServerParams;
        this.secureWebServerParams = secureWebServerParams;
        this.getWebServerProperties = getWebServerProperties;
        this.configure = configure;
    }

    @Override
    public void start() throws Exception {

        log.debug("start");

        String contextPath = webServerParams.getContextPath();
        String webappDirLocation = webServerParams.getWebappDirLocation();
        String webConfigFileLocation = webServerParams.getWebConfigFileLocation();
        int defaultHttpWebPort = webServerParams.getDefaultWebPort();

        int httpPort = WebServerUtils.getPortFromEnv(defaultHttpWebPort);

        WebAppContext rootContext = new WebAppContext();
        rootContext.setContextPath(contextPath);
        rootContext.setDescriptor(webappDirLocation + webConfigFileLocation);
        rootContext.setResourceBase(webappDirLocation);
        rootContext.setParentLoaderPriority(true);

        // Make sure static assets are returned as UTF-8.
        MimeTypes mimeTypes = rootContext.getMimeTypes();
        mimeTypes.addMimeMapping("html", "text/html; charset=utf-8");
        mimeTypes.addMimeMapping("js", "application/javascript; charset=utf-8");
        mimeTypes.addMimeMapping("css", "text/css; charset=utf-8");

        Server server = new Server(httpPort);
        server.setHandler(rootContext);
        server.setStopAtShutdown(true);

        Integer httpsPort = null;
        if (null != secureWebServerParams) {
            httpsPort = configureSecureServer(server, secureWebServerParams);
        }

        if (null != getWebServerProperties) {
            getWebServerProperties.accept(new WebServerProperties(httpPort, httpsPort));
        }

        if (null != configure) {
            configure.accept(server, rootContext);
        }

        StringBuilder startupMessage = new StringBuilder("Starting Jetty Server ");
        startupMessage.append("with context \"").append(contextPath).append("\"");
        startupMessage.append(" on port ").append(httpPort);
        if (null != httpsPort) {
            startupMessage.append(" and secure server on port ").append(httpsPort);
        }
        log.info(startupMessage.toString());

        server.start();
        server.join();
    }

    private Integer configureSecureServer(Server server, SecureWebServerParams secureWebServerParams) {

        log.debug("configureSecureServer: Configuring Jetty server for SSL");

        String keyStorePath = secureWebServerParams.getKeyStorePath();
        String keyStorePassword = secureWebServerParams.getKeyStorePassword();
        String keyManagerPassword = secureWebServerParams.getKeyManagerPassword();
        Integer httpsPort = secureWebServerParams.getHttpsPort();

        HttpConfiguration sslConfiguration = new HttpConfiguration();
        sslConfiguration.addCustomizer(new SecureRequestCustomizer());

        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(this.getClass().getResource(keyStorePath).toExternalForm());
        sslContextFactory.setKeyStorePassword(keyStorePassword);
        sslContextFactory.setKeyManagerPassword(keyManagerPassword);

        ServerConnector sslConnector = new ServerConnector(server, new SslConnectionFactory(sslContextFactory,
                "http/1.1"), new HttpConnectionFactory(sslConfiguration));
        sslConnector.setPort(httpsPort);

        server.addConnector(sslConnector);

        return httpsPort;

    }

}
