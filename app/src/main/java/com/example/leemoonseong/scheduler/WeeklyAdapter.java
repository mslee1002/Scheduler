package com.example.leemoonseong.scheduler;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Hosea on 2016-11-01.
 */
class WeekItem {
    private String dayName;
    private String date;
    private String schedules;

    WeekItem(String inputName, String inputDate, String inputSchedules) {
        dayName = inputName;
        date = inputDate;
        schedules = inputSchedules;
    }
}

public class WeeklyAdapter extends BaseAdapter {
    private Context wContext;
    private int wResource;
    private ArrayList<WeekItem> wItems = new ArrayList<WeekItem>();

    public WeeklyAdapter(Context context, int resource, ArrayList<WeekItem> items) {
        wContext = context;
        wResource = resource;
        wItems = items;
    }

    @Override
    public int getCount() {
        return wItems.size();
    }

    @Override
    public Object getItem(int i) {
        return wItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater =
                    (LayoutInflater) wContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(wResource, viewGroup,false);
        }

        // Set WeekName
        TextView dayName = (TextView) view.findViewById(R.id.tv_week_dayName);
        TextView date = (TextView) view.findViewById(R.id.tv_week_date);
        TextView schedules= (TextView) view.findViewById(R.id.tv_week_schedule);
        TextView scheduleTime= (TextView) view.findViewById(R.id.tv_week_time);


        switch (i%7) {
            case 0 : dayName.setText("월");
                break;
            case 1 : dayName.setText("화");
                break;
            case 2 : dayName.setText("수");
                break;
            case 3 : dayName.setText("목");
                break;
            case 4 : dayName.setText("금");
                break;
            case 5 : dayName.setText("토");
                dayName.setTextColor(Color.parseColor("#0000FF"));
                break;
            case 6 : dayName.setText("일");
                dayName.setTextColor(Color.parseColor("#FF0000"));
                break;
        }

        // Set Text 01
        date.setText(i + 5 + "");
        schedules.setText("할일들");
        scheduleTime.setText(new Date().toString());

        return view;
    }

}
