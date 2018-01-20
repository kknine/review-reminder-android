package com.kk9software.reviewreminder.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.TimeUtils;

import com.kk9software.reviewreminder.data.ReviewContract.*;
import com.kk9software.reviewreminder.model.Subject;

import java.util.ArrayList;
import java.util.Calendar;


public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "reviews.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLITE_CREATE_SUBJECTS_TABLE = "CREATE TABLE " +
                SubjectEntry.TABLE_NAME + " (" +
                SubjectEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SubjectEntry.COLUMN_SUBJECT_NAME + " TEXT NOT NULL, " +
                SubjectEntry.COLUMN_LEARN_TIME + " LONG NOT NULL" +
                ");";
        String SQLITE_CREATE_REMINDERS_TABLE = "CREATE TABLE " +
                ReminderEntry.TABLE_NAME + " (" +
                ReminderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ReminderEntry.COLUMN_SUBJECT_ID + " INTEGER NOT NULL, " +
                ReminderEntry.COLUMN_REMINDER_TIME + " LONG NOT NULL" +
                ");";
        db.execSQL(SQLITE_CREATE_SUBJECTS_TABLE);
        db.execSQL(SQLITE_CREATE_REMINDERS_TABLE);
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
        while(c.moveToNext()) {
            subjectList.add(new Subject(c.getInt(0),c.getString(1),c.getLong(2)));
        }
        c.close();
        return subjectList;
    }
    public void addSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SubjectEntry.COLUMN_SUBJECT_NAME,subject.getName());
        cv.put(SubjectEntry.COLUMN_LEARN_TIME, subject.getLearnTime());
        db.insert(SubjectEntry.TABLE_NAME,null,cv);
    }
}
