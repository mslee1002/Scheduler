package com.example.leemoonseong.scheduler;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import static com.example.leemoonseong.scheduler.MonthlyFragment.Month;

/**
 * Created by Hosea on 2016-11-28.
 */

public class GridAdapter extends BaseAdapter {
    // 오늘에 날짜를 세팅 해준다.
    long now = System.currentTimeMillis();
    final Date date = new Date(now);
    private final List<String> list;
    private final LayoutInflater inflater;
    private Calendar mCal;
    int Year;
    final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
    int original_month =Integer.parseInt(curMonthFormat.format(date));
    /**
     * 생성자
     *
     * @param context
     * @param list
     */

    public GridAdapter(Context context, List<String> list , Calendar cal) {
        this.mCal = cal;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.calendar_item, parent, false);
            holder = new ViewHolder();
            holder.tvItemGridView = (TextView) convertView.findViewById(R.id.tv_item_gridview);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvItemGridView.setText("" + getItem(position));

        //해당 날짜 텍스트 컬러,배경 변경
        SimpleDateFormat this_month = new SimpleDateFormat("MM", Locale.KOREA);
        mCal = Calendar.getInstance();
        //오늘 day 가져
        Integer today = mCal.get(Calendar.DAY_OF_MONTH);
        String sToday = String.valueOf(today);
        if (sToday.equals(getItem(position)) && Month ==original_month ) { //오늘 day 텍스트 컬러 변경
            String strColor1 = "#FF0000";
            holder.tvItemGridView.setTextColor(Color.parseColor(strColor1));

        }
        else{
            String strColor2 = "#000000";
            holder.tvItemGridView.setTextColor(Color.parseColor(strColor2));
        }
        return convertView;
    }

    private class ViewHolder {

        TextView tvItemGridView;

    }

}