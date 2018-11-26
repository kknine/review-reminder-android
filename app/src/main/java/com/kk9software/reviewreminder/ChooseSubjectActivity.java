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
import com.kk9software.reviewreminder.model.Subject;

public class ChooseSubjectActivity extends AppCompatActivity {

    private SubjectAdapter mSubjectAdapter;
    private DBHelper db;
    private int chapterId;
    private String chapterName;
    public static final String EXTRA_SUBJECT_NAME = "subjectName";
    public static final String EXTRA_SUBJECT_ID = "subjectId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_subject);

        final Intent intent = getIntent();
        chapterId = intent.getIntExtra(ChooseChapterActivity.EXTRA_CHAPTER_ID,-1);
        chapterName = intent.getStringExtra(ChooseChapterActivity.EXTRA_CHAPTER_NAME);
        if(chapterId==-1) {
            Toast.makeText(this,"Oops something went wrong, sorry for the inconvenience",Toast.LENGTH_SHORT).show();
            finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(chapterName);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChooseSubjectActivity.this,AddSubjectActivity.class);
                i.putExtra(ChooseChapterActivity.EXTRA_CHAPTER_ID,chapterId);
                i.putExtra(ChooseChapterActivity.EXTRA_CHAPTER_NAME,chapterName);
                startActivity(i);
            }
        });

        db = new DBHelper(this);

        mSubjectAdapter = new SubjectAdapter(db.getSubjects(chapterId), new ChooseSubjectActivity.OnItemClickListener() {
            @Override
            public void onItemLongClick(Subject Subject) {
                Intent i = new Intent(ChooseSubjectActivity.this,EditSubjectActivity.class);
                i.putExtra(EXTRA_SUBJECT_NAME,Subject.getName());
                i.putExtra(EXTRA_SUBJECT_ID,Subject.getId());
                startActivity(i);

            }
        }
        );
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mSubjectAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSubjectAdapter.swapData(db.getSubjects(chapterId));
    }

    public interface OnItemClickListener {
        void onItemLongClick(Subject subject);
    }


}
