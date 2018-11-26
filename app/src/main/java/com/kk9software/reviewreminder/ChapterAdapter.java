package com.kk9software.reviewreminder;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kk9software.reviewreminder.model.Chapter;

import java.util.ArrayList;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {

    private ArrayList<Chapter> mChapterList;
    private ChooseChapterActivity.OnItemClickListener mListener;
    public ChapterAdapter(ArrayList<Chapter> chapterList, ChooseChapterActivity.OnItemClickListener listener) {
        mChapterList = chapterList;
        mListener = listener;
    }
    @Override
    public ChapterAdapter.ChapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chapter, parent, false);
        return new ChapterAdapter.ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChapterAdapter.ChapterViewHolder holder, int position) {
        if(position>=mChapterList.size()) {
            return;
        }
        holder.bind(mChapterList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mChapterList==null)
            return 0;
        return mChapterList.size();
    }
    public void swapData(ArrayList<Chapter> newChapterList) {
        mChapterList = newChapterList;
        this.notifyDataSetChanged();
    }

    class ChapterViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView tvName;

        private ChapterViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.lich_name);
        }
        private void bind(final Chapter chapter) {
            tvName.setText(chapter.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(chapter);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    mListener.onItemLongClick(chapter);
                    return false;
                }
            });
        }
    }


}
