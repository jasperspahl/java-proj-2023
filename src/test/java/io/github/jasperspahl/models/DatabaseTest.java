package io.github.jasperspahl.models;

import io.github.jasperspahl.Config;
import io.github.jasperspahl.logging.LogLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private static final String TEST_DATA = "test/data.db";
    private Database db;
    private InputStream s;

    @BeforeEach
    void setUp() {
        Config.setLogLevel(LogLevel.DEBUG);
        s = getClass().getClassLoader().getResourceAsStream(TEST_DATA);
        db = new Database();
    }

    @AfterEach
    void tearDown() throws Exception {
        s.close();
    }

    @Test
    void loadData_CHECK_SIZE_OF_IMPORTS() throws Exception {
        db.loadData(s);
        assertEquals(3, db.getPersons().size());
        assertEquals(3, db.getCompanies().size());
        assertEquals(3, db.getProducts().size());
    }

    @Test
    void loadData_CHECK_PERSONS() throws Exception {

        db.loadData(s);
        Person person = db.getPersons().get(0);
        assertEquals(0, person.getID());
        assertEquals("Ellis Blair", person.getName());
        assertEquals("Male", person.getGender());

        Product product = db.getProducts().get(203);
        assertTrue(person.getProducts().contains(product));

        person = db.getPersons().get(1);
        assertEquals(1, person.getID());
        assertEquals("Mike Houston", person.getName());
        assertEquals("Male", person.getGender());
        product = db.getProducts().get(204);
        assertTrue(person.getProducts().contains(product));
        product = db.getProducts().get(205);
        assertTrue(person.getProducts().contains(product));

        person = db.getPersons().get(2);
        assertEquals(2, person.getID());
        assertEquals("Chad Gardner", person.getName());
        assertEquals("Male", person.getGender());
    }
}