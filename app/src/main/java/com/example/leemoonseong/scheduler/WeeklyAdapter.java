package com.example.leemoonseong.scheduler;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hosea on 2016-11-01.
 */
class WeekItem {
    String weekName;
    String scheduleName1;
    WeekItem(String sName1) {
        scheduleName1 = sName1;
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
        TextView dayName = (TextView) view.findViewById(R.id.weekDayName);
        String temp;
        switch (i%7) {
            case 0 : dayName.setText("월요일");
                break;
            case 1 : dayName.setText("화요일");
                break;
            case 2 : dayName.setText("수요일");
                break;
            case 3 : dayName.setText("목요일");
                break;
            case 4 : dayName.setText("금요일");
                break;
            case 5 : dayName.setText("토요일");
                dayName.setTextColor(Color.parseColor("#0000FF"));
                break;
            case 6 : dayName.setText("일요일");
                dayName.setTextColor(Color.parseColor("#FF0000"));
                break;

        }




        // Set Text 01
        TextView name = (TextView) view.findViewById(R.id.textItem1);
        name.setText(wItems.get(i).scheduleName1);



        return view;
    }
}
