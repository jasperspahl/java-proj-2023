package io.github.jasperspahl;

import io.github.jasperspahl.logging.ILogger;
import io.github.jasperspahl.logging.Logger;
import io.github.jasperspahl.models.Database;

import java.io.InputStream;

public class Main {
    private static final ILogger log = new Logger<>(Main.class);
    public static void main(String[] args) {
        try (InputStream stream = Main.class.getClassLoader().getResourceAsStream("productproject2023.db")) {
            Database db = new Database();
            db.loadData(stream);
        } catch (Exception e) {
            log.error(e);
        }
    }
}