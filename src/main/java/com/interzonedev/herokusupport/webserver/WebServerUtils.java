package com.interzonedev.herokusupport.webserver;

import org.apache.commons.lang.StringUtils;

public class WebServerUtils {
	public static int getPortFromEnv(int defaultWebPort) {
		int webPort = defaultWebPort;

		String webPortFromEnv = System.getenv("PORT");
		if (StringUtils.isNotBlank(webPortFromEnv)) {
			webPort = Integer.valueOf(webPortFromEnv);
		}

		return webPort;
	}
}
