package com.example.leemoonseong.scheduler.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.leemoonseong.scheduler.Database.MyDBHelper;
import com.example.leemoonseong.scheduler.R;
import com.example.leemoonseong.scheduler.dao.ScheduleVO;
import com.example.leemoonseong.scheduler.fragment.DailyFragment;
import com.example.leemoonseong.scheduler.fragment.MonthlyFragment;
import com.example.leemoonseong.scheduler.fragment.WeeklyFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ScheduleVO> dayList;
    private String currentFragment = "monthly";
    private ActionBar ab;
    private MyDBHelper helper;
    private String today, schedule;
    final MonthlyFragment monthlyFragment = new MonthlyFragment();
    final WeeklyFragment weeklyFragment = new WeeklyFragment();
    final DailyFragment dailyFragment = new DailyFragment();
    FragmentManager fm =getFragmentManager();

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ab = getSupportActionBar();
        ab.setTitle("월별 보기");
        setContentView(R.layout.activity_main);
        helper = new MyDBHelper(getApplicationContext());
        dayList = new ArrayList<>();
        SimpleDateFormat sfm = new SimpleDateFormat("yyyy-MM-dd");
        today = sfm.format(new Date());
    }

    @Override
    protected void onResume(){
        super.onResume();
        String sql = "Select * FROM schedule;";
        StringBuffer sb = new StringBuffer();
        Cursor cursor = helper.getReadableDatabase().rawQuery(sql,null);
        Date tempDate = new Date();
        while (cursor.moveToNext()) {
//            1. title, 2. startTime, 3 .endTime ,4. location, 5. memo,6. image
//            Calendar t = new GregorianCalendar();
            SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
            try {
                tempDate = dateFormat.parse(cursor.getString(2));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (dateFormat.format(tempDate).startsWith(today))
            {
                sb.append(" " + cursor.getString(1));
            }
        }
        if (sb.toString().equals("")) {
            NotificationSomethings("없음");
        } else {
            NotificationSomethings(sb.toString());
        }
    }


    protected void switchFragment(int id) {
        final FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        if (id == 0) {
            fragmentTransaction.replace(R.id.fragment, monthlyFragment).addToBackStack(null);
        }
        else if (id == 1) {
            fragmentTransaction.replace(R.id.fragment, weeklyFragment).addToBackStack(null);
        }
        else if (id == 2) {
            fragmentTransaction.replace(R.id.fragment, dailyFragment).addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_month:
                switchFragment(0);
                return true;
            case R.id.view_week:
                switchFragment(1);
                return true;
            case R.id.view_day:
                switchFragment(2);
                return true;
            case R.id.add_schedule:
                Intent intent =new Intent(getApplicationContext(), AddScheduleActivity.class);
                intent.putExtra("currentFragment",currentFragment);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        if (fm.getBackStackEntryCount() == 0 ) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("앱을 종료 하시겠습니까?");
            // alert.setMessage("Message");

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    finish();
                }
            });

            alert.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    });

            alert.show();
        }
        else
            super.onBackPressed();
    }

    public void NotificationSomethings(String msg) {
        Resources res = getResources();

        Intent notificationIntent = new Intent(this, NotificationSomething.class);
        notificationIntent.putExtra("notificationId", 9999); //전달할 값
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setContentTitle("이문성 이호세아의 scheduler")
                .setContentText("오늘의 스케줄 :" + msg)
                .setTicker("")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            builder.setCategory(Notification.CATEGORY_MESSAGE)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
        }
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1234, builder.build());
    }

}