package edu.freshfutures.seeka.CustomWidgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tokmang on 7/8/2016.
 */
public class CheckableView extends LinearLayout implements Checkable {

    private boolean isChecked;
    private RadioButton checkableViews;

    public CheckableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckableView(Context context) {
        super(context);

    }

    public CheckableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setChecked(boolean checked) {
        this.isChecked = checked;
        if (checkableViews != null) {
            checkableViews.setChecked(isChecked);
        }
    }

    @Override
    public boolean isChecked() {
        return checkableViews != null ? checkableViews.isChecked() : false;
    }

    @Override
    public void toggle() {
        this.isChecked = !this.isChecked;
        if (checkableViews != null) {
            checkableViews.toggle();
        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // find checked text view
        int childCount = getChildCount();
        for (int i = 0; i < childCount; ++i) {
            View v = getChildAt(i);
            if (v instanceof RadioButton) {
                checkableViews = (RadioButton) v;
            }
        }
    }

}
