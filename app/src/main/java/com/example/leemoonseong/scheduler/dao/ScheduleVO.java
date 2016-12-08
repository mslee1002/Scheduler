package com.example.leemoonseong.scheduler.dao;

import java.util.Date;

/**
 * Created by hosea on 2016-12-07.
 */
public class ScheduleVO {
    private int scheduleId;
    private String title;
    private Date startTime;
    private Date endTime;
    private String location;
    private String memo;
    private String imageName;


    public ScheduleVO(int ScheduleId,  String title, Date startTime, Date endTime, String location, String Memo, String imageName) {
        this.scheduleId = ScheduleId;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.memo = Memo;
        this.imageName = imageName;

    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMemo(String Memo) {
        memo = Memo;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }


    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }

    public String getMemo() {
        return memo;
    }

    public String getImageName() {
        return imageName;
    }


    public String getTitle() {
        return title;
    }
}