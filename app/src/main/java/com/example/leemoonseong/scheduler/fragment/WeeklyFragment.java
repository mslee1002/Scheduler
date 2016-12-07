package com.example.leemoonseong.scheduler.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.leemoonseong.scheduler.Activity.MainActivity;
import com.example.leemoonseong.scheduler.Activity.ScheduleDetailActivity;
import com.example.leemoonseong.scheduler.Adapter.WeeklyAdapter;
import com.example.leemoonseong.scheduler.R;
import com.example.leemoonseong.scheduler.dao.ScheduleVO;
import com.example.leemoonseong.scheduler.dao.WeekItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Hosea on 2016-11-28.
 */
public class WeeklyFragment extends Fragment {
    private ArrayList<ScheduleVO> dayList;
    static int Month;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_weekly, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.lv_weekly);

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

        //연,월,일을 따로 저장
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);

        //현재 날짜 텍스트뷰에 뿌려줌
        original_month =Integer.parseInt(curMonthFormat.format(date));

        Month =Integer.parseInt(curMonthFormat.format(date));

        WeeklyAdapter weeklyAdapter = new WeeklyAdapter(view.getContext(),R.layout.weeklyitem, dayList);
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

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}