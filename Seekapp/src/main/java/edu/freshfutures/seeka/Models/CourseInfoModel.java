package edu.freshfutures.seeka.Models;

/**
 * Created by tokmang on 8/6/2016.
 */
public class CourseInfoModel {

    public static final int COURSE_INFO_TYPE = 0;
    public static final int TWINNING_TYPE = 1;
    public static final int MINIMUM_REQ_TYPE = 2;
    public static final int TOTAL_AVAILABLE_TYPE = 3;
    public static final int MATCH_TYPE = 4;
    public static final int TOUR_TYPE = 5;
    public static final int SCHOLARSHIP_TYPE = 6;
    public static final int UNI_INFO_TYPE = 7;
    public static final int SERVICES_TYPE = 8;
    public static final int VISA_TYPE = 9;
    public static final int MEDIA_TYPE = 10;
    public static final int APPLY_TYPE = 11;

    private String mTitle;
    private int mType;



    public CourseInfoModel(String title, int type) {

        this.mTitle = title;
        this.mType = type;

    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }


}
