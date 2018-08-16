package com.kk9software.reviewreminder;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kk9software.reviewreminder.model.Subject;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private ArrayList<Subject> mSubjectList;
    private ChooseSubjectActivity.OnItemClickListener mListener;
    public SubjectAdapter(ArrayList<Subject> subjectList, ChooseSubjectActivity.OnItemClickListener listener) {
        this.mSubjectList = subjectList;
        this.mListener = listener;
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_subject, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubjectViewHolder holder, int position) {
        if(mSubjectList.size()<=position)
            return;
        holder.bind(mSubjectList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSubjectList.size();
    }
    public void swapData(ArrayList<Subject> newSubjectList) {
        mSubjectList = newSubjectList;
        this.notifyDataSetChanged();
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        View itemView;
        public SubjectViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.lis_name);
        }
        public void bind(final Subject subject) {
            tvName.setText(subject.getName());
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mListener.onItemLongClick(subject);
                    return false;
                }
            });

        }
    }
}
