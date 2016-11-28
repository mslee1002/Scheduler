package com.example.leemoonseong.scheduler;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by LeeMoonSeong on 2016-11-28.
 */

public class AddScheduleActivity extends AppCompatActivity {
    ActionBar ab;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ab = getSupportActionBar();
        ab.setTitle("월별 보기");
        setContentView(R.layout.activity_add_schedule);


    }
}
