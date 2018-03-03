package com.kk9software.reviewreminder.model;

public class Category {
    private final int id;
    private final String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Category(String name) {
        this.id = -1;
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public int getId() {
        return this.id;
    }

}
