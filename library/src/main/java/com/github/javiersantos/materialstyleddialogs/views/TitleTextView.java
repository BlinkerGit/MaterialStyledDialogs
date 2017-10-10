package com.github.javiersantos.materialstyleddialogs.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;


public class TitleTextView extends AppCompatTextView {

    public TitleTextView(Context context) {
        this(context, null);
        init();
    }

    public TitleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
        init();
    }

    public TitleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(TintContextWrapper.wrap(context), attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirNextLTPro-Demi.ttf");
        setTypeface(tf);
    }
}
