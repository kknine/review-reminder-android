package com.kk9software.reviewreminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.kk9software.reviewreminder.data.DBHelper;
import com.kk9software.reviewreminder.model.Category;
import com.kk9software.reviewreminder.model.Chapter;

public class ChooseChapterActivity extends AppCompatActivity {

    private ChapterAdapter mChapterAdapter;
    private DBHelper db;
    public static final String EXTRA_CHAPTER_ID = "chapter_id";
    public static final String EXTRA_CHAPTER_NAME = "chapter_name";
    private int categoryId;
    private String categoryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_chapter);
        final Intent intent = getIntent();
        categoryId = intent.getIntExtra(ChooseCategoryActivity.EXTRA_CATEGORY_ID,-1);
        categoryName = intent.getStringExtra(ChooseCategoryActivity.EXTRA_CATEGORY_NAME);
        if(categoryId==-1) {
            Toast.makeText(this,"Oops something went wrong, sorry for the inconvenience",Toast.LENGTH_LONG);
            finish();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(categoryName);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChooseChapterActivity.this,AddChapterActivity.class);
                i.putExtra(ChooseCategoryActivity.EXTRA_CATEGORY_NAME,categoryName);
                i.putExtra(ChooseCategoryActivity.EXTRA_CATEGORY_ID,categoryId);
                startActivity(i);

            }
        });

        db = new DBHelper(this);

        mChapterAdapter = new ChapterAdapter(db.getChapters(categoryId), new ChooseChapterActivity.OnItemClickListener() {
            @Override
            public void onItemClick(Chapter chapter) {
                Intent i = new Intent(ChooseChapterActivity.this,ChooseSubjectActivity.class);
                i.putExtra(EXTRA_CHAPTER_NAME,chapter.getName());
                i.putExtra(EXTRA_CHAPTER_ID,chapter.getId());
                startActivity(i);
            }
        });

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mChapterAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mChapterAdapter.swapData(db.getChapters(categoryId));
    }

    public interface OnItemClickListener {
        void onItemClick(Chapter chapter);
    }


}
