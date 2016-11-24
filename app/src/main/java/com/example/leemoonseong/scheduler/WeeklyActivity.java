package com.example.leemoonseong.scheduler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Hosea on 2016-11-01.
 */
public class WeeklyActivity extends AppCompatActivity {
    static WeeklyAdapter wadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        // 데이터 원본 준비
        ArrayList<WeekItem> data = new ArrayList<WeekItem>();
        data.add(new WeekItem("할일 있음"));
        data.add(new WeekItem("할일 있음"));
        data.add(new WeekItem("할일 있음"));
        data.add(new WeekItem("할일 있음"));
        data.add(new WeekItem("할일 있음"));
        data.add(new WeekItem("할일 있음"));
        data.add(new WeekItem("할일 있음"));

        //어댑터 생성
        wadapter = new WeeklyAdapter(this, R.layout.weeklyitem, data);

        //어댑터 연결
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(wadapter);

    }
}