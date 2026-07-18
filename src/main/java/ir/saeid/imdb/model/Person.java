package ir.saeid.imdb.model;

import static ir.saeid.imdb.model.DataUtils.getInteger;

public class Person {
    String id;
    String name;
    int deathYear = 0;
//    Boolean alive;
    public Person() {
    }

    public Person(String[] columns) {
        this.id = columns[0];
        this.name = columns[1];
        this.deathYear = getInteger(columns[3]);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(int deathYear) {
        this.deathYear = deathYear;
    }

    @Override
    public String toString() {
        return "name: " + name + ", deathYear:" + deathYear;
    }
}
