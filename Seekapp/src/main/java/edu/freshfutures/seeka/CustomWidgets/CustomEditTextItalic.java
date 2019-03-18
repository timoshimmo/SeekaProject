package edu.freshfutures.seeka.CustomWidgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by tokmang on 10/19/2016.
 */

public class CustomEditTextItalic extends EditText {

    public CustomEditTextItalic(Context context) {
        super(context);
        setFont();
    }

    public CustomEditTextItalic(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }
    public CustomEditTextItalic(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-Italic.ttf");
        setTypeface(font);
    }

}
