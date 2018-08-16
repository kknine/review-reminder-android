package com.kk9software.reviewreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kk9software.reviewreminder.data.DBHelper;
import com.kk9software.reviewreminder.model.Category;
import com.kk9software.reviewreminder.model.Chapter;

public class EditChapterActivity extends AppCompatActivity {

    private int chapterId;
    private String chapterName;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chapter);
        final Intent intent = getIntent();
        chapterId = intent.getIntExtra(ChooseChapterActivity.EXTRA_CHAPTER_ID,-1);
        chapterName = intent.getStringExtra(ChooseChapterActivity.EXTRA_CHAPTER_NAME);
        if(chapterId==-1) {
            Toast.makeText(this,"Oops something went wrong, sorry for the inconvenience",Toast.LENGTH_LONG).show();
            finish();
        }
        db = new DBHelper(this);
        EditText chapterNameET = (EditText)findViewById(R.id.ech_chapter);
        chapterNameET.setText(chapterName);
    }

    public void updateChapter(View v) {
        EditText chapterNameET = (EditText)findViewById(R.id.ech_chapter);
        String newChapterName = chapterNameET.getText().toString();
        if(!newChapterName.equals(chapterName)) {
            Chapter newChapter = db.getChapter(chapterId);
            newChapter.setName(newChapterName);
            if(db.updateChapter(newChapter)) {
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
