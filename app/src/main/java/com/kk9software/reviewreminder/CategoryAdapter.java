package com.kk9software.reviewreminder;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kk9software.reviewreminder.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {


    private ArrayList<Category> mCategoryList;
    public CategoryAdapter(ArrayList<Category> categoryList) {
        mCategoryList = categoryList;
    }
    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        if(position>=mCategoryList.size()) {
            return;
        }
        holder.bind(mCategoryList.get(position));
    }

    @Override
    public int getItemCount() {

        return 0;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView tvName;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.lic_name);
        }
        public void bind(Category category) {
            tvName.setText(category.getName());
            itemView.setTag(category.getId());
        }
    }
}
