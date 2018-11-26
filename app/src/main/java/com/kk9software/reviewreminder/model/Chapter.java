package com.kk9software.reviewreminder.model;


public class Chapter {
    private final int id;
    private final int categoryId;
    private String name;

    public Chapter(int id, int categoryId, String chapterName) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = chapterName;
    }
    public Chapter(int categoryId, String chapterName) {
        this.id = -1;
        this.categoryId = categoryId;
        this.name = chapterName;
    }

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }
}
