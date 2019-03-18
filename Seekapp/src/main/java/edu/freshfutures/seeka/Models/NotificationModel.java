package edu.freshfutures.seeka.Models;

/**
 * Created by tokmang on 9/5/2016.
 */
public class NotificationModel {

    private int position;
    private int emailNotifyValue;
    private int mobileNotifyValue;
    private String title;

    public NotificationModel() {

        super();

    }

    public int getNotifiPosition() {
        return this.position;
    }

    public int getEmailValue() {
        return this.emailNotifyValue;
    }

    public int getMobileValue() {
        return this.mobileNotifyValue;
    }

    public String getNotifyTitle() {
        return this.title;
    }

    public void setNotifiPosition(int pos) {
        this.position = pos;
    }

    public void setEmailValue(int ev) {
        this.emailNotifyValue = ev;
    }

    public void setMobileValue(int mv) {
        this.mobileNotifyValue = mv;
    }

    public void setTitleValue(String ttl) {
        this.title = ttl;
    }
}
