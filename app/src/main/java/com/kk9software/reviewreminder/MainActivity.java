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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.kk9software.reviewreminder.data.DBHelper;
import com.kk9software.reviewreminder.model.Reminder;
import com.kk9software.reviewreminder.model.Subject;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private DBHelper db;
    private ReminderAdapter reminderAdapter;
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
                Intent intent = new Intent(MainActivity.this,ChooseCategoryActivity.class);
                startActivity(intent);
            }
        });

        // Fill the list with reminders
        db = new DBHelper(this);
        reminderAdapter = new ReminderAdapter(new ArrayList<Reminder>(),this);
        RecyclerView ReviewList = (RecyclerView) findViewById(R.id.main_recycler);
        ReviewList.setHasFixedSize(true);
        ReviewList.setLayoutManager(new LinearLayoutManager(this));
        ReviewList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ReviewList.setAdapter(reminderAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int id = (int) viewHolder.itemView.getTag();
                db.completeReview(id);
                reminderAdapter.swapData(db.getAllUnCompletedReminders());
                Toast.makeText(MainActivity.this,"Nice Job!", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(ReviewList);
    }
    protected void onStart() {
        super.onStart();
        reminderAdapter.swapData(db.getAllUnCompletedReminders());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
