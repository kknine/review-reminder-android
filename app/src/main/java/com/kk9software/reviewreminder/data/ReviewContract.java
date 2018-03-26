package com.kk9software.reviewreminder.data;

import android.provider.BaseColumns;


public class ReviewContract {

    public class SubjectEntry implements BaseColumns {
        public static final String TABLE_NAME = "subjects";
        public static final String COLUMN_CHAPTER_ID = "chapter_id";
        public static final String COLUMN_SUBJECT_NAME = "subject_name";
        public static final String COLUMN_LEARN_TIME = "learn_time";
    }
    public class ReminderEntry implements BaseColumns {
        public static final String TABLE_NAME = "reminders";
        public static final String COLUMN_SUBJECT_ID = "subject_id";
        public static final String COLUMN_REMINDER_TIME = "reminder_time";
        public static final String COLUMN_TIME_INTERVAL = "time_interval";
        public static final String COLUMN_IF_COMPLETED = "if_completed";
    }
    public class CategoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "categories";
        public static final String COLUMN_CATEGORY_NAME = "category_name";
    }
    public class ChapterEntry implements BaseColumns {
        public static final String TABLE_NAME = "chapters";
        public static final String COLUMN_CATEGORY_ID = "category_id";
        public static final String COLUMN_CHAPTER_NAME = "chapter_name";
    }
}
