package com.kk9software.reviewreminder.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.TimeUtils;

import com.kk9software.reviewreminder.data.ReviewContract.*;
import com.kk9software.reviewreminder.model.Category;
import com.kk9software.reviewreminder.model.Chapter;
import com.kk9software.reviewreminder.model.Reminder;
import com.kk9software.reviewreminder.model.Subject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "reviews.db";
    public static final int DATABASE_VERSION = 4;

    public DBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLITE_CREATE_SUBJECTS_TABLE = "CREATE TABLE " +
                SubjectEntry.TABLE_NAME + " (" +
                SubjectEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SubjectEntry.COLUMN_CHAPTER_ID + " INTEGER NOT NULL, " +
                SubjectEntry.COLUMN_SUBJECT_NAME + " TEXT NOT NULL, " +
                SubjectEntry.COLUMN_LEARN_TIME + " LONG NOT NULL" +
                ");";
        String SQLITE_CREATE_REMINDERS_TABLE = "CREATE TABLE " +
                ReminderEntry.TABLE_NAME + " (" +
                ReminderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ReminderEntry.COLUMN_SUBJECT_ID + " INTEGER NOT NULL, " +
                ReminderEntry.COLUMN_REMINDER_TIME + " LONG NOT NULL, " +
                ReminderEntry.COLUMN_TIME_INTERVAL + " INTEGER NOT NULL, " +
                ReminderEntry.COLUMN_IF_COMPLETED + " INTEGER NOT NULL" +
                ");";
        String SQLITE_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoryEntry.TABLE_NAME + " (" +
                CategoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoryEntry.COLUMN_CATEGORY_NAME + " TEXT NOT NULL" +
                ");";
        String SQLITE_CREATE_CHAPTERS_TABLE = "CREATE TABLE " +
                ChapterEntry.TABLE_NAME + " (" +
                ChapterEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ChapterEntry.COLUMN_CATEGORY_ID + " INTEGER NOT NULL, " +
                ChapterEntry.COLUMN_CHAPTER_NAME + " TEXT NOT NULL" +
                ");";
        db.execSQL(SQLITE_CREATE_SUBJECTS_TABLE);
        db.execSQL(SQLITE_CREATE_REMINDERS_TABLE);
        db.execSQL(SQLITE_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQLITE_CREATE_CHAPTERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ReminderEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SubjectEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CategoryEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ChapterEntry.TABLE_NAME);
        onCreate(db);
    }

    public int addSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SubjectEntry.COLUMN_CHAPTER_ID,subject.getChapterId());
        cv.put(SubjectEntry.COLUMN_SUBJECT_NAME,subject.getName());
        cv.put(SubjectEntry.COLUMN_LEARN_TIME, subject.getLearnTime());
        long subjectId = db.insert(SubjectEntry.TABLE_NAME,null,cv);
        return (int)subjectId;
    }
    public void addReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ReminderEntry.COLUMN_SUBJECT_ID,reminder.getSubjectId());
        cv.put(ReminderEntry.COLUMN_REMINDER_TIME, reminder.getReminderTime());
        cv.put(ReminderEntry.COLUMN_TIME_INTERVAL, reminder.getTimeInterval());
        cv.put(ReminderEntry.COLUMN_IF_COMPLETED, reminder.getIfCompleted());
        db.insert(ReminderEntry.TABLE_NAME,null,cv);
    }
    public int addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CategoryEntry.COLUMN_CATEGORY_NAME,category.getName());
        return (int)db.insert(CategoryEntry.TABLE_NAME,null,cv);
    }
    public int addChapter(Chapter chapter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put(ChapterEntry.COLUMN_CATEGORY_ID,chapter.getCategoryId());
        cv.put(ChapterEntry.COLUMN_CHAPTER_NAME,chapter.getName());
        return (int)db.insert(ChapterEntry.TABLE_NAME,null,cv);
    }

    public boolean updateCategory(Category newCategory) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put(CategoryEntry.COLUMN_CATEGORY_NAME,newCategory.getName());
        int numRows = db.update(CategoryEntry.TABLE_NAME,cv,CategoryEntry._ID + "=?",new String[] {Integer.toString(newCategory.getId())});
        return numRows == 1;
    }
    public boolean updateChapter(Chapter newChapter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put(ChapterEntry.COLUMN_CHAPTER_NAME,newChapter.getName());
        int numRows = db.update(ChapterEntry.TABLE_NAME,cv,ChapterEntry._ID + "=?",new String[] {Integer.toString(newChapter.getId())});
        return numRows == 1;
    }
    public boolean updateSubject(Subject newSubject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put(SubjectEntry.COLUMN_SUBJECT_NAME,newSubject.getName());
        int numRows = db.update(SubjectEntry.TABLE_NAME,cv,SubjectEntry._ID + "=?",new String[] {Integer.toString(newSubject.getId())});
        return numRows == 1;
    }

    public Subject getSubject(int subjectId) {
        SQLiteDatabase db  = this.getReadableDatabase();
        Subject result = null;
        Cursor c = db.query(SubjectEntry.TABLE_NAME,null,SubjectEntry._ID + "=?",new String[] {Integer.toString(subjectId)},null,null,null,"1");
        if(c.moveToFirst()) {
            result = new Subject(c.getInt(0),c.getInt(1),c.getString(2),c.getLong(3));
        }
        c.close();
        return result;
    }
    public Reminder getReminder(int reminderId) {
        SQLiteDatabase db  = this.getReadableDatabase();
        Reminder result = null;
        Cursor c = db.query(ReminderEntry.TABLE_NAME,null,ReminderEntry._ID + "=?",new String[] {Integer.toString(reminderId)},null,null,null,"1");
        if(c.moveToFirst()) {
            result = new Reminder(c.getInt(0),c.getInt(1),c.getLong(2),c.getInt(3),c.getInt(4));
        }
        c.close();
        return result;
    }
    public Category getCategory(int categoryId) {
        Category category = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(CategoryEntry.TABLE_NAME,null,CategoryEntry._ID + "=?",new String[] {Integer.toString(categoryId)},null,null,null,"1");
        if(c.moveToFirst()) {
            category = new Category(c.getInt(0),c.getString(1));
        }
        c.close();
        return category;
    }
    public Chapter getChapter(int chapterId) {
        Chapter chapter = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(ChapterEntry.TABLE_NAME,null,ChapterEntry._ID + "=?",new String[] {Integer.toString(chapterId)},null,null,null,"1");
        if(c.moveToFirst()) {
            chapter = new Chapter(c.getInt(0),c.getInt(1),c.getString(2));
        }
        c.close();
        return chapter;
    }

    public ArrayList<Subject> getSubjects(int chapterId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(SubjectEntry.TABLE_NAME,null,SubjectEntry.COLUMN_CHAPTER_ID + "=?",new String[] {Integer.toString(chapterId)},null,null,SubjectEntry._ID + " ASC",null);
        ArrayList<Subject> subjectList = new ArrayList<>();
        while (c.moveToNext()) {
            subjectList.add(new Subject(c.getInt(0), c.getInt(1), c.getString(2), c.getLong(3)));
        }
        c.close();
        return subjectList;
    }
    public ArrayList<Reminder> getAllUnCompletedReminders() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(ReminderEntry.TABLE_NAME,null,ReminderEntry.COLUMN_IF_COMPLETED + "=?",new String[]{Integer.toString(Reminder.NOT_COMPLETED)},null,null,ReminderEntry.COLUMN_REMINDER_TIME + " ASC",null);
        ArrayList<Reminder> reminderList = new ArrayList<>();
        while (c.moveToNext()) {
            reminderList.add(new Reminder(c.getInt(0), c.getInt(1), c.getLong(2), c.getInt(3), c.getInt(4)));
        }
        c.close();
        return reminderList;
    }
    public ArrayList<Category> getAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(CategoryEntry.TABLE_NAME,null,null,null,null,null,null,null);
        ArrayList<Category> categoryList = new ArrayList<>();
        while(c.moveToNext()) {
            categoryList.add(new Category(c.getInt(0),c.getString(1)));
        }
        c.close();
        return categoryList;
    }
    public ArrayList<Chapter> getChapters(int categoryId) {
        ArrayList<Chapter> chapterList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(ChapterEntry.TABLE_NAME,null,ChapterEntry.COLUMN_CATEGORY_ID + "=?",new String[] {Integer.toString(categoryId)},null,null,ChapterEntry._ID + " ASC",null);
        while(c.moveToNext()) {
            chapterList.add(new Chapter(c.getInt(0),c.getInt(1),c.getString(2)));
        }
        c.close();
        return chapterList;
    }
