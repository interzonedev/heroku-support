package com.interzonedev.herokusupport.webserver;

public class WebServerParams {

    private final String contextPath;

    private final String webappDirLocation;

    private final String webConfigFileLocation;

    private final int defaultWebPort;

    public WebServerParams(int defaultWebPort) {
        this.contextPath = "/";
        this.webappDirLocation = "src/main/webapp";
        this.webConfigFileLocation = "/WEB-INF/web.xml";
        this.defaultWebPort = 5000;
    }

    public WebServerParams(String contextPath, String webappDirLocation, String webConfigFileLocation,
            int defaultWebPort) {
        this.contextPath = contextPath;
        this.webappDirLocation = webappDirLocation;
        this.webConfigFileLocation = webConfigFileLocation;
        this.defaultWebPort = defaultWebPort;
    }

    public String getContextPath() {
        return contextPath;
    }

    public String getWebappDirLocation() {
        return webappDirLocation;
    }

    public String getWebConfigFileLocation() {
        return webConfigFileLocation;
    }

    public int getDefaultWebPort() {
        return defaultWebPort;
    }
}
