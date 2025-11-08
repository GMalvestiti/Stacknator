package net.riser876.stacknator.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

public class StacknatorGlobals {

    public static final String MOD_ID = "stacknator";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void log(String message, Object... args) {
        StacknatorGlobals.log(Level.INFO, message, args);
    }

    public static void log(Level level, String message, Object... args) {
        switch (level) {
            case INFO -> LOGGER.info(getLogFormat(message), args);
            case ERROR -> LOGGER.error(getLogFormat(message), args);
            case WARN -> LOGGER.warn(getLogFormat(message), args);
            case TRACE -> LOGGER.trace(getLogFormat(message), args);
            default -> LOGGER.debug(getLogFormat(message), args);
        }
    }

    private static String getLogFormat(String message) {
        return String.format("[Stacknator] %s", message);
    }
}
