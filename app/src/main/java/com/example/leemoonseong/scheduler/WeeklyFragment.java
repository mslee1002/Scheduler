package com.example.leemoonseong.scheduler;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hosea on 2016-11-28.
 */
public class WeeklyFragment extends Fragment {

    public WeeklyFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("주별 보기");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weekly, container, false);
    }

}