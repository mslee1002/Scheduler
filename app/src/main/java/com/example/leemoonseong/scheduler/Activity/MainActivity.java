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
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.leemoonseong.scheduler.R;
import com.example.leemoonseong.scheduler.fragment.DailyFragment;
import com.example.leemoonseong.scheduler.fragment.MonthlyFragment;
import com.example.leemoonseong.scheduler.fragment.WeeklyFragment;

public class MainActivity extends AppCompatActivity {
    private String currentFragment = "monthly";
    ActionBar ab;
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


}
