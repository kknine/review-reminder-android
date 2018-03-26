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
    private ChooseCategoryActivity.OnItemClickListener mListener;
    public CategoryAdapter(ArrayList<Category> categoryList, ChooseCategoryActivity.OnItemClickListener listener) {
        mCategoryList = categoryList;
        mListener = listener;
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
        if(mCategoryList==null)
            return 0;
        return mCategoryList.size();
    }
    public void swapData(ArrayList<Category> newCategoryList) {
        mCategoryList = newCategoryList;
        this.notifyDataSetChanged();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView tvName;

        private CategoryViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.lic_name);
        }
        private void bind(final Category category) {
            tvName.setText(category.getName());
            itemView.setTag(category.getId());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(category);
                }
            });
        }
    }
}
