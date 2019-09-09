package com.av.smoothviewpager.Smoolider;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.viewpager.widget.ViewPager;

import com.av.smoothviewpager.utils.CardsPagerTransformerBasic;
import com.av.smoothviewpager.utils.FixedSpeedScroller;

import java.lang.reflect.Field;

/**
 * Created by Astrit Veliu on 09,September,2019
 */
public class SmoothViewpager extends ViewPager {

    private int baseElevation = 0;
    private int raisingElevation = 1;
    private float smallerScale = 0.6f;

    public SmoothViewpager(Context context) {
        super(context);
        postInitViewPager();
    }

    public SmoothViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        postInitViewPager();
    }


    private void postInitViewPager() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field mScroller;
            mScroller = viewpager.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(getContext());
            mScroller.set(this, scroller);

            transform_viewPager();
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }


    private void transform_viewPager(){
        Resources r = getResources();
        int partialWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());
        int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());

        int viewPagerPadding = partialWidth + pageMargin;
        setPageMargin(pageMargin);
        setPadding(viewPagerPadding, 0, viewPagerPadding, 0);

        setPageTransformer(false, new CardsPagerTransformerBasic(baseElevation, raisingElevation,  smallerScale));
    }

}
