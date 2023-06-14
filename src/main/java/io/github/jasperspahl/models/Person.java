package io.github.jasperspahl.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Person {

    protected int ID;
    protected String name;
    protected String gender;

    private final Set<Person> friends = new HashSet<>();
    private final Set<Product> products = new HashSet<>();

    public Person(int ID, String name, String gender) {
        this.ID = ID;
        this.name = name;
        this.gender = gender;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void addFriend(Person person) {
        friends.add(person);
    }

    public Set<Person> getFriends() {
        return friends;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
