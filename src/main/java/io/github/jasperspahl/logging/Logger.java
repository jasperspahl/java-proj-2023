package io.github.jasperspahl.logging;

import io.github.jasperspahl.Config;

import java.io.PrintStream;
import java.text.MessageFormat;

public class Logger<T> implements ILogger {

    private LogLevel logLevel;
    private String className;
    private PrintStream out = System.out;

    public Logger(T type) {
        className = type.getClass().getName();
        logLevel = Config.getLogLevel();
    }
    public Logger (T type, LogLevel logLevel) {
        className = type.getClass().getName();
        this.logLevel = logLevel;
    }

    public Logger (T type, LogLevel logLevel, PrintStream out) {
        className = type.getClass().getName();
        this.logLevel = logLevel;
        this.out = out;
    }

    public void debug(String message) {
        if (logLevel == LogLevel.DEBUG) {
            log("[DEBUG] " + className + ": " + message);
        }
    }

    public void debug(String message, Object... args) {
        if (logLevel == LogLevel.DEBUG) {
            log("[DEBUG] " + className + ": " + message, args);
        }
    }

    public void debug(String message, Throwable throwable) {
        if (logLevel == LogLevel.DEBUG) {
            log("[DEBUG] " + className + ": " + message, throwable);
        }
    }

    public void debug(String message, Throwable throwable, Object... args) {
        if (logLevel == LogLevel.DEBUG) {
            log("[DEBUG] " + className + ": " + message, throwable, args);
        }
    }

    public void debug(Throwable throwable) {
        if (logLevel == LogLevel.DEBUG) {
            log("[DEBUG] " + className + ": " + throwable);
        }
    }

    public void info(String message) {
        if (logLevel == LogLevel.DEBUG || logLevel == LogLevel.INFO) {
            log("[INFO] " + className + ": " + message);
        }
    }

    public void info(String message, Object... args) {
        if (logLevel == LogLevel.DEBUG || logLevel == LogLevel.INFO) {
            log("[INFO] " + className + ": " + message, args);
        }
    }

    public void info(String message, Throwable throwable) {
        if (logLevel == LogLevel.DEBUG || logLevel == LogLevel.INFO) {
            log("[INFO] " + className + ": " + message, throwable);
        }
    }

    public void info(String message, Throwable throwable, Object... args) {
        if (logLevel == LogLevel.DEBUG || logLevel == LogLevel.INFO) {
            log("[INFO] " + className + ": " + message, throwable, args);
        }
    }

    public void info(Throwable throwable) {
        if (logLevel == LogLevel.DEBUG || logLevel == LogLevel.INFO) {
            log("[INFO] " + className + ": " + throwable);
        }
    }

    public void warn(String message) {
        if (logLevel == LogLevel.DEBUG || logLevel == LogLevel.INFO || logLevel == LogLevel.WARN) {
            log("[WARN] " + className + ": " + message);
        }
    }

    public void warn(String message, Object... args) {
        if (logLevel == LogLevel.DEBUG || logLevel == LogLevel.INFO || logLevel == LogLevel.WARN) {
            log("[WARN] " + className + ": " + message, args);
        }
    }

    public void warn(String message, Throwable throwable) {
        if (logLevel == LogLevel.DEBUG || logLevel == LogLevel.INFO || logLevel == LogLevel.WARN) {
            log("[WARN] " + className + ": " + message, throwable);
        }
    }

    public void warn(String message, Throwable throwable, Object... args) {
        if (logLevel == LogLevel.DEBUG || logLevel == LogLevel.INFO || logLevel == LogLevel.WARN) {
            log("[WARN] " + className + ": " + message, throwable, args);
        }
    }

    public void warn(Throwable throwable) {
        if (logLevel == LogLevel.DEBUG || logLevel == LogLevel.INFO || logLevel == LogLevel.WARN) {
            log("[WARN] " + className + ": " + throwable);
        }
    }

    public void error(String message) {
        if (logLevel == LogLevel.DEBUG || logLevel == LogLevel.INFO || logLevel == LogLevel.WARN || logLevel == LogLevel.ERROR) {
            log("[ERROR] " + className + ": " + message);
        }
    }

    public void error(String message, Object... args) {
        if (logLevel == LogLevel.DEBUG || logLevel == LogLevel.INFO || logLevel == LogLevel.WARN || logLevel == LogLevel.ERROR) {
            log("[ERROR] " + className + ": " + message, args);
        }
    }

    public void error(String message, Throwable throwable) {
        if (logLevel == LogLevel.DEBUG || logLevel == LogLevel.INFO || logLevel == LogLevel.WARN || logLevel == LogLevel.ERROR) {
            log("[ERROR] " + className + ": " + message, throwable);
        }
    }

    public void error(String message, Throwable throwable, Object... args) {
        if (logLevel == LogLevel.DEBUG || logLevel == LogLevel.INFO || logLevel == LogLevel.WARN || logLevel == LogLevel.ERROR) {
            log("[ERROR] " + className + ": " + message, throwable, args);
        }
    }

    public void error(Throwable throwable) {
        if (logLevel == LogLevel.DEBUG || logLevel == LogLevel.INFO || logLevel == LogLevel.WARN || logLevel == LogLevel.ERROR) {
            log("[ERROR] " + className + ": " + throwable);
        }
    }

    private void log(String message) {
        out.println(message);
    }

    private void log(String message, Object... args) {
        out.println(MessageFormat.format(message, args));
    }

    private void log(String message, Throwable throwable) {
        out.println(message);
        throwable.printStackTrace();
    }

    private void log(String message, Throwable throwable, Object... args) {
        out.println(MessageFormat.format(message, args));
        throwable.printStackTrace();
    }
}
