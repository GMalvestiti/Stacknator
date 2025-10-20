package net.riser876.stacknator.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

public class StacknatorGlobals {

    public static final String MOD_ID = "stacknator";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void log(String message, Object... args) {
        StacknatorGlobals.log(true, Level.INFO, message, args);
    }

    public static void log(boolean isEnabled, String message, Object... args) {
        StacknatorGlobals.log(isEnabled, Level.INFO, message, args);
    }

    public static void log(Level level, String message, Object... args) {
        StacknatorGlobals.log(true, level, message, args);
    }

    public static void log(boolean isEnabled, Level level, String message, Object... args) {
        if (!isEnabled) {
            return;
        }

        switch (level) {
            case ERROR -> LOGGER.error(getLogFormat(message), args);
            case WARN -> LOGGER.warn(getLogFormat(message), args);
            case DEBUG -> LOGGER.debug(getLogFormat(message), args);
            case TRACE -> LOGGER.trace(getLogFormat(message), args);
            default -> LOGGER.info(getLogFormat(message), args);
        }
    }

    private static String getLogFormat(String message) {
        return String.format("[Stacknator] %s", message);
    }
}
