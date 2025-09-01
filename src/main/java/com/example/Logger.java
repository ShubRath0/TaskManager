package com.example;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger is a helper class for logging messages to the console.
 * Provides methods to log INFO, WARNING, and ERROR messages with timestamps.
 */
public class Logger {

    /** Formatter for timestamps in log messages */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** Private constructor to prevent instantiation */
    private Logger() {
    }

    /**
     * Logs an informational message
     * 
     * @param message The message to log
     */
    public static void info(String message) {
        System.out.println(formatMessage("INFO", message));
    }

    /**
     * Logs a warning message
     * 
     * @param message The message to log
     */
    public static void warn(String message) {
        System.out.println(formatMessage("WARN", message));
    }

    /**
     * Logs an error message
     * 
     * @param message The message to log
     */
    public static void error(String message) {
        System.err.println(formatMessage("ERROR", message));
    }

    /**
     * Logs an error message along with an exception stack trace
     * 
     * @param message The message to log
     * @param e       The exception to log
     */
    public static void error(String message, Exception e) {
        System.err.println(formatMessage("ERROR", message));
        e.printStackTrace(System.err);
    }

    /**
     * Formats the log message with a timestamp and log level
     * 
     * @param level   The log level (INFO, WARN, ERROR)
     * @param message The message to log
     * @return Formatted log string
     */
    private static String formatMessage(String level, String message) {
        return String.format("[%s] [%s] %s",
                LocalDateTime.now().format(formatter), level, message);
    }
}
