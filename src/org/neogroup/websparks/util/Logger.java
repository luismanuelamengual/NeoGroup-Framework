
package org.neogroup.websparks.util;

public class Logger {

    public static final String LOGGER_NAME = "websparks_logger";

    public static final java.util.logging.Logger instance;

    static {
        instance = java.util.logging.Logger.getLogger(LOGGER_NAME);
    }

    public static java.util.logging.Logger getInstance() {
        return instance;
    }
}