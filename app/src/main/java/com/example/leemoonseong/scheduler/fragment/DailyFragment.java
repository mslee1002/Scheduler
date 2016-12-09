package com.example.leemoonseong.scheduler.fragment;

import android.app.Fragment;
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
import com.example.leemoonseong.scheduler.Adapter.DailyAdapter;
import com.example.leemoonseong.scheduler.Adapter.WeeklyAdapter;
import com.example.leemoonseong.scheduler.Database.MyDBHelper;
import com.example.leemoonseong.scheduler.R;
import com.example.leemoonseong.scheduler.comparator;
import com.example.leemoonseong.scheduler.dao.ScheduleVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * Created by Hosea on 2016-11-28.
 */
public class DailyFragment extends Fragment {
    DailyAdapter dailyAdapter;

    public DailyFragment() {
        // Required empty public constructor
    }
    Calendar cal;
    MyDBHelper helper;
    int db_year, db_month, db_day;
    String dayOfWeek;
    private ArrayList<ScheduleVO> dayList;
    private int Year, Month, Day, Time;
    private Date date = new Date();
    private TextView tvDate;
    int dayNum;
    int real_day;

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("일별 보기");
        if (dailyAdapter != null) {
            try {
                load_dailySchedule();
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                dailyAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cal = new GregorianCalendar(Locale.KOREA);
        cal.setTime(date);
        final View view = inflater.inflate(R.layout.fragment_daily, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.lv_daily);
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
        tvDate = (TextView)view.findViewById(R.id.tv_week_date);
        tvDate = (TextView)view.findViewById(R.id.tv_day_date);
        Button btn_before = (Button)view.findViewById(R.id.day_before);
        Button btn_next = (Button)view.findViewById(R.id.day_next);
        Year = Integer.parseInt(curYearFormat.format(date));
        Month = Integer.parseInt(curMonthFormat.format(date));
        Day = Integer.parseInt(curDayFormat.format(date));
        tvDate.setText(Year +"년  " + Month +"월 " + Day +"일"+getDayKor());
        helper = new MyDBHelper(view.getContext());

        //이전 날 스케줄 갱신
        btn_before.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                cal.add(Calendar.DATE, -1); // 하루를 더한다.

                SimpleDateFormat fm = new SimpleDateFormat(
                        "yyyy-MM-dd");
                String strDate = fm.format(cal.getTime());

                Year = cal.get(Calendar.YEAR);
                Month = cal.get(Calendar.MONTH)+1;
                Day= cal.get(Calendar.DATE);

                tvDate.setText(Year +"년  " + Month +"월 " + Day +"일"+getDayKor());
                try {
                    load_dailySchedule();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(),"beforeClicked", Toast.LENGTH_SHORT).show();
            }
        });

        //다음 날 스케줄 갱신

        btn_next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                cal.add(Calendar.DATE, 1); // 하루를 더한다.
                SimpleDateFormat fm = new SimpleDateFormat(
                        "yyyy-MM-dd");
                String strDate = fm.format(cal.getTime());

                Year = cal.get(Calendar.YEAR);
                Month = cal.get(Calendar.MONTH)+1;
                Day = cal.get(Calendar.DATE);
                tvDate.setText(Year +"년  " + Month +"월 " + Day +"일"+getDayKor());
                try {
                    load_dailySchedule();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(),"beforeClicked", Toast.LENGTH_SHORT).show();
            }
        });

        // 오늘에 날짜를 세팅 해준다.
        long now = System.currentTimeMillis();
        final Date date = new Date(now);




        //연,월,일을 따로 저장

        //현재 날짜 텍스트뷰에 뿌려줌

        Month =Integer.parseInt(curMonthFormat.format(date));
        dayList = new ArrayList<ScheduleVO>();
        dailyAdapter = new DailyAdapter(view.getContext(),R.layout.dailyitem, dayList);
        listView.setAdapter(dailyAdapter); // uses the view to get the context instead of getActivity().

        try {
            load_dailySchedule();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                real_day = position -5 -dayNum;
                Intent intent = new Intent(getActivity(), ScheduleDetailActivity.class);
                intent.putExtra("scheduleId", dayList.get(position).getScheduleId());
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public String getDayKor(){

        int cnt = cal.get(Calendar.DAY_OF_WEEK) - 1;
        String[] week = { "일", "월", "화", "수", "목", "금", "토" };
        dayOfWeek = week[cnt];
        return "( "+dayOfWeek+" )";
    }
    public void load_dailySchedule() throws ParseException {
        dayList.clear();
        comparator comp = new comparator();
        String sql = "Select * FROM schedule ";
        Cursor cursor = helper.getReadableDatabase().rawQuery(sql,null);
        StringBuffer buffer = new StringBuffer();
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        while (cursor.moveToNext()) {
//            1. title, 2. startTime, 3 .endTime ,4. location, 5. memo,6. image
//            Calendar t = new GregorianCalendar();
            SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
            db_year = Integer.parseInt(curYearFormat.format(dateFormat.parse(cursor.getString(2))));
            db_month = Integer.parseInt(curMonthFormat.format(dateFormat.parse(cursor.getString(2))));
            db_day = Integer.parseInt(curDayFormat.format(dateFormat.parse(cursor.getString(2))));
            String check = db_year+"-"+db_month+"-"+db_day;
            if(check.equals(Year+"-"+Month+"-"+Day)) {

//            Date s_time = dateFormat.parse(cursor.getString(2)); //replace 4 with the column index
//            Date e_time = dateFormat.parse(cursor.getString(3)); //replace 4 with the column index
                dayList.add(new ScheduleVO(cursor.getInt(0), cursor.getString(1),
                        dateFormat.parse(cursor.getString(2)), dateFormat.parse(cursor.getString(3)),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6)));
                Collections.sort(dayList,comp);
                dailyAdapter.notifyDataSetChanged();

            }
            else{

                dailyAdapter.notifyDataSetChanged();
            }
        }
    }
}

