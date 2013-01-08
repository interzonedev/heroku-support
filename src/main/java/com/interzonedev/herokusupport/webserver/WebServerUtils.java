package com.interzonedev.herokusupport.webserver;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class WebServerUtils {
	private static Logger log = (Logger) LoggerFactory.getLogger(WebServerUtils.class);

	public static int getPortFromEnv(int defaultWebPort) {
		int webPort = defaultWebPort;

		String webPortFromEnv = System.getenv("PORT");
		log.info("getPortFromEnv: webPortFromEnv = " + webPortFromEnv);

		if (StringUtils.isNotBlank(webPortFromEnv)) {
			webPort = Integer.valueOf(webPortFromEnv);
		}

		return webPort;
	}
}
