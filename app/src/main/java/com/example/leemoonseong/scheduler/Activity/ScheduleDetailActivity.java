package com.example.leemoonseong.scheduler.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leemoonseong.scheduler.R;

public class ScheduleDetailActivity extends AppCompatActivity {
    private ActionBar ab;
    private TextView name, date, scheduleStart, scheduleEnd, location, memo;
    private ImageView image;
    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ab = getSupportActionBar();
        ab.setTitle("일정 상세보기");
        setContentView(R.layout.activity_schedule_detail);

        name = (TextView) findViewById(R.id.tv_detail_schedule_name);
        date = (TextView) findViewById(R.id.tv_detail_schedule_date);
        scheduleStart = (TextView) findViewById(R.id.tv_detail_schedule_start);
        scheduleEnd = (TextView) findViewById(R.id.tv_detail_schedule_end);
        location = (TextView) findViewById(R.id.tv_detail_location);
        memo = (TextView) findViewById(R.id.tv_detail_memo);
        image = (ImageView) findViewById(R.id.tv_detail_image);

//        name.setText();
//        date.setText();
//        scheduleStart.setText();
//        scheduleEnd.setText();
//        location.setText();
//        memo.setText();
//        image.setImageBitmap();



    }
}
