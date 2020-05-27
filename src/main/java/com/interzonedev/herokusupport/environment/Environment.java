package com.interzonedev.herokusupport.environment;

/**
 * Enumeration that identifies the environment in which the application is running.
 *
 * @author mmarkarian
 */
public enum Environment {

    LOCAL("local"), PRODUCTION("production");

    /**
     * The environment in which the application is running.
     */
    private static final Environment CURRENT_ENVIRONMENT = setCurrentEnvironment();

    private final String environmentName;

    private Environment(String environmentName) {
        this.environmentName = environmentName;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    /**
     * Gets the {@link Environment} with the specified name.
     *
     * @param environmentName The name of the {@link Environment} to get.
     * @return Returns the {@link Environment} with the specified name or null if the specified environment name does
     * not correspond to any definied {@link Environment} values.
     */
    public static Environment fromEnvironmentName(String environmentName) {

        for (Environment environment : Environment.values()) {
            if (environment.getEnvironmentName().equals(environmentName)) {
                return environment;
            }
        }

        return null;
    }

    /**
     * Gets the environment in which the application is running.
     *
     * @return Returns {@link Environment#PRODUCTION} if there is any environment variable name that starts with
     * "HEROKU", otherwise returns {@link Environment#LOCAL}.
     */
    public static Environment getCurrentEnvironment() {
        return CURRENT_ENVIRONMENT;
    }

    /**
     * Determines the environment in which the application is running. If there is any environment variable name that
     * starts with "HEROKU" then the current environment is set as {@link Environment#PRODUCTION}, otherwise it is set a
     * {@link Environment#LOCAL}.
     *
     * @return Returns {@link Environment#PRODUCTION} if there is any environment variable name that starts with
     * "HEROKU", otherwise returns {@link Environment#LOCAL}.
     */
    private static Environment setCurrentEnvironment() {
        if (System.getenv().keySet().stream().anyMatch((key) -> key.startsWith("HEROKU"))) {
            return PRODUCTION;
        } else {
            return LOCAL;
        }
    }

}
