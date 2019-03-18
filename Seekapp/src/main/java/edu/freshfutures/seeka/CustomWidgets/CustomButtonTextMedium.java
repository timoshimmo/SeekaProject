package edu.freshfutures.seeka.CustomWidgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by tokmang on 5/3/2016.
 */
public class CustomButtonTextMedium extends Button {

    public CustomButtonTextMedium(Context context) {
        super(context);
        setFont();
    }

    public CustomButtonTextMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public CustomButtonTextMedium(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-Medium.ttf");
        setTypeface(font);
    }
}
