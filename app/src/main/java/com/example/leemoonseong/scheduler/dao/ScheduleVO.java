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
    private String audioName;
    private String videoName;
    private String imageName;

    public ScheduleVO(String title, Date startTime, Date endTime, String location, String Memo, String imageName, String audioName, String videoName) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.memo = Memo;
        this.imageName = imageName;
        this.audioName = audioName;
        this.videoName = videoName;
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

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
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

    public String getAudioName() {
        return audioName;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getTitle() {
        return title;
    }
}