package com.interzonedev.herokusupport.webserver;

import java.util.function.BiConsumer;

import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyWebServer implements WebServer {

    private static Logger log = (Logger) LoggerFactory.getLogger(JettyWebServer.class);

    private final WebServerParams webServerParams;

    private final BiConsumer<Server, WebAppContext> configure;

    public JettyWebServer(WebServerParams webServerParams, BiConsumer<Server, WebAppContext> configure) {
        this.webServerParams = webServerParams;
        this.configure = configure;
    }

    public JettyWebServer(WebServerParams webServerParams) {
        this(webServerParams, null);
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

        // Make sure static assets are returned as UTF-8.
        MimeTypes mimeTypes = rootContext.getMimeTypes();
        mimeTypes.addMimeMapping("html", "text/html; charset=utf-8");
        mimeTypes.addMimeMapping("js", "application/javascript; charset=utf-8");
        mimeTypes.addMimeMapping("css", "text/css; charset=utf-8");

        Server server = new Server(webPort);
        server.setHandler(rootContext);
        server.setStopAtShutdown(true);

        if (null != configure) {
            configure.accept(server, rootContext);
        }

        server.start();
        server.join();
    }

}
