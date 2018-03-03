package com.kk9software.reviewreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kk9software.reviewreminder.data.DBHelper;
import com.kk9software.reviewreminder.model.Reminder;
import com.kk9software.reviewreminder.model.Subject;

import java.util.Calendar;


public class AddSubjectActivity extends AppCompatActivity {

    private DBHelper db;
    int categoryId;
    public static final int CHOOSE_CATEGORY_REQUEST = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        db = new DBHelper(this);
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if(extras!=null) {
            String categoryName = extras.getString(ChooseCategoryActivity.EXTRA_CATEGORY_NAME,"Default category");
            categoryId = extras.getInt(ChooseCategoryActivity.EXTRA_CATEGORY_ID,0);
            TextView tvCategoryName = (TextView)findViewById(R.id.ads_category);
            tvCategoryName.setText(categoryName);
        }

    }

    public void addNewSubject(View v) {
        EditText et_subjectName = (EditText) findViewById(R.id.ads_subject_name);
        String subjectName = et_subjectName.getText().toString();
        CheckBox cb_1h = (CheckBox) findViewById(R.id.ads_1h);
        CheckBox cb_1d = (CheckBox) findViewById(R.id.ads_1d);
        CheckBox cb_3d = (CheckBox) findViewById(R.id.ads_3d);
        CheckBox cb_1w = (CheckBox) findViewById(R.id.ads_1w);
        CheckBox cb_1m = (CheckBox) findViewById(R.id.ads_1m);
        CheckBox cb_3m = (CheckBox) findViewById(R.id.ads_3m);
        CheckBox cb_6m = (CheckBox) findViewById(R.id.ads_6m);
        CheckBox cb_1y = (CheckBox) findViewById(R.id.ads_1y);
        boolean b_1h = cb_1h.isChecked();
        boolean b_1d = cb_1d.isChecked();
        boolean b_3d = cb_3d.isChecked();
        boolean b_1w = cb_1w.isChecked();
        boolean b_1m = cb_1m.isChecked();
        boolean b_3m = cb_3m.isChecked();
        boolean b_6m = cb_6m.isChecked();
        boolean b_1y = cb_1y.isChecked();
        Calendar def_cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        long time = def_cal.getTimeInMillis();
        int subjectId = db.addSubject(new Subject(categoryId,subjectName,time));
        if(b_1h) {
            cal2.add(Calendar.HOUR_OF_DAY,1);
            db.addReminder(new Reminder(subjectId,cal2.getTimeInMillis(),Reminder.ONE_HOUR));
            cal2.setTime(def_cal.getTime());
        }
        if(b_1d) {
            cal2.add(Calendar.DAY_OF_YEAR,1);
            db.addReminder(new Reminder(subjectId,cal2.getTimeInMillis(),Reminder.ONE_DAY));
            cal2.setTime(def_cal.getTime());
        }
        if(b_3d) {
            cal2.add(Calendar.DAY_OF_YEAR,3);
            db.addReminder(new Reminder(subjectId,cal2.getTimeInMillis(),Reminder.THREE_DAYS));
            cal2.setTime(def_cal.getTime());
        }
        if(b_1w) {
            cal2.add(Calendar.WEEK_OF_YEAR,1);
            db.addReminder(new Reminder(subjectId,cal2.getTimeInMillis(),Reminder.ONE_WEEK));
            cal2.setTime(def_cal.getTime());
        }
        if(b_1m) {
            cal2.add(Calendar.MONTH,1);
            db.addReminder(new Reminder(subjectId,cal2.getTimeInMillis(),Reminder.ONE_MONTH));
            cal2.setTime(def_cal.getTime());
        }
        if(b_3m) {
            cal2.add(Calendar.MONTH,3);
            db.addReminder(new Reminder(subjectId,cal2.getTimeInMillis(),Reminder.THREE_MONTHS));
            cal2.setTime(def_cal.getTime());
        }
        if(b_6m) {
            cal2.add(Calendar.MONTH,6);
            db.addReminder(new Reminder(subjectId,cal2.getTimeInMillis(),Reminder.SIX_MONTHS));
            cal2.setTime(def_cal.getTime());
        }
        if(b_1y) {
            cal2.add(Calendar.YEAR,1);
            db.addReminder(new Reminder(subjectId,cal2.getTimeInMillis(),Reminder.ONE_YEAR));
            cal2.setTime(def_cal.getTime());
        }
        Toast toast = Toast.makeText(this,"Saved", Toast.LENGTH_LONG);
        toast.show();
        finish();
    }



}
