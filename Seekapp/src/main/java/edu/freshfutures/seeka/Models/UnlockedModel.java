package edu.freshfutures.seeka.Models;

import android.support.v7.widget.RecyclerView;

/**
 * Created by tokmang on 7/20/2016.
 */
public class UnlockedModel {

    private String rankNum;
    private String imgUrl;
    private int sponsStatus;
    private String durationType;
    private String durationTime;
    private String costRange;
    private String courseTitle;
    private int courseId;
    private String currency;
    private int cached;

    public UnlockedModel() {

        super();

    }

    public String getRankValue() {
        return rankNum;
    }

    public String getCtryImg() {
        return imgUrl;
    }

    public int getSponStatus() {
        return sponsStatus;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getDurationType() {
        return durationType;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public String getCostRange() {
        return costRange;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCurrency() {
        return currency;
    }

    public int getCached() {
        return cached;
    }



    public void setSponStatus(int spons) {
        this.sponsStatus = spons;
    }

    public void setRankValue(String ranks) {
        this.rankNum = ranks;
    }

    public void setCtryImg(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setDurationType(String durationType) {
        this.durationType = durationType;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public void setCostRange(String costRange) {
        this.costRange = costRange;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCached(int cached) {
        this.cached = cached;
    }


}
