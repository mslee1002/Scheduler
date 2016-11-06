package com.example.leemoonseong.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    /**
     * 연/월 텍스트뷰
     */
    private TextView tvDate;
    /**
     * 그리드뷰 어댑터
     */
    private GridAdapter gridAdapter;
    /**
     * 일 저장 할 리스트
     */
    private ArrayList<String> dayList;
    //동적으로 변화하는 달력을 위해 현재 연,월을 저장힉 위한 변수
    private int Year;
    private int Month;
    //오늘 날짜를 가지는 변수 (동적으로 변하는 변수가 아님)
    private int original_month;
    /**
     * 그리드뷰
     */
    private GridView gridView;
    /**
     * 캘린더 변수
     */
    private Calendar mCal;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDate = (TextView) findViewById(R.id.tv_date);
        gridView = (GridView) findViewById(R.id.gridview);


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
        Month =Integer.parseInt(curMonthFormat.format(date));

        tvDate.setText(Year +"년  " + Month +"월");


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

        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);

        //1일 - 요일 매칭 시키기 위해 공백 add

        for (int i = 1; i < dayNum; i++) {

            dayList.add("");

        }

        setCalendarDate(mCal.get(Calendar.MONTH) + 1);

        gridAdapter = new GridAdapter(getApplicationContext(), dayList);

        gridView.setAdapter(gridAdapter);


        Button btn_before = (Button)findViewById(R.id.before);
        Button btn_next = (Button)findViewById(R.id.next);


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
                    int dayNum = mCal.get(Calendar.DAY_OF_WEEK);
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
                int dayNum = mCal.get(Calendar.DAY_OF_WEEK);
                //1일 - 요일 매칭 시키기 위해 공백 add
                for (int i = 1; i < dayNum; i++) {
                    dayList.add("");
                }
                setCalendarDate(mCal.get(Calendar.MONTH) + 1);
                gridAdapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
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


    /**
     * 그리드뷰 어댑터
     */

    private class GridAdapter extends BaseAdapter {
        private final List<String> list;
        private final LayoutInflater inflater;

        /**
         * 생성자
         *
         * @param context
         * @param list
         */

        public GridAdapter(Context context, List<String> list) {

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
    }

    private class ViewHolder {

        TextView tvItemGridView;

    }

}
