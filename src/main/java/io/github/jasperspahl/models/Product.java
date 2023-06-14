package io.github.jasperspahl.models;

import io.github.jasperspahl.logging.ILogger;
import io.github.jasperspahl.logging.Logger;

public class Product {
    protected int ID;
    protected String Name;

    private Company company;

    public Product(int ID, String name) {
        this.ID = ID;
        Name = name;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
