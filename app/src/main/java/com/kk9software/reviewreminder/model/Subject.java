package com.kk9software.reviewreminder.model;


public class Subject {
    private final int id;
    private final int categoryId;
    private final String name;
    private final long learnTime;
    public Subject(int id, int categoryId, String name,long learnTime) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.learnTime = learnTime;
    }
    public Subject(int categoryId, String name, long learnTime) {
        this.id = -1;
        this.categoryId = categoryId;
        this.name = name;
        this.learnTime = learnTime;
    }
    public int getId() {
        return this.id;
    }
    public int getCategoryId() {
        return this.categoryId;
    }
    public String getName() {
        return this.name;
    }
    public long getLearnTime() {
        return this.learnTime;
    }
}
