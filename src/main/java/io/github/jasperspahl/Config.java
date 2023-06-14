package io.github.jasperspahl;

import io.github.jasperspahl.logging.LogLevel;

import java.util.Properties;

public class Config {

    public static final String DATABASE_FILE = "productproject2023.db";

    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(Config.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static void set(String key, String value) {
        properties.setProperty(key, value);
    }

    public static void setLogLevel(LogLevel logLevel) {
        set("log.level", logLevel.toString());
    }

    public static LogLevel getLogLevel() {
        return LogLevel.getEnum(get("log.level"));
    }
}
