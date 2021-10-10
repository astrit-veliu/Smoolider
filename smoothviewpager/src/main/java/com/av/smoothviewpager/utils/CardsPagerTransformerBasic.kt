package com.av.smoothviewpager.utils;

import android.os.Build;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by Astrit Veliu on 09,September,2019
 */
public class CardsPagerTransformerBasic implements ViewPager.PageTransformer {

    private int baseElevation;
    private int raisingElevation;
    private float smallerScale;

    public CardsPagerTransformerBasic(int baseElevation, int raisingElevation, float smallerScale) {
        this.baseElevation = baseElevation;
        this.raisingElevation = raisingElevation;
        this.smallerScale = smallerScale;
    }

    @Override
    public void transformPage(View page, float position) {
        float absPosition = Math.abs(position);

        if (absPosition >= 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                page.setElevation(baseElevation);
            }
            page.setScaleY(smallerScale);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                page.setElevation(((1 - absPosition) * raisingElevation + baseElevation));
            }
            page.setScaleY((smallerScale - 1) * absPosition + 1);
        }
    }

}