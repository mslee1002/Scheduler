package com.example.leemoonseong.scheduler;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Hosea on 2016-11-28.
 */
public class WeeklyFragment extends Fragment {
    private ArrayList<WeekItem> dayList;
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

        dayList = new ArrayList<WeekItem>();
        dayList.add(new WeekItem("월","1","ㅁ"));
        dayList.add(new WeekItem("화","2","ㅁ"));
        dayList.add(new WeekItem("수","3","민"));
        dayList.add(new WeekItem("목","4","경훈이"));
        dayList.add(new WeekItem("금","5","타조알"));
        dayList.add(new WeekItem("토","6","그릴"));
        dayList.add(new WeekItem("일","7","맛있다"));

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
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}