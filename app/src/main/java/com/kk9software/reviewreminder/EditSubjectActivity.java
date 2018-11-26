package com.kk9software.reviewreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kk9software.reviewreminder.data.DBHelper;
import com.kk9software.reviewreminder.model.Chapter;
import com.kk9software.reviewreminder.model.Subject;

public class EditSubjectActivity extends AppCompatActivity {

    private int subjectId;
    private String subjectName;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subject);
        final Intent intent = getIntent();
        subjectId = intent.getIntExtra(ChooseSubjectActivity.EXTRA_SUBJECT_ID,-1);
        subjectName = intent.getStringExtra(ChooseSubjectActivity.EXTRA_SUBJECT_NAME);
        if(subjectId==-1) {
            Toast.makeText(this,"Oops something went wrong, sorry for the inconvenience",Toast.LENGTH_LONG).show();
            finish();
        }
        db = new DBHelper(this);
        EditText subjectNameET = (EditText)findViewById(R.id.esu_subject);
        subjectNameET.setText(subjectName);
    }

    public void updateSubject(View v) {
        EditText subjectNameET = (EditText)findViewById(R.id.esu_subject);
        String newSubjectName = subjectNameET.getText().toString();
        if(!newSubjectName.equals(subjectName)) {
            Subject newSubject = db.getSubject(subjectId);
            newSubject.setName(newSubjectName);
            if(db.updateSubject(newSubject)) {
                Toast.makeText(this, "Successfully updated chapter", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "There was an error while updating", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No changes were recorded", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
