package com.mxi.android.salarynotification.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.LruCache;
import android.widget.EditText;

/**
 * Created by etiloxadmin on 24/8/15.
 */
public class EditTextMyQuestrialRegular extends EditText {
//
    private final static String NAME = "FONTAWESOME";
    private static LruCache<String, Typeface> sTypefaceCache = new LruCache<String, Typeface>(12);

    public EditTextMyQuestrialRegular(Context context) {
        super(context);
        init();

    }

    public EditTextMyQuestrialRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {

        Typeface typeface = sTypefaceCache.get(NAME);

        if (typeface == null) {

            typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Questrial-Regular.otf");
            sTypefaceCache.put(NAME, typeface);

        }

        setTypeface(typeface);
//        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Questrial-Regular.otf"));


    }

}
