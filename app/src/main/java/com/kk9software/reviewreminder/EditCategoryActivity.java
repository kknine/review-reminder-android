package com.kk9software.reviewreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kk9software.reviewreminder.data.DBHelper;
import com.kk9software.reviewreminder.model.Category;

public class EditCategoryActivity extends AppCompatActivity {

    private int categoryId;
    private String categoryName;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        final Intent intent = getIntent();
        categoryId = intent.getIntExtra(ChooseCategoryActivity.EXTRA_CATEGORY_ID,-1);
        categoryName = intent.getStringExtra(ChooseCategoryActivity.EXTRA_CATEGORY_NAME);
        if(categoryId==-1) {
            Toast.makeText(this,"Oops something went wrong, sorry for the inconvenience",Toast.LENGTH_LONG).show();
            finish();
        }
        db = new DBHelper(this);
        EditText categoryNameET = (EditText)findViewById(R.id.eca_category);
        categoryNameET.setText(categoryName);
    }

    public void updateCategory(View v) {
        EditText categoryNameET = (EditText)findViewById(R.id.eca_category);
        String newCategoryName = categoryNameET.getText().toString();
        if(!newCategoryName.equals(categoryName)) {
            Category newCategory = db.getCategory(categoryId);
            newCategory.setName(newCategoryName);
            if(db.updateCategory(newCategory)) {
                Toast.makeText(this, "Successfully updated category", Toast.LENGTH_SHORT).show();
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
