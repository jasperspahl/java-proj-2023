package io.github.jasperspahl.logging;

public enum LogLevel {
    DEBUG, INFO, WARN, ERROR;
    public static LogLevel getEnum(String logLevel) {
        return switch (logLevel) {
            case "DEBUG" -> DEBUG;
            case "WARN" -> WARN;
            case "ERROR" -> ERROR;
            default -> INFO;
        };
    }
}
