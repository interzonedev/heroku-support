package com.interzonedev.herokusupport.webserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyWebServer implements WebServer {
    private Logger log = (Logger) LoggerFactory.getLogger(getClass());

    private WebServerParams webServerParams;

    public JettyWebServer(WebServerParams webServerParams) {
        this.webServerParams = webServerParams;
    }

    @Override
    public void start() throws Exception {
        String contextPath = webServerParams.getContextPath();
        String webappDirLocation = webServerParams.getWebappDirLocation();
        String webConfigFileLocation = webServerParams.getWebConfigFileLocation();
        int defaultWebPort = webServerParams.getDefaultWebPort();

        int webPort = WebServerUtils.getPortFromEnv(defaultWebPort);

        StringBuilder startupMessage = new StringBuilder("Starting Jetty Server ");
        startupMessage.append("with context \"").append(contextPath).append("\"");
        startupMessage.append(" on port ").append(webPort);

        log.info(startupMessage.toString());

        WebAppContext rootContext = new WebAppContext();
        rootContext.setContextPath(contextPath);
        rootContext.setDescriptor(webappDirLocation + webConfigFileLocation);
        rootContext.setResourceBase(webappDirLocation);
        rootContext.setParentLoaderPriority(true);

        Server server = new Server(webPort);
        server.setHandler(rootContext);
        server.setStopAtShutdown(true);
        server.start();
        server.join();
    }
}
