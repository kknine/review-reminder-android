package com.kk9software.reviewreminder.model;


public class Subject {
    private final int id;
    private final int chapterId;
    private String name;
    private final long learnTime;
    public Subject(int id, int categoryId, String name,long learnTime) {
        this.id = id;
        this.chapterId = categoryId;
        this.name = name;
        this.learnTime = learnTime;
    }
    public Subject(int categoryId, String name, long learnTime) {
        this.id = -1;
        this.chapterId = categoryId;
        this.name = name;
        this.learnTime = learnTime;
    }
    public int getId() {
        return this.id;
    }
    public int getChapterId() {
        return this.chapterId;
    }
    public String getName() {
        return this.name;
    }
    public long getLearnTime() {
        return this.learnTime;
    }
    public void setName(String newName) {
        this.name = newName;
    }
}
