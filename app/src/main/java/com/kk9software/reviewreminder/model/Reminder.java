package com.kk9software.reviewreminder.model;


public class Reminder {
    private final int id;
    private final int subjectId;
    private final long reminderTime;
    private final int timeInterval;
    private final int ifCompleted;
    public static final int ONE_HOUR = 0;
    public static final int ONE_DAY = 1;
    public static final int THREE_DAYS = 2;
    public static final int ONE_WEEK = 3;
    public static final int ONE_MONTH = 4;
    public static final int THREE_MONTHS = 5;
    public static final int SIX_MONTHS = 6;
    public static final int ONE_YEAR = 7;
    public static final int COMPLETED = 1;
    public static final int NOT_COMPLETED = 0;

    public Reminder(int id, int subjectId, long reminderTime, int timeInterval, int ifCompleted) {
        this.id = id;
        this.subjectId = subjectId;
        this.reminderTime = reminderTime;
        this.timeInterval = timeInterval;
        this.ifCompleted = ifCompleted;
    }

    public Reminder(int subjectId, long reminderTime, int timeInterval, int ifCompleted) {
        this.id = -1;
        this.subjectId = subjectId;
        this.reminderTime = reminderTime;
        this.timeInterval = timeInterval;
        this.ifCompleted = ifCompleted;
    }
    public int getId() {
        return this.id;
    }
    public int getSubjectId() {
        return this.subjectId;
    }
    public long getReminderTime() {
        return this.reminderTime;
    }
    public int getTimeInterval() {
        return this.timeInterval;
    }
    public int getIfCompleted() {
        return ifCompleted;
    }

}
