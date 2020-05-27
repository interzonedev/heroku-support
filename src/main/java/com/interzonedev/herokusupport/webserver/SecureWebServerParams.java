package com.interzonedev.herokusupport.webserver;

/**
 * Immuatable value object that holds the necessary parameters for starting a secure web server.
 *
 * @author mmarkarian
 */
public class SecureWebServerParams {

    private final String keyStorePath;
    private final String keyStorePassword;
    private final String keyManagerPassword;
    private final Integer httpsPort;

    public SecureWebServerParams(String keyStorePath, String keyStorePassword, String keyManagerPassword,
                                 Integer httpsPort) {
        this.keyStorePath = keyStorePath;
        this.keyStorePassword = keyStorePassword;
        this.keyManagerPassword = keyManagerPassword;
        this.httpsPort = httpsPort;
    }

    public String getKeyStorePath() {
        return keyStorePath;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public String getKeyManagerPassword() {
        return keyManagerPassword;
    }

    public Integer getHttpsPort() {
        return httpsPort;
    }

}
