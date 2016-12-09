package com.example.leemoonseong.scheduler;

import com.example.leemoonseong.scheduler.dao.ScheduleVO;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by LeeMoonSeong on 2016-12-09.
 */
public class comparator implements Comparator<ScheduleVO> {
    @Override
    public int compare(ScheduleVO first , ScheduleVO second){
        Date date1 = first.getStartTime();
        Date date2 = second.getStartTime();

        if(date1.after(date2)){
            return 1;
        }
        else if(date2.after(date1)){
            return -1;
        }
        else{
            return 0;
        }
    }
}