package com.kk9software.reviewreminder.model;


public class Subject {
    private int _id;
    private String _name;
    private long _learnTime;
    public Subject(int id, String name,long learnTime) {
        this._id = id;
        this._name = name;
        this._learnTime = learnTime;
    }
    public Subject(String name, long learnTime) {
        this._name = name;
        this._learnTime = learnTime;
    }
    public int getId() {
        return this._id;
    }
    public String getName() {
        return this._name;
    }
    public long getLearnTime() {
        return this._learnTime;
    }
}
