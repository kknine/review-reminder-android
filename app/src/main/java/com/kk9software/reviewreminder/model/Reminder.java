package com.kk9software.reviewreminder.model;


public class Reminder {
    private final int id;
    private final int subjectId;
    private final String subjectName;
    private final long reminderTime;
    private final int timeInterval;
    public static final int ONE_HOUR = 0;
    public static final int ONE_DAY = 1;
    public static final int THREE_DAYS = 2;
    public static final int ONE_WEEK = 3;
    public static final int ONE_MONTH = 4;
    public static final int THREE_MONTHS = 5;
    public static final int SIX_MONTHS = 6;
    public static final int ONE_YEAR = 7;

    public Reminder(int id, int subjectId, String subjectName, long reminderTime, int timeInterval) {
        this.id = id;
        this.subjectId = subjectId;
        this.reminderTime = reminderTime;
        this.subjectName = subjectName;
        this.timeInterval = timeInterval;
    }

    public Reminder(int subjectId, long reminderTime, int timeInterval) {
        this.id = -1;
        this.subjectName = "Not specified";
        this.subjectId = subjectId;
        this.reminderTime = reminderTime;
        this.timeInterval = timeInterval;
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
    public String getSubjectName() {
        return this.subjectName;
    }
    public int getTimeInterval() {
        return this.timeInterval;
    }

}
