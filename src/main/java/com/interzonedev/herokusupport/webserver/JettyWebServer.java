package com.interzonedev.herokusupport.webserver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class JettyWebServer implements WebServer {
	private Log log = LogFactory.getLog(getClass());

	private String contextPath = "/";
	private String webappDirLocation = "src/main/webapp";
	private String webConfigFileLocation = "/WEB-INF/web.xml";
	private int webPort = 5000;

	public JettyWebServer() {
	}

	public JettyWebServer(int webPort) {
		this.webPort = webPort;
	}

	public JettyWebServer(String contextPath, String webappDirLocation, String webConfigFileLocation, int webPort) {
		this.contextPath = contextPath;
		this.webappDirLocation = webappDirLocation;
		this.webConfigFileLocation = webConfigFileLocation;
		this.webPort = webPort;
	}

	@Override
	public void start() throws Exception {
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
		server.start();
		server.join();
	}
}
