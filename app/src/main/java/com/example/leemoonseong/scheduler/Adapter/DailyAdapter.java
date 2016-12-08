package com.example.leemoonseong.scheduler.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leemoonseong.scheduler.R;
import com.example.leemoonseong.scheduler.dao.ScheduleVO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Hosea on 2016-11-01.
 */


public class DailyAdapter extends BaseAdapter {
    private Context dContext;
    private int dResource;
    private ArrayList<ScheduleVO> dItems = new ArrayList<ScheduleVO>();

    public DailyAdapter(Context context, int resource, ArrayList<ScheduleVO> items) {
        dContext = context;
        dResource = resource;
        dItems = items;
    }

    @Override
    public int getCount() {
        return dItems.size();
    }

    @Override
    public Object getItem(int i) {
        return dItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater =
                    (LayoutInflater) dContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(dResource, viewGroup,false);
        }

        // Set DayName
        TextView startTime = (TextView) view.findViewById(R.id.tv_day_startTime);
        TextView endTime = (TextView) view.findViewById(R.id.tv_day_endTime);
        TextView title = (TextView) view.findViewById(R.id.tv_day_title);
        TextView location= (TextView) view.findViewById(R.id.tv_day_location);
//        TextView scheduleTime= (TextView) view.findViewById(R.id.tv_week_time);


        // Set Text 01
        ScheduleVO scheduleVO = dItems.get(i);
        SimpleDateFormat formatForTime = new SimpleDateFormat("HH:mm");
        startTime.setText(formatForTime.format(scheduleVO.getStartTime()));
        endTime.setText(formatForTime.format(scheduleVO.getEndTime()));
        title.setText(scheduleVO.getTitle());
        location.setText(scheduleVO.getLocation());

        return view;
    }

}
