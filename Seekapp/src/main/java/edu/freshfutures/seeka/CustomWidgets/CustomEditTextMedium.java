package edu.freshfutures.seeka.CustomWidgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by tokmang on 5/3/2016.
 */
public class CustomEditTextMedium extends EditText {

    public CustomEditTextMedium(Context context) {
        super(context);
        setFont();
    }

    public CustomEditTextMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }
    public CustomEditTextMedium(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-Medium.ttf");
        setTypeface(font);
    }

}
