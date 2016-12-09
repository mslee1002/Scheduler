package com.example.leemoonseong.scheduler.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leemoonseong.scheduler.Activity.MainActivity;
import com.example.leemoonseong.scheduler.Activity.ScheduleDetailActivity;
import com.example.leemoonseong.scheduler.Adapter.WeeklyAdapter;
import com.example.leemoonseong.scheduler.Database.MyDBHelper;
import com.example.leemoonseong.scheduler.R;
import com.example.leemoonseong.scheduler.comparator;
import com.example.leemoonseong.scheduler.dao.ScheduleVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Hosea on 2016-11-28.
 */
public class WeeklyFragment extends Fragment {
    private ArrayList<ScheduleVO> dayList;
    private int Year, Month, Day, Time;
    private Date date = new Date();
    private TextView tvDate;
    private String sDate, eDate;
    Date date1 ,date2;
    int db_year, db_month, db_day;
    MyDBHelper helper;
    WeeklyAdapter weeklyAdapter;
    Calendar calendar = new GregorianCalendar();
    Calendar cal;
    Calendar sWeek;
    Calendar eWeek;
    int dayNum;
    int real_day;
    private int original_month;

    public WeeklyFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("주별 보기");
        if (weeklyAdapter != null) {
            try {
                load_dailySchedule();
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                weeklyAdapter.notifyDataSetChanged();
            }
        }
        weeklyAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_weekly, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.lv_weekly);
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
        helper = new MyDBHelper(view.getContext());
        tvDate = (TextView)view.findViewById(R.id.tv_week_date);
        Button btn_before = (Button)view.findViewById(R.id.week_before);
        Button btn_next = (Button)view.findViewById(R.id.week_next);
        cal = new GregorianCalendar(Locale.KOREA);
        cal.setTime(new Date());
        sWeek = new GregorianCalendar(Locale.KOREA);
        eWeek = new GregorianCalendar(Locale.KOREA);
        sWeek.setTime(cal.getTime());
        sWeek.add(Calendar.DAY_OF_YEAR, - (cal.get(Calendar.DAY_OF_WEEK)) + 1);
        eWeek.setTime(cal.getTime());
        eWeek.add(Calendar.DAY_OF_YEAR, (7- (cal.get(Calendar.DAY_OF_WEEK)) + 1));

        dayList = new ArrayList<ScheduleVO>();

        int month = (cal.get(Calendar.MONTH))+1;

        int weekStart = ((cal.get(Calendar.DAY_OF_MONTH)+1)
                - (cal.get(Calendar.DAY_OF_WEEK)));
        int weekEnd = weekStart + 6;

        SimpleDateFormat fm = new SimpleDateFormat(
                "yyyy-MM-dd");
        String strDate = fm.format(cal.getTime());

        sDate = fm.format(sWeek.getTime());
        eDate = fm.format(eWeek.getTime());
        try {
            date1 = fm.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            date2 = fm.parse(eDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        tvDate.setText(strDate);

        tvDate.setText(month + "월 " + cal.get(Calendar.WEEK_OF_MONTH)+"째 주");
//        tvDate.setText(weekStart);


        //이전 주 스케줄 갱신
        btn_before.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                cal.add(Calendar.YEAR, 0); // 1년을 더한다.
                cal.add(Calendar.MONTH, 0); // 한달을 더한다.
                cal.add(Calendar.DAY_OF_YEAR, -7); // 하루를 더한다.
                cal.add(Calendar.HOUR, 0); // 시간을 더한다

                SimpleDateFormat fm = new SimpleDateFormat(
                        "yyyy-MM-dd");
//                String strDate = fm.format(cal.getTime());
//                tvDate.setText(strDate);
                int month = (cal.get(Calendar.MONTH))+1;
                tvDate.setText(month + "월 " + cal.get(Calendar.WEEK_OF_MONTH)+"째 주");
                sWeek.setTime(cal.getTime());
                sWeek.add(Calendar.DAY_OF_YEAR, - (cal.get(Calendar.DAY_OF_WEEK)) + 1);


                eWeek.setTime(cal.getTime());
                eWeek.add(Calendar.DAY_OF_YEAR, (7- (cal.get(Calendar.DAY_OF_WEEK)) + 1));


                sDate = fm.format(sWeek.getTime());
                eDate = fm.format(eWeek.getTime());
                try {
                    date1 = fm.parse(sDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    date2 = fm.parse(eDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    load_dailySchedule();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getActivity(),sDate + " 에서 " + eDate, Toast.LENGTH_SHORT).show();

            }
        });

        //다음 주 스케줄 갱신

        btn_next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                cal.add(Calendar.YEAR, 0); // 1년을 더한다.
                cal.add(Calendar.MONTH, 0); // 한달을 더한다.
                cal.add(Calendar.DAY_OF_YEAR, 7); // 하루를 더한다.
                cal.add(Calendar.HOUR, 0); // 시간을 더한다

                SimpleDateFormat fm = new SimpleDateFormat(
                        "yyyy-MM-dd");
//                String strDate = fm.format(cal.getTime());
//                tvDate.setText(strDate);

                int month = (cal.get(Calendar.MONTH))+1;
                tvDate.setText(month + "월 " + cal.get(Calendar.WEEK_OF_MONTH)+"째 주");

                sWeek.setTime(cal.getTime());
                sWeek.add(Calendar.DAY_OF_YEAR, - (cal.get(Calendar.DAY_OF_WEEK)) + 1);

                eWeek.setTime(cal.getTime());
                eWeek.add(Calendar.DAY_OF_YEAR, (7- (cal.get(Calendar.DAY_OF_WEEK)) + 1));


                sDate = fm.format(sWeek.getTime());
                eDate = fm.format(eWeek.getTime());
                try {
                    date1 = fm.parse(sDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    date2 = fm.parse(eDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getActivity(),sDate + " 에서 " + eDate, Toast.LENGTH_SHORT).show();
                try {
                    load_dailySchedule();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        // 오늘에 날짜를 세팅 해준다.
        long now = System.currentTimeMillis();
        final Date date = new Date(now);


        //현재 날짜 텍스트뷰에 뿌려줌
        original_month =Integer.parseInt(curMonthFormat.format(date));

        Month =Integer.parseInt(curMonthFormat.format(date));

        weeklyAdapter = new WeeklyAdapter(view.getContext(),R.layout.weeklyitem, dayList);
        listView.setAdapter(weeklyAdapter); // uses the view to get the context instead of getActivity().

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                real_day = position -5 -dayNum;
                Toast.makeText(view.getContext(),Month+"/"+real_day,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ScheduleDetailActivity.class);
                intent.putExtra("scheduleId", dayList.get(position).getScheduleId());
                startActivity(intent);
            }
        });
        try {
            load_dailySchedule();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void load_dailySchedule() throws ParseException {
        comparator comp = new comparator();
        dayList.clear();
        SimpleDateFormat sfm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String sql = "Select * FROM schedule;";
        Cursor cursor = helper.getReadableDatabase().rawQuery(sql,null);
        while (cursor.moveToNext()) {
//            1. title, 2. startTime, 3 .endTime ,4. location, 5. memo,6. image
//            Calendar t = new GregorianCalendar();
            SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
            if ((dateFormat.parse(cursor.getString(2))).after(date1) && date2.after(dateFormat.parse(cursor.getString(2))) )
            {

//            Date s_time = dateFormat.parse(cursor.getString(2)); //replace 4 with the column index
//            Date e_time = dateFormat.parse(cursor.getString(3)); //replace 4 with the column index
                dayList.add(new ScheduleVO(cursor.getInt(0), cursor.getString(1),
                        dateFormat.parse(cursor.getString(2)), dateFormat.parse(cursor.getString(3)),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6)));
                Collections.sort(dayList,comp);
                weeklyAdapter.notifyDataSetChanged();
            }
            else{
                weeklyAdapter.notifyDataSetChanged();
            }
        }
    }
}