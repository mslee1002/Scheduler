package com.example.leemoonseong.scheduler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by LeeMoonSeong on 2016-11-28.
 */

public class AddScheduleActivity extends AppCompatActivity {
    ActionBar ab;
    Date date1;
    Date date2;
    int s_year, s_month, s_day, s_hour, s_minute;
    int e_year, e_month, e_day, e_hour, e_minute;
    int start_year =0 , start_month=0, start_day =0, start_hour=0, start_mitute=0;
    int end_year=0 , end_month =0, end_day =0, end_hour =0, end_minute =0;
    TextView start;
    TextView end;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ab = getSupportActionBar();
        ab.setTitle("일정 추가");
        setContentView(R.layout.activity_add_schedule);

        GregorianCalendar calendar = new GregorianCalendar();
        s_year = calendar.get(Calendar.YEAR);
        s_month = calendar.get(Calendar.MONTH);
        s_day = calendar.get(Calendar.DAY_OF_MONTH);
        s_hour = calendar.get(Calendar.HOUR_OF_DAY);
        s_minute = calendar.get(Calendar.MINUTE);

        e_year = calendar.get(Calendar.YEAR);
        e_month = calendar.get(Calendar.MONTH);
        e_day = calendar.get(Calendar.DAY_OF_MONTH);
        e_hour = calendar.get(Calendar.HOUR_OF_DAY);
        e_minute = calendar.get(Calendar.MINUTE);

        start = (TextView)findViewById(R.id.start_schedule);
        end = (TextView)findViewById(R.id.end_schedule);
        findViewById(R.id.start_date).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(AddScheduleActivity.this, dateSetListener, s_year, s_month, s_day);
                datePickerDialog1.show();
            }
        });
//        findViewById(R.id.start_time).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                TimePickerDialog timePickerDialog1 = new TimePickerDialog(AddScheduleActivity.this, timeSetListener, s_hour, s_minute, false);
//                timePickerDialog1.show();
//            }
//        });
        findViewById(R.id.end_date).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(start_year ==0){
                    Toast.makeText(getApplicationContext(),"시작일을 먼저 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
                else {
                    // TODO Auto-generated method stub
                    DatePickerDialog datePickerDialog2 = new DatePickerDialog(AddScheduleActivity.this, dateSetListener2, e_year, e_month, e_day);
                    datePickerDialog2.show();
                }
            }
        });
//        findViewById(R.id.end_time).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                TimePickerDialog timePickerDialog2 = new TimePickerDialog(AddScheduleActivity.this, timeSetListener2, e_hour, e_minute, false);
//                timePickerDialog2.show();
//            }
//        });
    }


    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            start_year = year;
            start_month = monthOfYear +1;
            start_day = dayOfMonth;
            TimePickerDialog timePickerDialog1 = new TimePickerDialog(AddScheduleActivity.this, timeSetListener, s_hour, s_minute, false);
            timePickerDialog1.show();
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            start_hour = hourOfDay;
            start_mitute = minute;
            String msg = String.format("%d년 %d월 %d일 %d시 %d분", start_year, start_month,start_day,start_hour,start_mitute);
            start.setText(msg);
        }
    };

    private DatePickerDialog.OnDateSetListener dateSetListener2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            end_year = year;
            end_month = monthOfYear +1;
            end_day = dayOfMonth;
            TimePickerDialog timePickerDialog2 = new TimePickerDialog(AddScheduleActivity.this, timeSetListener2, s_hour, s_minute, false);
            timePickerDialog2.show();
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener2 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            end_hour = hourOfDay;
            end_minute = minute;
            SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
            String s_date = start_year+"-"+start_month+"-"+start_day+" "+start_hour+":"+start_mitute;
            String e_date = end_year+"-"+end_month+"-"+end_day+" "+end_hour+":"+end_minute;
            try {
                date1 = dateFormat.parse(s_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                date2 = dateFormat.parse(e_date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(date2.after(date1)){
                String msg = String.format("%d년 %d월 %d일 %d시 %d분", end_year, end_month,end_day,end_hour,end_minute);
                end.setText(msg);
            }
            else{
                Toast.makeText(getApplicationContext(),"시작시간은 종료시간보다 클 수 없습니다.",Toast.LENGTH_SHORT).show();
                start.setText("");
                end.setText("");
            }




        }
    };
}

