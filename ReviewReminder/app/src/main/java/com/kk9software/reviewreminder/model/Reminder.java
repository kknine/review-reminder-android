package com.kk9software.reviewreminder.model;


public class Reminder {
    int _id;
    int _subjectId;
    String _subjectName;
    long _reminderTime;
    int _timeInterval;
    public static final int ONE_HOUR = 0;
    public static final int ONE_DAY = 1;
    public static final int THREE_DAYS = 2;
    public static final int ONE_WEEK = 3;
    public static final int ONE_MONTH = 4;
    public static final int THREE_MONTHS = 5;
    public static final int SIX_MONTHS = 6;
    public static final int ONE_YEAR = 7;

    public Reminder(int id, int subjectId, long reminderTime) {
        this._id = id;
        this._subjectId = subjectId;
        this._reminderTime = reminderTime;
    }
    public Reminder(int id, int subjectId, String subjectName, long reminderTime, int timeInterval) {
        this._id = id;
        this._subjectId = subjectId;
        this._reminderTime = reminderTime;
        this._subjectName = subjectName;
        this._timeInterval = timeInterval;
    }

    public Reminder(int subjectId, long reminderTime, int timeInterval) {
        this._subjectId = subjectId;
        this._reminderTime = reminderTime;
        this._timeInterval = timeInterval;
    }
    public int getId() {
        return this._id;
    }
    public int getSubjectId() {
        return this._subjectId;
    }
    public long getReminderTime() {
        return this._reminderTime;
    }
    public String getSubjectName() {
        return this._subjectName;
    }
    public int getTimeInterval() {
        return this._timeInterval;
    }

}
