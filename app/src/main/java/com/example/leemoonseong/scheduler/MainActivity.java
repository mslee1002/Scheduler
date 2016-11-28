package com.example.leemoonseong.scheduler;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

public class MainActivity extends AppCompatActivity {

    final MonthlyFragment monthlyFragment = new MonthlyFragment();
    final WeeklyFragment weeklyFragment = new WeeklyFragment();
    final DailyFragment dailyFragment = new DailyFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    protected void switchFragment(int id) {
        final FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        if (id == 0)
            fragmentTransaction.replace(R.id.fragment, monthlyFragment);
        else if (id == 1)
            fragmentTransaction.replace(R.id.fragment, weeklyFragment);
        else if (id == 2)
            fragmentTransaction.replace(R.id.fragment, dailyFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
