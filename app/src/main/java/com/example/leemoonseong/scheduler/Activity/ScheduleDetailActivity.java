package com.example.leemoonseong.scheduler.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leemoonseong.scheduler.R;
import com.example.leemoonseong.scheduler.dao.ScheduleVO;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ScheduleDetailActivity extends AppCompatActivity {
    private Intent previousIntent = getIntent();
    private ScheduleVO scheduleVO;
    private ActionBar ab;
    private TextView title, date, scheduleStart, scheduleEnd, location, memo;
    private ImageView image;
    private Bitmap bitmap;
    private Button modify, delete;

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ab = getSupportActionBar();
        ab.setTitle("일정 상세보기");
        setContentView(R.layout.activity_schedule_detail);

        //TODO: id값을 통한 데이터 받아오기필요
//        previousIntent.getExtras().getString("scheduleId");    // id 값임
//        scheduleVO =

        title = (TextView) findViewById(R.id.tv_detail_schedule_title);
        date = (TextView) findViewById(R.id.tv_detail_schedule_date);
        scheduleStart = (TextView) findViewById(R.id.tv_detail_schedule_start);
        scheduleEnd = (TextView) findViewById(R.id.tv_detail_schedule_end);
        location = (TextView) findViewById(R.id.tv_detail_location);
        memo = (TextView) findViewById(R.id.tv_detail_memo);
        image = (ImageView) findViewById(R.id.tv_detail_image);
        modify = (Button) findViewById(R.id.btn_detail_modify);

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "수정 clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ModifyScheduleActivity.class);
//                intent.
                startActivity(intent);
                ScheduleDetailActivity.this.finish();
            }
        });

        delete = (Button) findViewById(R.id.btn_detail_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSimple();
            }
        });

        try {
            title.setText(scheduleVO.getTitle());
            date.setText(scheduleVO.getStartTime().toString());
            scheduleStart.setText(scheduleVO.getStartTime().toString());
            scheduleEnd.setText(scheduleVO.getEndTime().toString());
            location.setText(scheduleVO.getLocation());
            memo.setText(scheduleVO.getMemo());

            File sd = Environment.getExternalStorageDirectory();
            File imgfile = new File(sd, scheduleVO.getImageName());
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(imgfile.getAbsolutePath(), bmOptions);
            image.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "해당 하는 데이터를 찾지 못했습니다.", Toast.LENGTH_SHORT).show();
        }


    }

    public void DialogSimple() {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setMessage("정말로 이 일정을 삭제하시겠습니까?").setCancelable(
                false).setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "삭제가 취소되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'Yes' Button
                        Toast.makeText(getApplicationContext(), "삭제 되었습니다. (삭제 기능 구현 필요)", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        AlertDialog alert = alt_bld.create();
        // Title for AlertDialog
        alert.setTitle("일정 삭제");
        // Icon for AlertDialog
//        alert.setIcon(R.drawable.icon);
        alert.show();
    }
}
