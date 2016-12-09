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
import com.example.leemoonseong.scheduler.dao.WeekItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Hosea on 2016-11-01.
 */


public class WeeklyAdapter extends BaseAdapter {
    private Context wContext;
    private int wResource;
    Calendar cal;
    String dayOfWeek;
    private ArrayList<ScheduleVO> wItems = new ArrayList<ScheduleVO>();
    final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
    final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
    final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
    private int Year, Month, Day, Time;

    public WeeklyAdapter(Context context, int resource, ArrayList<ScheduleVO> items) {
        cal = new GregorianCalendar(Locale.KOREA);
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
        ScheduleVO scheduleVO = wItems.get(i);
        cal.setTime(scheduleVO.getStartTime());
        if (view == null) {
            LayoutInflater inflater =
                    (LayoutInflater) wContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(wResource, viewGroup,false);
        }
        Year = Integer.parseInt(curYearFormat.format(scheduleVO.getStartTime()));
        Month = Integer.parseInt(curMonthFormat.format(scheduleVO.getStartTime()));
        Day = Integer.parseInt(curDayFormat.format(scheduleVO.getStartTime()));
        // Set WeekName
        TextView dayName = (TextView) view.findViewById(R.id.tv_week_dayName);
        TextView date = (TextView) view.findViewById(R.id.tv_week_date);
        TextView schedules= (TextView) view.findViewById(R.id.tv_week_title);
        TextView scheduleTime= (TextView) view.findViewById(R.id.tv_week_time);


        // Set Text 01

        date.setText(""+Day);
        dayName.setText(getDayKor());
        schedules.setText(scheduleVO.getTitle());
        DateFormat formatForDate = DateFormat.getDateInstance(DateFormat.LONG);
        scheduleTime.setText(formatForDate.format(scheduleVO.getStartTime()));

        return view;
    }
    public String getDayKor(){

        int cnt = cal.get(Calendar.DAY_OF_WEEK) - 1;
        String[] week = { "일", "월", "화", "수", "목", "금", "토" };
        dayOfWeek = week[cnt];
        return dayOfWeek;
    }

}
