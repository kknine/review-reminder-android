package com.kk9software.reviewreminder;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;

import com.kk9software.reviewreminder.data.DBHelper;
import com.kk9software.reviewreminder.model.Category;
import com.kk9software.reviewreminder.model.Chapter;
import com.kk9software.reviewreminder.model.Reminder;
import com.kk9software.reviewreminder.model.Subject;

import java.util.ArrayList;
import java.util.Calendar;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

    private ArrayList<Reminder> mReminderList;
    private Context mContext;
    private DBHelper db;
    public ReminderAdapter(ArrayList<Reminder> reminderList, Context context) {
        this.mContext = context;
        this.mReminderList = reminderList;
        db = new DBHelper(mContext);
    }

    @Override
    public ReminderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_review, parent, false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReminderViewHolder holder, int position) {
        if(mReminderList.size()<=position)
            return;
        holder.bind(mReminderList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mReminderList==null)
            return 0;
        else
            return mReminderList.size();
    }

    public void swapData(ArrayList<Reminder> newReminderList) {
        mReminderList = newReminderList;
        this.notifyDataSetChanged();
    }

    class ReminderViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;
        TextView tvSubjectName;
        TextView tvReminderTime;
        TextView tvChapterName;
        LinearLayout llBackground;

        public ReminderViewHolder(View itemView) {
            super(itemView);
            tvCategoryName = (TextView) itemView.findViewById(R.id.lir_category);
            tvSubjectName = (TextView) itemView.findViewById(R.id.lir_subject);
            tvReminderTime = (TextView) itemView.findViewById(R.id.lir_time);
            tvChapterName = (TextView) itemView.findViewById(R.id.lir_chapter);
            llBackground = (LinearLayout) itemView.findViewById(R.id.lir_background);
        }

        public void bind(Reminder reminderToDisplay) {
            Subject subject = db.getSubject(reminderToDisplay.getSubjectId());
            Chapter chapter = db.getChapter(subject.getChapterId());
            Category category = db.getCategory(chapter.getCategoryId());
            tvSubjectName.setText(subject.getName());
            tvChapterName.setText(chapter.getName());
            tvCategoryName.setText(category.getName());
            tvReminderTime.setText(howFar(reminderToDisplay.getReminderTime()));
            this.itemView.setTag(reminderToDisplay.getId());
            int color = determineColor(reminderToDisplay);
            llBackground.setBackgroundColor(ContextCompat.getColor(mContext,color));
        }


        // Determines color
        // 0 - no color
        // 1 - green color
        // 2 - red color
        private int determineColor(Reminder reminder) {
            Calendar cal = Calendar.getInstance();
            int interval = reminder.getTimeInterval();
            long difference = reminder.getReminderTime() - cal.getTimeInMillis();
            int green = R.color.green;
            int red = R.color.red;
            int white = R.color.white;
            switch(interval) {
                case 0:
                    if(Math.abs(difference)<=TimeUnit.MINUTES.toMillis(15))
                        return green;
                    else if(difference<-TimeUnit.MINUTES.toMillis(15))
                        return red;
                    else
                        return white;
                case 1:
                    if(Math.abs(difference)<=TimeUnit.HOURS.toMillis(6))
                        return green;
                    else if(difference<-TimeUnit.HOURS.toMillis(6))
                        return red;
                    else
                        return white;
                default:
                    return white;
            }

        }
        // TODO: Generally it should be changed
        private String howFar(long dataTime) {
            Calendar now = Calendar.getInstance();
            long nowTime = now.getTimeInMillis();
            long diff = dataTime - nowTime;
            String result;
            if (diff < 0)
                result = "You should have revised ";
            else
                result = "You should revise in ";
            long absDiff = Math.abs(diff);
            long sec = absDiff / TimeUnit.SECONDS.toMillis(1);
            if (sec > 0) {
                long min = absDiff / TimeUnit.MINUTES.toMillis(1);
                if (min > 0) {
                    long hour = absDiff / TimeUnit.HOURS.toMillis(1);
                    if (hour > 0) {
                        long day = absDiff / TimeUnit.DAYS.toMillis(1);
                        if (day > 0) {
                            long week = absDiff / TimeUnit.DAYS.toMillis(7);
                            if (week > 0) {
                                // TODO: Change it to more accurate months
                                long month = absDiff / TimeUnit.DAYS.toMillis(30);
                                if (month > 0) {
                                    result += Long.toString(month) + " months";
                                    if (diff < 0) result += " ago";
                                    return result;
                                } else {
                                    result += Long.toString(week) + " weeks";
                                    if (diff < 0) result += " ago";
                                    return result;
                                }
                            } else {
                                result += Long.toString(day) + " days";
                                if (diff < 0) result += " ago";
                                return result;
                            }
                        } else {
                            result += Long.toString(hour) + " hours";
                            if (diff < 0) result += " ago";
                            return result;
                        }
                    } else {
                        result += Long.toString(min) + " minutes";
                        if (diff < 0) result += " ago";
                        return result;
                    }
                } else {
                    result += Long.toString(sec) + " seconds";
                    if (diff < 0) result += " ago";
                    return result;
                }
            } else
                return "JUST NOW";
        }
    }

}

