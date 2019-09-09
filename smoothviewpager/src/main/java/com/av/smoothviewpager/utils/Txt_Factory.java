package com.av.smoothviewpager.utils;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.StyleRes;

/**
 * Created by Astrit Veliu on 09,September,2019
 */
public class Txt_Factory implements  ViewSwitcher.ViewFactory{

    @StyleRes
    final int styleId;
    final boolean center;
    final Context mContext;

    public Txt_Factory(@StyleRes int styleId, boolean center, Context context) {
        this.styleId = styleId;
        this.center = center;
        this.mContext = context;
    }

    @SuppressWarnings("deprecation")
    @Override
    public View makeView() {
        final TextView textView = new TextView(mContext);

        if (center) {
            textView.setGravity(Gravity.CENTER);
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            textView.setTextAppearance(mContext, styleId);
        } else {
            textView.setTextAppearance(styleId);
        }

        return textView;
    }

}
