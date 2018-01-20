package com.kk9software.reviewreminder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.kk9software.reviewreminder.data.DBHelper;
import com.kk9software.reviewreminder.model.Subject;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private DBHelper db;
    private ReviewAdapter reviewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        db = new DBHelper(this);
        reviewAdapter = new ReviewAdapter(db.getAllSubjects());
        RecyclerView ReviewList = (RecyclerView) findViewById(R.id.main_recycler);
        ReviewList.setHasFixedSize(true);
        ReviewList.setLayoutManager(new LinearLayoutManager(this));
        ReviewList.setAdapter(reviewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void addSubject(View v) {
        EditText et_subjectName = (EditText) findViewById(R.id.main_subject_name);
        Calendar cal = Calendar.getInstance();
        Subject subjectToAdd = new Subject(et_subjectName.getText().toString(),cal.getTimeInMillis());
        db.addSubject(subjectToAdd);
        reviewAdapter.swapCursor(db.getAllSubjects());

    }
}
