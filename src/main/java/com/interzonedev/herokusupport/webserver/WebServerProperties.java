package com.interzonedev.herokusupport.webserver;

/**
 * Immutable value object with web server properties after it has been configured and before it is started.
 *
 * @author mmarkarian
 */
public class WebServerProperties {

    private final Integer httpPort;

    private final Integer httpsPort;

    public WebServerProperties(Integer httpPort, Integer httpsPort) {
        this.httpPort = httpPort;
        this.httpsPort = httpsPort;
    }

    public Integer getHttpPort() {
        return httpPort;
    }

    public Integer getHttpsPort() {
        return httpsPort;
    }

}
