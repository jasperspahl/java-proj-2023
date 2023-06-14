package io.github.jasperspahl.models;

import io.github.jasperspahl.I18N;
import io.github.jasperspahl.logging.ILogger;
import io.github.jasperspahl.logging.Logger;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Database {
    private final Map<Integer, Person> persons = new HashMap<>();
    private final Map<Integer, Company> companies = new HashMap<>();
    private final Map<Integer, Product> products = new HashMap<>();
    private final ILogger log = new Logger<>(this);

    public Map<Integer, Person> getPersons() {
        return persons;
    }

    public Map<Integer, Company> getCompanies() {
        return companies;
    }

    public Map<Integer, Product> getProducts() {
        return products;
    }

    public List<Person> searchPerson(String name) {
        return persons.values().stream()
                .filter(person -> person.getName().contains(name))
                .collect(Collectors.toList());
    }
    public List<Product> searchProduct(String name) {
        return products.values().stream()
                .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<String> getProductNetwork(int id) {
        return persons.get(id).getFriends().stream()
                .flatMap(person -> person.getProducts().stream())
                .sorted((a, b) -> b.getName().compareTo(a.getName()))
                .distinct()
                .map(Product::getName)
                .collect(Collectors.toList());
    }

    public List<String> getCompanyNetwork(int id) {
        var p = persons.get(id);
        return p.getFriends().stream()
                .flatMap(person -> person.getProducts().stream())
                .map(Product::getCompany)
                .filter(Objects::nonNull)
                .filter(company -> !p.getProducts().stream()
                        .map(Product::getCompany)
                        .collect(Collectors.toSet())
                        .contains(company))
                .sorted((a, b) -> b.getName().compareTo(a.getName()))
                .distinct()
                .map(Company::getName)
                .collect(Collectors.toList());
    }



    public void loadData(InputStream stream) throws Exception {
        DatabaseLoader loader = new DatabaseLoader();
        loader.loadData(stream);
        log.info(I18N.getString("loaded.data"));
        log.info(I18N.getString("found.1.0"), I18N.getString("person"), persons.size());
        log.info(I18N.getString("found.1.0"), I18N.getString("company"), companies.size());
        log.info(I18N.getString("found.1.0"), I18N.getString("product"), products.size());
    }


    public class DatabaseLoader {
        private enum State {
            START, PERSONS, PRODUCTS, COMPANIES, FRIENDS, OWNED, PRODUCTS_OF_COMPANY, END
        }

        private State state = State.START;
        private final ILogger log = new Logger<>(this);

        public void loadData(InputStream stream) throws Exception {
            try (BufferedReader bf = new BufferedReader(new InputStreamReader(stream))) {
                String line;
                while ((line = bf.readLine()) != null) {
                    // Skip comments
                    if (line.startsWith("#")) continue;
                    // Switch state
                    if (line.startsWith("New_Entity:")) {
                        nextState(line);
                        continue;
                    }
                    // Parse line
                    line = line.trim();
                    String[] parts = line.split(",");
                    parts = Arrays.stream(parts)
                            .map(str -> str.charAt(0) == '"' ? str.substring(1, str.length() - 1) : str)
                            .toArray(String[]::new);
                    parseLine(parts);
                }

            } catch (IOException e) {
                log.error(I18N.getString("error.while.reading"), e);
            }

        }

        public void parseLine(String[] parts) throws Exception {
            switch (state) {
                case PERSONS -> parsePerson(parts);
                case PRODUCTS -> parseProduct(parts);
                case COMPANIES -> parseCompany(parts);
                case FRIENDS -> parseFriend(parts);
                case OWNED -> parseOwned(parts);
                case PRODUCTS_OF_COMPANY -> parseProductsOfCompany(parts);
                case END, START -> throw new Exception("Invalid state");
            }
        }

        public void parseProductsOfCompany(String[] parts) {
            assert parts.length == 2;
            int productID = Integer.parseInt(parts[0]);
            int companyID = Integer.parseInt(parts[1]);
            Company company = companies.get(companyID);
            if (company == null) {
                log.error(
                        I18N.getString("not.found"),
                        I18N.getString("company"),
                        companyID);
                return;
            }
            Product product = products.get(productID);
            if (product == null) {
                log.error(
                        I18N.getString("product.not.found.0"),
                        I18N.getString("product"),
                        productID);
                return;
            }
            company.addProduct(product);
            product.setCompany(company);
            log.debug(I18N.getString("linked.product.0.to.company.1"), product, company);
        }

        public void parseOwned(String[] parts) {
            assert parts.length == 2;
            int ID = Integer.parseInt(parts[0]);
            int productID = Integer.parseInt(parts[1]);
            Person person = persons.get(ID);
            if (person == null) {
                log.error(
                        I18N.getString("not.found"),
                        I18N.getString("person"),
                        ID);
                return;
            }
            Product product = products.get(productID);
            if (product == null) {
                log.error(
                        I18N.getString("not.found"),
                        I18N.getString("product"),
                        productID);
                return;
            }
            person.addProduct(product);
            log.debug(I18N.getString("linked.product.0.to.person.1"), product, person);
        }

        public void parseFriend(String[] parts) {
            assert parts.length == 2;
            int ID = Integer.parseInt(parts[0]);
            int friendID = Integer.parseInt(parts[1]);
            Person person = persons.get(ID);
            if (person == null) {
                log.error(
                        I18N.getString("not.found"),
                        I18N.getString("person"),
                        ID);
                return;
            }
            Person friend = persons.get(friendID);
            if (friend == null) {
                log.error(
                        I18N.getString("not.found"),
                        I18N.getString("person"),
                        friendID);
                return;
            }
            person.addFriend(friend);
            friend.addFriend(person);
            log.debug(I18N.getString("linked.friends.0.and.1"), friend, person);
        }

        public void parseCompany(String[] parts) {
            assert parts.length == 2;
            int ID = Integer.parseInt(parts[0]);
            Company company = new Company(ID, parts[1]);
            companies.put(ID, company);
            log.debug(I18N.getString("0.added.1"), I18N.getString("company"), company);
        }

        public void parseProduct(String[] parts) {
            assert parts.length == 2;
            int ID = Integer.parseInt(parts[0]);
            var product = new Product(ID, parts[1]);
            products.put(ID, product);
            log.debug(I18N.getString("0.added.1"), I18N.getString("product"), product);
        }

        public void parsePerson(String[] parts) {
            assert parts.length == 3;
            int ID = Integer.parseInt(parts[0]);
            Person person = new Person(ID, parts[1], parts[2]);
            persons.put(ID, person);
            log.debug(I18N.getString("0.added.1"), I18N.getString("person"), person);
        }

        public void nextState(String line) {
            switch (state) {
                case START -> state = State.PERSONS;
                case PERSONS -> state = State.PRODUCTS;
                case PRODUCTS -> state = State.COMPANIES;
                case COMPANIES -> state = State.FRIENDS;
                case FRIENDS -> state = State.OWNED;
                case OWNED -> state = State.PRODUCTS_OF_COMPANY;
                case PRODUCTS_OF_COMPANY -> state = State.END;
                case END -> {
                    throw new RuntimeException("This should not happen (END state)");
                }
                default -> throw new RuntimeException("Invalid state");
            }
            log.debug(I18N.getString("stage.changed.to.0"), state.toString());
        }
    }
}
