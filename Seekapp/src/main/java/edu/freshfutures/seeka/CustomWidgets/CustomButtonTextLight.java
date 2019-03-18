package edu.freshfutures.seeka.CustomWidgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by tokmang on 5/3/2016.
 */
public class CustomButtonTextLight extends Button {

    public CustomButtonTextLight(Context context) {
        super(context);
        setFont();
    }

    public CustomButtonTextLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public CustomButtonTextLight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-Light.ttf");
        setTypeface(font);
    }
}
