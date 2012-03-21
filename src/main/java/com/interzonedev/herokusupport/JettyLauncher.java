package com.interzonedev.herokusupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class JettyLauncher {
	private static Log log = LogFactory.getLog(JettyLauncher.class);

	public static void main(String[] args) throws Exception {
		String webappDirLocation = "src/main/webapp/";
		String webConfigFileLocation = "/WEB-INF/web.xml";
		String defaultWebPort = "5000";

		String webPort = System.getenv("PORT");
		if (StringUtils.isBlank(webPort)) {
			webPort = defaultWebPort;
		}
		log.debug("Starting Jetty Server on port " + webPort);

		WebAppContext rootContext = new WebAppContext();
		rootContext.setContextPath("/");
		rootContext.setDescriptor(webappDirLocation + webConfigFileLocation);
		rootContext.setResourceBase(webappDirLocation);
		rootContext.setParentLoaderPriority(true);

		Server server = new Server(Integer.valueOf(webPort));
		server.setHandler(rootContext);
		server.start();
		server.join();
	}
}