//    public ArrayList<Reminder> getAllRemindersWithSubjects() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String SELECT_QUERY = "SELECT " +
//                ReminderEntry.TABLE_NAME + "." + ReminderEntry._ID + ", " +
//                ReminderEntry.TABLE_NAME + "." + ReminderEntry.COLUMN_SUBJECT_ID + ", " +
//                SubjectEntry.TABLE_NAME + "." + SubjectEntry.COLUMN_SUBJECT_NAME + ", " +
//                ReminderEntry.TABLE_NAME + "." + ReminderEntry.COLUMN_REMINDER_TIME + ", " +
//                ReminderEntry.TABLE_NAME + "." + ReminderEntry.COLUMN_TIME_INTERVAL + " FROM " +
//                ReminderEntry.TABLE_NAME + " INNER JOIN " +
//                SubjectEntry.TABLE_NAME + " ON " +
//                ReminderEntry.TABLE_NAME + "." + ReminderEntry.COLUMN_SUBJECT_ID + " = " +
//                SubjectEntry.TABLE_NAME + "." + SubjectEntry._ID + " ORDER BY " +
//                ReminderEntry.TABLE_NAME + "." + ReminderEntry.COLUMN_REMINDER_TIME + " ASC";
//        System.out.println(SELECT_QUERY);
//        Cursor c = db.rawQuery(SELECT_QUERY,null);
//        ArrayList<Reminder> reminderList = new ArrayList<>();
//        while (c.moveToNext()) {
//            reminderList.add(new Reminder(c.getInt(0),  c.getInt(1), c.getString(2), c.getLong(3), c.getInt(4), c.getInt(5)));
//        }
//        c.close();
//        return reminderList;
//    }




    public void removeReminder(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ReminderEntry.TABLE_NAME,ReminderEntry._ID + "=?",new String[]{Integer.toString(id)});
    }
    public void completeReview(int reminderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ReminderEntry.COLUMN_IF_COMPLETED,Reminder.COMPLETED);
        db.update(ReminderEntry.TABLE_NAME,cv,ReminderEntry._ID + "=?",new String[]{Integer.toString(reminderId)});
        Reminder previous = getReminder(reminderId);
        int subjectId = previous.getSubjectId();
        int timeInterval = previous.getTimeInterval();
        if(timeInterval!=Reminder.ONE_YEAR)
            timeInterval++;
        addReminder(new Reminder(subjectId,newReminderTime(timeInterval),timeInterval,Reminder.NOT_COMPLETED));
    }
    public long newReminderTime(int timeInterval) {
        Calendar cal = Calendar.getInstance();
        switch(timeInterval) {
            case Reminder.ONE_HOUR:
                cal.add(Calendar.HOUR_OF_DAY, 1);
                break;
            case Reminder.ONE_DAY:
                cal.add(Calendar.DAY_OF_YEAR, 1);
                break;
            case Reminder.THREE_DAYS:
                cal.add(Calendar.DAY_OF_YEAR, 3);
                break;
            case Reminder.ONE_WEEK:
                cal.add(Calendar.WEEK_OF_YEAR, 1);
                break;
            case Reminder.ONE_MONTH:
                cal.add(Calendar.MONTH, 1);
                break;
            case Reminder.THREE_MONTHS:
                cal.add(Calendar.MONTH, 3);
                break;
            case Reminder.SIX_MONTHS:
                cal.add(Calendar.MONTH, 6);
                break;
            case Reminder.ONE_YEAR:
                cal.add(Calendar.YEAR, 1);
                break;
            default:
                cal.add(Calendar.YEAR, 1);
                break;
        }
        return cal.getTimeInMillis();
    }




}
