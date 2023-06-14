package io.github.jasperspahl.models;

import java.util.HashSet;
import java.util.Set;

public class Company {
    protected int ID;
    protected String Name;

    private final Set<Product> products = new HashSet<>();

    public Company(int ID, String name) {
        this.ID = ID;
        Name = name;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public Set<Product> getProducts() {
        return products;
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
