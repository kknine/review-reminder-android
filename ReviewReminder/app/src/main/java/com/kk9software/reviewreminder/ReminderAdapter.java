package com.kk9software.reviewreminder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;

import com.kk9software.reviewreminder.model.Reminder;

import java.util.ArrayList;
import java.util.Calendar;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

//    public static final int MINUTES = 0;
//    public static final int HOURS = 1;
//    public static final int DAYS = 2;
//    public static final int WEEKS = 3;
//    public static final int MONTHS = 4;
    private ArrayList<Reminder> mReminderList;
    private Context context;
    public ReminderAdapter(ArrayList<Reminder> reminderList, Context context) {
        this.context = context;
        this.mReminderList = reminderList;
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

    public void swapCursor(ArrayList<Reminder> newReminderList) {
        mReminderList = newReminderList;
        this.notifyDataSetChanged();
    }

    class ReminderViewHolder extends RecyclerView.ViewHolder {
        TextView reminderName_tv;
        TextView reminderTime_tv;
        LinearLayout background_ll;

        public ReminderViewHolder(View itemView) {
            super(itemView);
            reminderName_tv = (TextView) itemView.findViewById(R.id.lir_text);
            reminderTime_tv = (TextView) itemView.findViewById(R.id.lir_time);
            background_ll = (LinearLayout) itemView.findViewById(R.id.lir_background);
        }

        public void bind(Reminder reminderToDisplay) {
            reminderName_tv.setText(reminderToDisplay.getSubjectName());
            reminderTime_tv.setText(howFar(reminderToDisplay.getReminderTime()));
            int color = determineColor(reminderToDisplay);
            if(color!=0) {
                background_ll.setBackgroundColor(ContextCompat.getColor(context,color));
            }

        }

//        private int howMany(int what, long time) {
//            Calendar cal = Calendar.getInstance();
//            Calendar cal_review = Calendar.getInstance();
//            cal_review.setTimeInMillis(time);
//        }

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
            switch(interval) {
                case 0:
                    if(Math.abs(difference)<=TimeUnit.MINUTES.toMillis(58))
                        return green;
                    else if(difference<-TimeUnit.MINUTES.toMillis(15))
                        return red;
                    else
                        return 0;
                case 1:
                    if(Math.abs(difference)<=TimeUnit.HOURS.toMillis(6))
                        return green;
                    else if(difference<-TimeUnit.HOURS.toMillis(6))
                        return red;
                    else
                        return 0;
                default:
                    return 0;
            }
        }
        // TODO: Generally it looks horrible, it should be changed
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

