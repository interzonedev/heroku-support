package com.interzonedev.herokusupport.webserver;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServerUtils {
    private static final Logger log = LoggerFactory.getLogger(WebServerUtils.class);

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
