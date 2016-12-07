package com.example.leemoonseong.scheduler.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leemoonseong.scheduler.Adapter.GridAdapter;
import com.example.leemoonseong.scheduler.Activity.MainActivity;
import com.example.leemoonseong.scheduler.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Hosea on 2016-11-28.
 */
public class MonthlyFragment extends Fragment {

    private ArrayList<String> dayList;
    //동적으로 변화하는 달력을 위해 현재 연,월을 저장힉 위한 변수

    static Calendar mCal;
    private int Year;
    static int Month;
    private TextView tvDate;
    GridAdapter gridAdapter;
    int dayNum;
    int real_day;
    //오늘 날짜를 가지는 변수 (동적으로 변하는 변수가 아님)
    private int original_month;

    public MonthlyFragment(){}

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("월별 보기");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_monthly, container, false);
        tvDate = (TextView)view.findViewById(R.id.tv_date);
        final GridView gridView = (GridView)view.findViewById(R.id.gridview);


        // 오늘에 날짜를 세팅 해준다.

        long now = System.currentTimeMillis();
        final Date date = new Date(now);

        //연,월,일을 따로 저장

        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        //현재 날짜 텍스트뷰에 뿌려줌
        Year = Integer.parseInt(curYearFormat.format(date));
        original_month =Integer.parseInt(curMonthFormat.format(date));

        Button btn_before = (Button)view.findViewById(R.id.before);
        Button btn_next = (Button)view.findViewById(R.id.next);
        Month =Integer.parseInt(curMonthFormat.format(date));
        tvDate.setText(Year +"년  " + Month +"월");


        //이전 달 달력 갱신
        btn_before.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Month = Month -1;
                dayList.clear();
                dayList.add("일");
                dayList.add("월");
                dayList.add("화");
                dayList.add("수");
                dayList.add("목");
                dayList.add("금");
                dayList.add("토");
                mCal = Calendar.getInstance();
                if(Month ==0){
                    Year = Year -1;
                    Month = 12;
                }
                tvDate.setText(Year +"년  " + Month +"월");
                mCal.set(Year, Month - 1, 1);
                dayNum = mCal.get(Calendar.DAY_OF_WEEK);
                //1일 - 요일 매칭 시키기 위해 공백 add
                for (int i = 1; i < dayNum; i++) {
                    dayList.add("");
                }
                setCalendarDate(mCal.get(Calendar.MONTH) + 1);
                gridAdapter.notifyDataSetChanged();

            }
        });

        //다음 달 달력 갱신

        btn_next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Month = Month +1;

                dayList.clear();
                dayList.add("일");
                dayList.add("월");
                dayList.add("화");
                dayList.add("수");
                dayList.add("목");
                dayList.add("금");
                dayList.add("토");
                mCal = Calendar.getInstance();

                if(Month ==13)
                {
                    Year = Year + 1;
                    Month = 1;
                }
                tvDate.setText(Year +"년  " + Month +"월");
                mCal.set(Year, Month - 1, 1);
                dayNum = mCal.get(Calendar.DAY_OF_WEEK);
                //1일 - 요일 매칭 시키기 위해 공백 add
                for (int i = 1; i < dayNum; i++) {
                    dayList.add("");
                }
                setCalendarDate(mCal.get(Calendar.MONTH) + 1);
                gridAdapter.notifyDataSetChanged();

            }
        });
        //gridview 요일 표시

        dayList = new ArrayList<String>();
        dayList.add("일");
        dayList.add("월");
        dayList.add("화");
        dayList.add("수");
        dayList.add("목");
        dayList.add("금");
        dayList.add("토");

        mCal = Calendar.getInstance();

        //이번달 1일 무슨요일인지 판단 mCal.set(Year,Month,Day)

        mCal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);

         dayNum = mCal.get(Calendar.DAY_OF_WEEK);

        //1일 - 요일 매칭 시키기 위해 공백 add

        for (int i = 1; i < dayNum; i++) {
            dayList.add("");
        }

        setCalendarDate(mCal.get(Calendar.MONTH) + 1);
        gridAdapter = new GridAdapter(view.getContext(),dayList,mCal);
        gridView.setAdapter(gridAdapter); // uses the view to get the context instead of getActivity().

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                real_day = position -5 -dayNum;
                Toast.makeText(view.getContext(),Year+"/"+Month+"/"+real_day,Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    /**
     * 해당 월에 표시할 일 수 구함
     *
     * @param month
     */

    private void setCalendarDate(int month) {
        mCal.set(Calendar.MONTH, month - 1);
        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add("" + (i + 1));
        }
    }




}