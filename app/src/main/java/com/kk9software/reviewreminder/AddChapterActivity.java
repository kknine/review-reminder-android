package com.kk9software.reviewreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kk9software.reviewreminder.data.DBHelper;
import com.kk9software.reviewreminder.model.Chapter;

public class AddChapterActivity extends AppCompatActivity {

    private DBHelper db;
    private int categoryId;
    private String categoryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chapter);
        db = new DBHelper(this);
        final Intent intent = getIntent();
        categoryId = intent.getIntExtra(ChooseCategoryActivity.EXTRA_CATEGORY_ID,-1);
        categoryName = intent.getStringExtra(ChooseCategoryActivity.EXTRA_CATEGORY_NAME);
        if(categoryId==-1) {
            Toast.makeText(this,"Oops something went wrong, sorry for the inconvenience",Toast.LENGTH_LONG);
            finish();
        }
    }
    public void addChapter(View v) {
        TextView tvName = (TextView)findViewById(R.id.ach_name);
        String name = tvName.getText().toString();
        db.addChapter(new Chapter(categoryId,name));
        finish();
    }

}
