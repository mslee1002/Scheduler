package com.example.leemoonseong.scheduler.fragment;

import android.app.Fragment;
import android.content.Intent;
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
import com.example.leemoonseong.scheduler.R;
import com.example.leemoonseong.scheduler.dao.ScheduleVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Hosea on 2016-11-28.
 */
public class DailyFragment extends Fragment {

    public DailyFragment() {
        // Required empty public constructor
    }
    private ArrayList<ScheduleVO> dayList;
    private int Year, Month, Day, Time;
    private Date date = new Date();
    private TextView tvDate;
    int dayNum;
    int real_day;
    private int original_month;

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("일별 보기");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        tvDate.setText(Year +"년  " + Month +"월 " + Day +"일");


        //이전 날 스케줄 갱신
        btn_before.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getActivity(),"beforeClicked", Toast.LENGTH_SHORT).show();
            }
        });

        //다음 날 스케줄 갱신

        btn_next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getActivity(),"nextClicked", Toast.LENGTH_SHORT).show();
            }
        });

        // 오늘에 날짜를 세팅 해준다.
        long now = System.currentTimeMillis();
        final Date date = new Date(now);

        dayList = new ArrayList<ScheduleVO>();
        dayList.add(new ScheduleVO("밥약속",new Date(), new Date(), "신촌", "메모는 이곳에", null, null, null));
        dayList.add(new ScheduleVO("신촌",new Date(), new Date(), "신촌", "메모는 이곳에", null, null, null));
        dayList.add(new ScheduleVO("술약속",new Date(), new Date(), "김포", "메모는 이곳에", null, null, null));
        dayList.add(new ScheduleVO("저녁 약속이 있던듯",new Date(), new Date(), "신촌", "메모는 이곳에", null, null, null));
        dayList.add(new ScheduleVO("뭐 하기로 했더라?",new Date(), new Date(), "학교", "메모는 이곳에", null, null, null));
        dayList.add(new ScheduleVO("밥약속",new Date(), new Date(), "집", "메모는 이곳에", null, null, null));
        dayList.add(new ScheduleVO("뭐 하는날",new Date(), new Date(), "신촌", "메모는 이곳에", null, null, null));


        //현재 날짜 텍스트뷰에 뿌려줌
        original_month =Integer.parseInt(curMonthFormat.format(date));

        Month =Integer.parseInt(curMonthFormat.format(date));

        DailyAdapter dailyAdapter = new DailyAdapter(view.getContext(),R.layout.dailyitem, dayList);
        listView.setAdapter(dailyAdapter); // uses the view to get the context instead of getActivity().

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

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}