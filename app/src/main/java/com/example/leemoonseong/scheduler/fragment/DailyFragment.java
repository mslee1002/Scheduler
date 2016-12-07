package com.example.leemoonseong.scheduler.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leemoonseong.scheduler.Activity.MainActivity;
import com.example.leemoonseong.scheduler.R;

/**
 * Created by Hosea on 2016-11-28.
 */
public class DailyFragment extends Fragment {

    public DailyFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("일별 보기");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily, container, false);
    }

}