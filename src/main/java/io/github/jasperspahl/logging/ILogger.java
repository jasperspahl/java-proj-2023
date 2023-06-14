package io.github.jasperspahl.logging;

public interface ILogger {
    void debug(String message);
    void debug(String message, Object... args);
    void debug(String message, Throwable throwable);
    void debug(String message, Throwable throwable, Object... args);
    void debug(Throwable throwable);

    void info(String message);
    void info(String message, Object... args);
    void info(String message, Throwable throwable);
    void info(String message, Throwable throwable, Object... args);
    void info(Throwable throwable);

    void warn(String message);
    void warn(String message, Object... args);
    void warn(String message, Throwable throwable);
    void warn(String message, Throwable throwable, Object... args);
    void warn(Throwable throwable);

    void error(String message);
    void error(String message, Object... args);
    void error(String message, Throwable throwable);
    void error(String message, Throwable throwable, Object... args);
    void error(Throwable throwable);
}
