package com.interzonedev.herokusupport.webserver;

public class WebServerParams {

    private String contextPath = "/";

    private String webappDirLocation = "src/main/webapp";

    private String webConfigFileLocation = "/WEB-INF/web.xml";

    private int defaultWebPort = 5000;

    public WebServerParams(int defaultWebPort) {
        this.defaultWebPort = defaultWebPort;
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
