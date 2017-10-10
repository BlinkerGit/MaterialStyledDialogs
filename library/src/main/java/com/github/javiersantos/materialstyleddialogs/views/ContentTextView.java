package com.github.javiersantos.materialstyleddialogs.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;


public class ContentTextView extends AppCompatTextView {

    public ContentTextView (Context context) {
        this(context, null);
        init();
    }

    public ContentTextView (Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
        init();
    }

    public ContentTextView (Context context, AttributeSet attrs, int defStyleAttr) {
        super(TintContextWrapper.wrap(context), attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/BentonSans-Regular.ttf");
        setTypeface(tf);
    }

}
