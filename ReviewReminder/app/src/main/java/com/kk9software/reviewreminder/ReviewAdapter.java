package com.kk9software.reviewreminder;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kk9software.reviewreminder.data.ReviewContract;
import com.kk9software.reviewreminder.model.Subject;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ArrayList<Subject> mSubjectList;
    public ReviewAdapter(ArrayList<Subject> subjectList) {
        this.mSubjectList = subjectList;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        if(mSubjectList.size()<=position)
            return;
        holder.bind(mSubjectList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSubjectList.size();
    }
    public void swapCursor(ArrayList<Subject> newSubjectList) {
        mSubjectList = newSubjectList;
        this.notifyDataSetChanged();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView reviewName;
        public ReviewViewHolder(View itemView) {
            super(itemView);
            reviewName = (TextView) itemView.findViewById(R.id.lir_text);
        }
        public void bind(Subject subjectToDisplay) {
            reviewName.setText(subjectToDisplay.getName());
        }
    }
}
