package edu.freshfutures.seeka;

/**
 * Created by tokmang on 4/18/2016.
 */
public enum LayoutModels {

    COURSES(R.layout.courses_layout),
    ENROLL(R.layout.enrolment_layout);

    private int mLayoutResId;

    LayoutModels(int layouts) {
        this.mLayoutResId = layouts;

    }

    public int getLayoutResId() {
        return mLayoutResId;
    }
}
