package com.example.leemoonseong.scheduler.dao;

/**
 * Created by hosea on 2016-12-07.
 */

public class WeekItem {
    private String dayName;
    private String date;
    private String schedules;

    public WeekItem(String inputName, String inputDate, String inputSchedules) {
        dayName = inputName;
        date = inputDate;
        schedules = inputSchedules;
    }
}