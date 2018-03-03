package com.kk9software.reviewreminder.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.TimeUtils;

import com.kk9software.reviewreminder.data.ReviewContract.*;
import com.kk9software.reviewreminder.model.Reminder;
import com.kk9software.reviewreminder.model.Subject;

import java.util.ArrayList;
import java.util.Calendar;


public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "reviews.db";
    public static final int DATABASE_VERSION = 2;

    public DBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLITE_CREATE_SUBJECTS_TABLE = "CREATE TABLE " +
                SubjectEntry.TABLE_NAME + " (" +
                SubjectEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SubjectEntry.COLUMN_CATEGORY_ID + " INTEGER NOT NULL, " +
                SubjectEntry.COLUMN_SUBJECT_NAME + " TEXT NOT NULL, " +
                SubjectEntry.COLUMN_LEARN_TIME + " LONG NOT NULL" +
                ");";
        String SQLITE_CREATE_REMINDERS_TABLE = "CREATE TABLE " +
                ReminderEntry.TABLE_NAME + " (" +
                ReminderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ReminderEntry.COLUMN_SUBJECT_ID + " INTEGER NOT NULL, " +
                ReminderEntry.COLUMN_REMINDER_TIME + " LONG NOT NULL, " +
                ReminderEntry.COLUMN_TIME_INTERVAL + " INTEGER NOT NULL" +
                ");";
        String SQLITE_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoryEntry.TABLE_NAME + " (" +
                CategoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoryEntry.COLUMN_CATEGORY_NAME + " TEXT NOT NULL, " +
                ");";
        db.execSQL(SQLITE_CREATE_SUBJECTS_TABLE);
        db.execSQL(SQLITE_CREATE_REMINDERS_TABLE);
        db.execSQL(SQLITE_CREATE_CATEGORIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ReminderEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+SubjectEntry.TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<Subject> getAllSubjects() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(SubjectEntry.TABLE_NAME,null,null,null,null,null,null,null);
        ArrayList<Subject> subjectList = new ArrayList<>();
        while (c.moveToNext()) {
            subjectList.add(new Subject(c.getInt(0), c.getString(1), c.getLong(2)));
        }
        c.close();
        return subjectList;
    }
    public int addSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SubjectEntry.COLUMN_CATEGORY_ID,subject.getCategoryId());
        cv.put(SubjectEntry.COLUMN_SUBJECT_NAME,subject.getName());
        cv.put(SubjectEntry.COLUMN_LEARN_TIME, subject.getLearnTime());
        long subjectId = db.insert(SubjectEntry.TABLE_NAME,null,cv);
        return (int)subjectId;
    }

//    public ArrayList<Reminder> getAllReminders() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.query(ReminderEntry.TABLE_NAME,null,null,null,null,null,null,null);
//        ArrayList<Reminder> reminderList = new ArrayList<>();
//        while (c.moveToNext()) {
//            reminderList.add(new Reminder(c.getInt(0), c.getInt(1), c.getLong(2)));
//        }
//        c.close();
//        return reminderList;
//    }
    public ArrayList<Reminder> getAllRemindersWithSubjects() {
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT " +
                ReminderEntry.TABLE_NAME + "." + ReminderEntry._ID + ", " +
                ReminderEntry.TABLE_NAME + "." + ReminderEntry.COLUMN_SUBJECT_ID + ", " +
                SubjectEntry.TABLE_NAME + "." + SubjectEntry.COLUMN_SUBJECT_NAME + ", " +
                ReminderEntry.TABLE_NAME + "." + ReminderEntry.COLUMN_REMINDER_TIME + ", " +
                ReminderEntry.TABLE_NAME + "." + ReminderEntry.COLUMN_TIME_INTERVAL + " FROM " +
                ReminderEntry.TABLE_NAME + " INNER JOIN " +
                SubjectEntry.TABLE_NAME + " ON " +
                ReminderEntry.TABLE_NAME + "." + ReminderEntry.COLUMN_SUBJECT_ID + " = " +
                SubjectEntry.TABLE_NAME + "." + SubjectEntry._ID + " ORDER BY " +
                ReminderEntry.TABLE_NAME + "." + ReminderEntry.COLUMN_REMINDER_TIME + " ASC";
        System.out.println(SELECT_QUERY);
        Cursor c = db.rawQuery(SELECT_QUERY,null);
        ArrayList<Reminder> reminderList = new ArrayList<>();
        while (c.moveToNext()) {
            reminderList.add(new Reminder(c.getInt(0),  c.getInt(1), c.getString(2), c.getLong(3), c.getInt(4)));
        }
        c.close();
        return reminderList;
    }
    public Subject getSubject(int id) {
        SQLiteDatabase db  = this.getWritableDatabase();
        Subject result = null;
        Cursor c = db.query(SubjectEntry.TABLE_NAME,null,"id=?",new String[] {Integer.toString(id)},null,null,null,"1");
        if(c.getCount()>0) {
            result = new Subject(c.getInt(0),c.getInt(1),c.getString(2),c.getLong(3));
        }
        c.close();
        return result;
    }


    public void addReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ReminderEntry.COLUMN_SUBJECT_ID,reminder.getSubjectId());
        cv.put(ReminderEntry.COLUMN_REMINDER_TIME, reminder.getReminderTime());
        cv.put(ReminderEntry.COLUMN_TIME_INTERVAL, reminder.getTimeInterval());
        db.insert(ReminderEntry.TABLE_NAME,null,cv);
    }
    public void removeReminder(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ReminderEntry.TABLE_NAME,ReminderEntry._ID+  "=?",new String[]{Integer.toString(id)});
    }
}
