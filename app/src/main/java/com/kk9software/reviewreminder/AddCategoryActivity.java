package com.kk9software.reviewreminder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.kk9software.reviewreminder.data.DBHelper;
import com.kk9software.reviewreminder.model.Category;

public class AddCategoryActivity extends AppCompatActivity {

    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        db = new DBHelper(this);
    }

    public void addCategory(View v) {
        EditText etCategoryName = (EditText)findViewById(R.id.adc_category);
        db.addCategory(new Category(etCategoryName.getText().toString()));
        finish();
    }
}
