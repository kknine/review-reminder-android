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

import com.kk9software.reviewreminder.data.DBHelper;
import com.kk9software.reviewreminder.model.Category;

public class ChooseCategoryActivity extends AppCompatActivity {

    private DBHelper db;
    private CategoryAdapter mCategoryAdapter;
    public static final String EXTRA_CATEGORY_ID = "category_id";
    public static final String EXTRA_CATEGORY_NAME = "category_name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChooseCategoryActivity.this,AddCategoryActivity.class);
                startActivity(i);
            }
        });

        db = new DBHelper(this);
        mCategoryAdapter = new CategoryAdapter(db.getAllCategories(), new OnItemClickListener() {
            @Override
            public void onItemClick(Category category) {
                Intent i = new Intent(ChooseCategoryActivity.this,AddSubjectActivity.class);
                i.putExtra(EXTRA_CATEGORY_NAME,category.getName());
                i.putExtra(EXTRA_CATEGORY_ID,category.getId());
                startActivity(i);
            }
        });
        System.out.println(db.getAllCategories().size());
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mCategoryAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCategoryAdapter.swapData(db.getAllCategories());
    }

    public interface OnItemClickListener {
        void onItemClick(Category category);
    }

}
