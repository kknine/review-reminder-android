package com.kk9software.reviewreminder.data;

import android.provider.BaseColumns;


public class ReviewContract {

    public class SubjectEntry implements BaseColumns {
        public static final String TABLE_NAME = "subjects";
        public static final String COLUMN_SUBJECT_NAME = "subjectName";
        public static final String COLUMN_LEARN_TIME = "learnTime";
    }
    public class ReminderEntry implements BaseColumns {
        public static final String TABLE_NAME = "reminders";
        public static final String COLUMN_SUBJECT_ID = "subjectId";
        public static final String COLUMN_REMINDER_TIME = "reminderTime";
    }
}
