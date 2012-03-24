package com.interzonedev.herokusupport.webserver;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WebServerUtils {
	private static Log log = LogFactory.getLog(WebServerUtils.class);

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
